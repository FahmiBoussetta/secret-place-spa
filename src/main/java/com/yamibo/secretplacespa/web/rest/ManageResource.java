package com.yamibo.secretplacespa.web.rest;

import com.yamibo.secretplacespa.domain.Manage;
import com.yamibo.secretplacespa.repository.ManageRepository;
import com.yamibo.secretplacespa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.yamibo.secretplacespa.domain.Manage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ManageResource {

    private final Logger log = LoggerFactory.getLogger(ManageResource.class);

    private static final String ENTITY_NAME = "manage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManageRepository manageRepository;

    public ManageResource(ManageRepository manageRepository) {
        this.manageRepository = manageRepository;
    }

    /**
     * {@code POST  /manages} : Create a new manage.
     *
     * @param manage the manage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new manage, or with status {@code 400 (Bad Request)} if the manage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/manages")
    public ResponseEntity<Manage> createManage(@RequestBody Manage manage) throws URISyntaxException {
        log.debug("REST request to save Manage : {}", manage);
        if (manage.getId() != null) {
            throw new BadRequestAlertException("A new manage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Manage result = manageRepository.save(manage);
        return ResponseEntity
            .created(new URI("/api/manages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /manages/:id} : Updates an existing manage.
     *
     * @param id the id of the manage to save.
     * @param manage the manage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manage,
     * or with status {@code 400 (Bad Request)} if the manage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the manage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/manages/{id}")
    public ResponseEntity<Manage> updateManage(@PathVariable(value = "id", required = false) final Long id, @RequestBody Manage manage)
        throws URISyntaxException {
        log.debug("REST request to update Manage : {}, {}", id, manage);
        if (manage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Manage result = manageRepository.save(manage);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manage.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /manages/:id} : Partial updates given fields of an existing manage, field will ignore if it is null
     *
     * @param id the id of the manage to save.
     * @param manage the manage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated manage,
     * or with status {@code 400 (Bad Request)} if the manage is not valid,
     * or with status {@code 404 (Not Found)} if the manage is not found,
     * or with status {@code 500 (Internal Server Error)} if the manage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/manages/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Manage> partialUpdateManage(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Manage manage
    ) throws URISyntaxException {
        log.debug("REST request to partial update Manage partially : {}, {}", id, manage);
        if (manage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, manage.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!manageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Manage> result = manageRepository
            .findById(manage.getId())
            .map(
                existingManage -> {
                    return existingManage;
                }
            )
            .map(manageRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, manage.getId().toString())
        );
    }

    /**
     * {@code GET  /manages} : get all the manages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of manages in body.
     */
    @GetMapping("/manages")
    public List<Manage> getAllManages() {
        log.debug("REST request to get all Manages");
        return manageRepository.findAll();
    }

    /**
     * {@code GET  /manages/:id} : get the "id" manage.
     *
     * @param id the id of the manage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the manage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/manages/{id}")
    public ResponseEntity<Manage> getManage(@PathVariable Long id) {
        log.debug("REST request to get Manage : {}", id);
        Optional<Manage> manage = manageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(manage);
    }

    /**
     * {@code DELETE  /manages/:id} : delete the "id" manage.
     *
     * @param id the id of the manage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/manages/{id}")
    public ResponseEntity<Void> deleteManage(@PathVariable Long id) {
        log.debug("REST request to delete Manage : {}", id);
        manageRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
