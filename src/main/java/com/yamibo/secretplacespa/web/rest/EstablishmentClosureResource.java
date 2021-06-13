package com.yamibo.secretplacespa.web.rest;

import com.yamibo.secretplacespa.domain.EstablishmentClosure;
import com.yamibo.secretplacespa.repository.EstablishmentClosureRepository;
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
 * REST controller for managing {@link com.yamibo.secretplacespa.domain.EstablishmentClosure}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstablishmentClosureResource {

    private final Logger log = LoggerFactory.getLogger(EstablishmentClosureResource.class);

    private static final String ENTITY_NAME = "establishmentClosure";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstablishmentClosureRepository establishmentClosureRepository;

    public EstablishmentClosureResource(EstablishmentClosureRepository establishmentClosureRepository) {
        this.establishmentClosureRepository = establishmentClosureRepository;
    }

    /**
     * {@code POST  /establishment-closures} : Create a new establishmentClosure.
     *
     * @param establishmentClosure the establishmentClosure to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new establishmentClosure, or with status {@code 400 (Bad Request)} if the establishmentClosure has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/establishment-closures")
    public ResponseEntity<EstablishmentClosure> createEstablishmentClosure(@Valid @RequestBody EstablishmentClosure establishmentClosure)
        throws URISyntaxException {
        log.debug("REST request to save EstablishmentClosure : {}", establishmentClosure);
        if (establishmentClosure.getId() != null) {
            throw new BadRequestAlertException("A new establishmentClosure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstablishmentClosure result = establishmentClosureRepository.save(establishmentClosure);
        return ResponseEntity
            .created(new URI("/api/establishment-closures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /establishment-closures/:id} : Updates an existing establishmentClosure.
     *
     * @param id the id of the establishmentClosure to save.
     * @param establishmentClosure the establishmentClosure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated establishmentClosure,
     * or with status {@code 400 (Bad Request)} if the establishmentClosure is not valid,
     * or with status {@code 500 (Internal Server Error)} if the establishmentClosure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/establishment-closures/{id}")
    public ResponseEntity<EstablishmentClosure> updateEstablishmentClosure(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstablishmentClosure establishmentClosure
    ) throws URISyntaxException {
        log.debug("REST request to update EstablishmentClosure : {}, {}", id, establishmentClosure);
        if (establishmentClosure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, establishmentClosure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!establishmentClosureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstablishmentClosure result = establishmentClosureRepository.save(establishmentClosure);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, establishmentClosure.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /establishment-closures/:id} : Partial updates given fields of an existing establishmentClosure, field will ignore if it is null
     *
     * @param id the id of the establishmentClosure to save.
     * @param establishmentClosure the establishmentClosure to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated establishmentClosure,
     * or with status {@code 400 (Bad Request)} if the establishmentClosure is not valid,
     * or with status {@code 404 (Not Found)} if the establishmentClosure is not found,
     * or with status {@code 500 (Internal Server Error)} if the establishmentClosure couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/establishment-closures/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EstablishmentClosure> partialUpdateEstablishmentClosure(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstablishmentClosure establishmentClosure
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstablishmentClosure partially : {}, {}", id, establishmentClosure);
        if (establishmentClosure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, establishmentClosure.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!establishmentClosureRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstablishmentClosure> result = establishmentClosureRepository
            .findById(establishmentClosure.getId())
            .map(
                existingEstablishmentClosure -> {
                    if (establishmentClosure.getStartDate() != null) {
                        existingEstablishmentClosure.setStartDate(establishmentClosure.getStartDate());
                    }
                    if (establishmentClosure.getEndDate() != null) {
                        existingEstablishmentClosure.setEndDate(establishmentClosure.getEndDate());
                    }
                    if (establishmentClosure.getCause() != null) {
                        existingEstablishmentClosure.setCause(establishmentClosure.getCause());
                    }

                    return existingEstablishmentClosure;
                }
            )
            .map(establishmentClosureRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, establishmentClosure.getId().toString())
        );
    }

    /**
     * {@code GET  /establishment-closures} : get all the establishmentClosures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of establishmentClosures in body.
     */
    @GetMapping("/establishment-closures")
    public List<EstablishmentClosure> getAllEstablishmentClosures() {
        log.debug("REST request to get all EstablishmentClosures");
        return establishmentClosureRepository.findAll();
    }

    /**
     * {@code GET  /establishment-closures/:id} : get the "id" establishmentClosure.
     *
     * @param id the id of the establishmentClosure to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the establishmentClosure, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/establishment-closures/{id}")
    public ResponseEntity<EstablishmentClosure> getEstablishmentClosure(@PathVariable Long id) {
        log.debug("REST request to get EstablishmentClosure : {}", id);
        Optional<EstablishmentClosure> establishmentClosure = establishmentClosureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(establishmentClosure);
    }

    /**
     * {@code DELETE  /establishment-closures/:id} : delete the "id" establishmentClosure.
     *
     * @param id the id of the establishmentClosure to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/establishment-closures/{id}")
    public ResponseEntity<Void> deleteEstablishmentClosure(@PathVariable Long id) {
        log.debug("REST request to delete EstablishmentClosure : {}", id);
        establishmentClosureRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
