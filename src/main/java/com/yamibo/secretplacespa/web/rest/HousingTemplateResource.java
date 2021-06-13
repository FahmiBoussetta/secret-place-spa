package com.yamibo.secretplacespa.web.rest;

import com.yamibo.secretplacespa.domain.HousingTemplate;
import com.yamibo.secretplacespa.repository.HousingTemplateRepository;
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
 * REST controller for managing {@link com.yamibo.secretplacespa.domain.HousingTemplate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HousingTemplateResource {

    private final Logger log = LoggerFactory.getLogger(HousingTemplateResource.class);

    private static final String ENTITY_NAME = "housingTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HousingTemplateRepository housingTemplateRepository;

    public HousingTemplateResource(HousingTemplateRepository housingTemplateRepository) {
        this.housingTemplateRepository = housingTemplateRepository;
    }

    /**
     * {@code POST  /housing-templates} : Create a new housingTemplate.
     *
     * @param housingTemplate the housingTemplate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new housingTemplate, or with status {@code 400 (Bad Request)} if the housingTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/housing-templates")
    public ResponseEntity<HousingTemplate> createHousingTemplate(@Valid @RequestBody HousingTemplate housingTemplate)
        throws URISyntaxException {
        log.debug("REST request to save HousingTemplate : {}", housingTemplate);
        if (housingTemplate.getId() != null) {
            throw new BadRequestAlertException("A new housingTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HousingTemplate result = housingTemplateRepository.save(housingTemplate);
        return ResponseEntity
            .created(new URI("/api/housing-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /housing-templates/:id} : Updates an existing housingTemplate.
     *
     * @param id the id of the housingTemplate to save.
     * @param housingTemplate the housingTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingTemplate,
     * or with status {@code 400 (Bad Request)} if the housingTemplate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the housingTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/housing-templates/{id}")
    public ResponseEntity<HousingTemplate> updateHousingTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HousingTemplate housingTemplate
    ) throws URISyntaxException {
        log.debug("REST request to update HousingTemplate : {}, {}", id, housingTemplate);
        if (housingTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, housingTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!housingTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HousingTemplate result = housingTemplateRepository.save(housingTemplate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingTemplate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /housing-templates/:id} : Partial updates given fields of an existing housingTemplate, field will ignore if it is null
     *
     * @param id the id of the housingTemplate to save.
     * @param housingTemplate the housingTemplate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated housingTemplate,
     * or with status {@code 400 (Bad Request)} if the housingTemplate is not valid,
     * or with status {@code 404 (Not Found)} if the housingTemplate is not found,
     * or with status {@code 500 (Internal Server Error)} if the housingTemplate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/housing-templates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<HousingTemplate> partialUpdateHousingTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HousingTemplate housingTemplate
    ) throws URISyntaxException {
        log.debug("REST request to partial update HousingTemplate partially : {}, {}", id, housingTemplate);
        if (housingTemplate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, housingTemplate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!housingTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HousingTemplate> result = housingTemplateRepository
            .findById(housingTemplate.getId())
            .map(
                existingHousingTemplate -> {
                    if (housingTemplate.getHousingType() != null) {
                        existingHousingTemplate.setHousingType(housingTemplate.getHousingType());
                    }
                    if (housingTemplate.getNbOfUnit() != null) {
                        existingHousingTemplate.setNbOfUnit(housingTemplate.getNbOfUnit());
                    }
                    if (housingTemplate.getNbMaxOfOccupants() != null) {
                        existingHousingTemplate.setNbMaxOfOccupants(housingTemplate.getNbMaxOfOccupants());
                    }
                    if (housingTemplate.getPrice() != null) {
                        existingHousingTemplate.setPrice(housingTemplate.getPrice());
                    }

                    return existingHousingTemplate;
                }
            )
            .map(housingTemplateRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, housingTemplate.getId().toString())
        );
    }

    /**
     * {@code GET  /housing-templates} : get all the housingTemplates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of housingTemplates in body.
     */
    @GetMapping("/housing-templates")
    public List<HousingTemplate> getAllHousingTemplates() {
        log.debug("REST request to get all HousingTemplates");
        return housingTemplateRepository.findAll();
    }

    /**
     * {@code GET  /housing-templates/:id} : get the "id" housingTemplate.
     *
     * @param id the id of the housingTemplate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the housingTemplate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/housing-templates/{id}")
    public ResponseEntity<HousingTemplate> getHousingTemplate(@PathVariable Long id) {
        log.debug("REST request to get HousingTemplate : {}", id);
        Optional<HousingTemplate> housingTemplate = housingTemplateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(housingTemplate);
    }

    /**
     * {@code DELETE  /housing-templates/:id} : delete the "id" housingTemplate.
     *
     * @param id the id of the housingTemplate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/housing-templates/{id}")
    public ResponseEntity<Void> deleteHousingTemplate(@PathVariable Long id) {
        log.debug("REST request to delete HousingTemplate : {}", id);
        housingTemplateRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
