package com.yamibo.secretplacespa.web.rest;

import com.yamibo.secretplacespa.domain.Establishment;
import com.yamibo.secretplacespa.repository.EstablishmentRepository;
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
 * REST controller for managing {@link com.yamibo.secretplacespa.domain.Establishment}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EstablishmentResource {

    private final Logger log = LoggerFactory.getLogger(EstablishmentResource.class);

    private static final String ENTITY_NAME = "establishment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstablishmentRepository establishmentRepository;

    public EstablishmentResource(EstablishmentRepository establishmentRepository) {
        this.establishmentRepository = establishmentRepository;
    }

    /**
     * {@code POST  /establishments} : Create a new establishment.
     *
     * @param establishment the establishment to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new establishment, or with status {@code 400 (Bad Request)} if the establishment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/establishments")
    public ResponseEntity<Establishment> createEstablishment(@Valid @RequestBody Establishment establishment) throws URISyntaxException {
        log.debug("REST request to save Establishment : {}", establishment);
        if (establishment.getId() != null) {
            throw new BadRequestAlertException("A new establishment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Establishment result = establishmentRepository.save(establishment);
        return ResponseEntity
            .created(new URI("/api/establishments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /establishments/:id} : Updates an existing establishment.
     *
     * @param id the id of the establishment to save.
     * @param establishment the establishment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated establishment,
     * or with status {@code 400 (Bad Request)} if the establishment is not valid,
     * or with status {@code 500 (Internal Server Error)} if the establishment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/establishments/{id}")
    public ResponseEntity<Establishment> updateEstablishment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Establishment establishment
    ) throws URISyntaxException {
        log.debug("REST request to update Establishment : {}, {}", id, establishment);
        if (establishment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, establishment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!establishmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Establishment result = establishmentRepository.save(establishment);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, establishment.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /establishments/:id} : Partial updates given fields of an existing establishment, field will ignore if it is null
     *
     * @param id the id of the establishment to save.
     * @param establishment the establishment to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated establishment,
     * or with status {@code 400 (Bad Request)} if the establishment is not valid,
     * or with status {@code 404 (Not Found)} if the establishment is not found,
     * or with status {@code 500 (Internal Server Error)} if the establishment couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/establishments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Establishment> partialUpdateEstablishment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Establishment establishment
    ) throws URISyntaxException {
        log.debug("REST request to partial update Establishment partially : {}, {}", id, establishment);
        if (establishment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, establishment.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!establishmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Establishment> result = establishmentRepository
            .findById(establishment.getId())
            .map(
                existingEstablishment -> {
                    if (establishment.getName() != null) {
                        existingEstablishment.setName(establishment.getName());
                    }
                    if (establishment.getAddress() != null) {
                        existingEstablishment.setAddress(establishment.getAddress());
                    }
                    if (establishment.getLatitude() != null) {
                        existingEstablishment.setLatitude(establishment.getLatitude());
                    }
                    if (establishment.getLongitude() != null) {
                        existingEstablishment.setLongitude(establishment.getLongitude());
                    }
                    if (establishment.getGlobalRate() != null) {
                        existingEstablishment.setGlobalRate(establishment.getGlobalRate());
                    }
                    if (establishment.getEstablishmentType() != null) {
                        existingEstablishment.setEstablishmentType(establishment.getEstablishmentType());
                    }
                    if (establishment.getHasParking() != null) {
                        existingEstablishment.setHasParking(establishment.getHasParking());
                    }
                    if (establishment.getHasRestaurant() != null) {
                        existingEstablishment.setHasRestaurant(establishment.getHasRestaurant());
                    }
                    if (establishment.getHasFreeWifi() != null) {
                        existingEstablishment.setHasFreeWifi(establishment.getHasFreeWifi());
                    }
                    if (establishment.getDescription() != null) {
                        existingEstablishment.setDescription(establishment.getDescription());
                    }

                    return existingEstablishment;
                }
            )
            .map(establishmentRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, establishment.getId().toString())
        );
    }

    /**
     * {@code GET  /establishments} : get all the establishments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of establishments in body.
     */
    @GetMapping("/establishments")
    public List<Establishment> getAllEstablishments() {
        log.debug("REST request to get all Establishments");
        return establishmentRepository.findAll();
    }

    /**
     * {@code GET  /establishments/:id} : get the "id" establishment.
     *
     * @param id the id of the establishment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the establishment, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/establishments/{id}")
    public ResponseEntity<Establishment> getEstablishment(@PathVariable Long id) {
        log.debug("REST request to get Establishment : {}", id);
        Optional<Establishment> establishment = establishmentRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(establishment);
    }

    /**
     * {@code DELETE  /establishments/:id} : delete the "id" establishment.
     *
     * @param id the id of the establishment to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/establishments/{id}")
    public ResponseEntity<Void> deleteEstablishment(@PathVariable Long id) {
        log.debug("REST request to delete Establishment : {}", id);
        establishmentRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
