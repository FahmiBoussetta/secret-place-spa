package com.yamibo.secretplacespa.web.rest;

import com.yamibo.secretplacespa.domain.HousingClosure;
import com.yamibo.secretplacespa.repository.HousingClosureRepository;
import com.yamibo.secretplacespa.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.yamibo.secretplacespa.domain.HousingClosure}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HousingClosureResource {

    private final Logger log = LoggerFactory.getLogger(HousingClosureResource.class);

    private static final String ENTITY_NAME = "housingClosure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HousingClosureRepository housingClosureRepository;

    public HousingClosureResource(HousingClosureRepository housingClosureRepository) {
        this.housingClosureRepository = housingClosureRepository;
    }

    /**
     * {@code POST  /housing-closures} : Create a new housingClosure.
     *
     * @param housingClosure the housingClosure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new housingClosure, or with status {@code 400 (Bad Request)} if the housingClosure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/housing-closures")
    public ResponseEntity<HousingClosure> createHousingClosure(@Valid @RequestBody HousingClosure housingClosure)
        throws URISyntaxException {
        log.debug("REST request to save HousingClosure : {}", housingClosure);
        if (housingClosure.getId() != null) {
            throw new BadRequestAlertException("A new housingClosure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HousingClosure result = housingClosureRepository.save(housingClosure);
        return ResponseEntity
            .created(new URI("/api/housing-closures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /housing-closures/:id} : Updates an existing housingClosure.
     *
     * @param id the id of the housingClosure to save.
     * @param housingClosure the housingClosure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingClosure,
     * or with status {@code 400 (Bad Request)} if the housingClosure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the housingClosure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/housing-closures/{id}")
    public ResponseEntity<HousingClosure> updateHousingClosure(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HousingClosure housingClosure
    ) throws URISyntaxException {
        log.debug("REST request to update HousingClosure : {}, {}", id, housingClosure);
        if (housingClosure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, housingClosure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!housingClosureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HousingClosure result = housingClosureRepository.save(housingClosure);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingClosure.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /housing-closures/:id} : Partial updates given fields of an existing housingClosure, field will ignore if it is null
     *
     * @param id the id of the housingClosure to save.
     * @param housingClosure the housingClosure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingClosure,
     * or with status {@code 400 (Bad Request)} if the housingClosure is not valid,
     * or with status {@code 404 (Not Found)} if the housingClosure is not found,
     * or with status {@code 500 (Internal Server Error)} if the housingClosure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/housing-closures/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HousingClosure> partialUpdateHousingClosure(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HousingClosure housingClosure
    ) throws URISyntaxException {
        log.debug("REST request to partial update HousingClosure partially : {}, {}", id, housingClosure);
        if (housingClosure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, housingClosure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!housingClosureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HousingClosure> result = housingClosureRepository
            .findById(housingClosure.getId())
            .map(
                existingHousingClosure -> {
                    if (housingClosure.getStartDate() != null) {
                        existingHousingClosure.setStartDate(housingClosure.getStartDate());
                    }
                    if (housingClosure.getEndDate() != null) {
                        existingHousingClosure.setEndDate(housingClosure.getEndDate());
                    }
                    if (housingClosure.getCause() != null) {
                        existingHousingClosure.setCause(housingClosure.getCause());
                    }

                    return existingHousingClosure;
                }
            )
            .map(housingClosureRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingClosure.getId().toString())
        );
    }

    /**
     * {@code GET  /housing-closures} : get all the housingClosures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of housingClosures in body.
     */
    @GetMapping("/housing-closures")
    public List<HousingClosure> getAllHousingClosures() {
        log.debug("REST request to get all HousingClosures");
        return housingClosureRepository.findAll();
    }

    /**
     * {@code GET  /housing-closures/:id} : get the "id" housingClosure.
     *
     * @param id the id of the housingClosure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the housingClosure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/housing-closures/{id}")
    public ResponseEntity<HousingClosure> getHousingClosure(@PathVariable Long id) {
        log.debug("REST request to get HousingClosure : {}", id);
        Optional<HousingClosure> housingClosure = housingClosureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(housingClosure);
    }

    /**
     * {@code DELETE  /housing-closures/:id} : delete the "id" housingClosure.
     *
     * @param id the id of the housingClosure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/housing-closures/{id}")
    public ResponseEntity<Void> deleteHousingClosure(@PathVariable Long id) {
        log.debug("REST request to delete HousingClosure : {}", id);
        housingClosureRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
