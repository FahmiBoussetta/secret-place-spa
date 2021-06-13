package com.yamibo.secretplacespa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yamibo.secretplacespa.IntegrationTest;
import com.yamibo.secretplacespa.domain.HousingTemplate;
import com.yamibo.secretplacespa.domain.enumeration.HousingType;
import com.yamibo.secretplacespa.repository.HousingTemplateRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link HousingTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HousingTemplateResourceIT {

    private static final HousingType DEFAULT_HOUSING_TYPE = HousingType.SPA_PREMIUM;
    private static final HousingType UPDATED_HOUSING_TYPE = HousingType.SPA;

    private static final Integer DEFAULT_NB_OF_UNIT = 1;
    private static final Integer UPDATED_NB_OF_UNIT = 2;

    private static final Integer DEFAULT_NB_MAX_OF_OCCUPANTS = 1;
    private static final Integer UPDATED_NB_MAX_OF_OCCUPANTS = 2;

    private static final Float DEFAULT_PRICE = 1F;
    private static final Float UPDATED_PRICE = 2F;

    private static final String ENTITY_API_URL = "/api/housing-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HousingTemplateRepository housingTemplateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHousingTemplateMockMvc;

    private HousingTemplate housingTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HousingTemplate createEntity(EntityManager em) {
        HousingTemplate housingTemplate = new HousingTemplate()
            .housingType(DEFAULT_HOUSING_TYPE)
            .nbOfUnit(DEFAULT_NB_OF_UNIT)
            .nbMaxOfOccupants(DEFAULT_NB_MAX_OF_OCCUPANTS)
            .price(DEFAULT_PRICE);
        return housingTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HousingTemplate createUpdatedEntity(EntityManager em) {
        HousingTemplate housingTemplate = new HousingTemplate()
            .housingType(UPDATED_HOUSING_TYPE)
            .nbOfUnit(UPDATED_NB_OF_UNIT)
            .nbMaxOfOccupants(UPDATED_NB_MAX_OF_OCCUPANTS)
            .price(UPDATED_PRICE);
        return housingTemplate;
    }

    @BeforeEach
    public void initTest() {
        housingTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createHousingTemplate() throws Exception {
        int databaseSizeBeforeCreate = housingTemplateRepository.findAll().size();
        // Create the HousingTemplate
        restHousingTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isCreated());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        HousingTemplate testHousingTemplate = housingTemplateList.get(housingTemplateList.size() - 1);
        assertThat(testHousingTemplate.getHousingType()).isEqualTo(DEFAULT_HOUSING_TYPE);
        assertThat(testHousingTemplate.getNbOfUnit()).isEqualTo(DEFAULT_NB_OF_UNIT);
        assertThat(testHousingTemplate.getNbMaxOfOccupants()).isEqualTo(DEFAULT_NB_MAX_OF_OCCUPANTS);
        assertThat(testHousingTemplate.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createHousingTemplateWithExistingId() throws Exception {
        // Create the HousingTemplate with an existing ID
        housingTemplate.setId(1L);

        int databaseSizeBeforeCreate = housingTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHousingTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHousingTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingTemplateRepository.findAll().size();
        // set the field null
        housingTemplate.setHousingType(null);

        // Create the HousingTemplate, which fails.

        restHousingTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbOfUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingTemplateRepository.findAll().size();
        // set the field null
        housingTemplate.setNbOfUnit(null);

        // Create the HousingTemplate, which fails.

        restHousingTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbMaxOfOccupantsIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingTemplateRepository.findAll().size();
        // set the field null
        housingTemplate.setNbMaxOfOccupants(null);

        // Create the HousingTemplate, which fails.

        restHousingTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingTemplateRepository.findAll().size();
        // set the field null
        housingTemplate.setPrice(null);

        // Create the HousingTemplate, which fails.

        restHousingTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHousingTemplates() throws Exception {
        // Initialize the database
        housingTemplateRepository.saveAndFlush(housingTemplate);

        // Get all the housingTemplateList
        restHousingTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(housingTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].housingType").value(hasItem(DEFAULT_HOUSING_TYPE.toString())))
            .andExpect(jsonPath("$.[*].nbOfUnit").value(hasItem(DEFAULT_NB_OF_UNIT)))
            .andExpect(jsonPath("$.[*].nbMaxOfOccupants").value(hasItem(DEFAULT_NB_MAX_OF_OCCUPANTS)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getHousingTemplate() throws Exception {
        // Initialize the database
        housingTemplateRepository.saveAndFlush(housingTemplate);

        // Get the housingTemplate
        restHousingTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, housingTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(housingTemplate.getId().intValue()))
            .andExpect(jsonPath("$.housingType").value(DEFAULT_HOUSING_TYPE.toString()))
            .andExpect(jsonPath("$.nbOfUnit").value(DEFAULT_NB_OF_UNIT))
            .andExpect(jsonPath("$.nbMaxOfOccupants").value(DEFAULT_NB_MAX_OF_OCCUPANTS))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingHousingTemplate() throws Exception {
        // Get the housingTemplate
        restHousingTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHousingTemplate() throws Exception {
        // Initialize the database
        housingTemplateRepository.saveAndFlush(housingTemplate);

        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();

        // Update the housingTemplate
        HousingTemplate updatedHousingTemplate = housingTemplateRepository.findById(housingTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedHousingTemplate are not directly saved in db
        em.detach(updatedHousingTemplate);
        updatedHousingTemplate
            .housingType(UPDATED_HOUSING_TYPE)
            .nbOfUnit(UPDATED_NB_OF_UNIT)
            .nbMaxOfOccupants(UPDATED_NB_MAX_OF_OCCUPANTS)
            .price(UPDATED_PRICE);

        restHousingTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHousingTemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHousingTemplate))
            )
            .andExpect(status().isOk());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
        HousingTemplate testHousingTemplate = housingTemplateList.get(housingTemplateList.size() - 1);
        assertThat(testHousingTemplate.getHousingType()).isEqualTo(UPDATED_HOUSING_TYPE);
        assertThat(testHousingTemplate.getNbOfUnit()).isEqualTo(UPDATED_NB_OF_UNIT);
        assertThat(testHousingTemplate.getNbMaxOfOccupants()).isEqualTo(UPDATED_NB_MAX_OF_OCCUPANTS);
        assertThat(testHousingTemplate.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingHousingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();
        housingTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, housingTemplate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHousingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();
        housingTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHousingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();
        housingTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingTemplateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHousingTemplateWithPatch() throws Exception {
        // Initialize the database
        housingTemplateRepository.saveAndFlush(housingTemplate);

        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();

        // Update the housingTemplate using partial update
        HousingTemplate partialUpdatedHousingTemplate = new HousingTemplate();
        partialUpdatedHousingTemplate.setId(housingTemplate.getId());

        partialUpdatedHousingTemplate.nbOfUnit(UPDATED_NB_OF_UNIT);

        restHousingTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHousingTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHousingTemplate))
            )
            .andExpect(status().isOk());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
        HousingTemplate testHousingTemplate = housingTemplateList.get(housingTemplateList.size() - 1);
        assertThat(testHousingTemplate.getHousingType()).isEqualTo(DEFAULT_HOUSING_TYPE);
        assertThat(testHousingTemplate.getNbOfUnit()).isEqualTo(UPDATED_NB_OF_UNIT);
        assertThat(testHousingTemplate.getNbMaxOfOccupants()).isEqualTo(DEFAULT_NB_MAX_OF_OCCUPANTS);
        assertThat(testHousingTemplate.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateHousingTemplateWithPatch() throws Exception {
        // Initialize the database
        housingTemplateRepository.saveAndFlush(housingTemplate);

        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();

        // Update the housingTemplate using partial update
        HousingTemplate partialUpdatedHousingTemplate = new HousingTemplate();
        partialUpdatedHousingTemplate.setId(housingTemplate.getId());

        partialUpdatedHousingTemplate
            .housingType(UPDATED_HOUSING_TYPE)
            .nbOfUnit(UPDATED_NB_OF_UNIT)
            .nbMaxOfOccupants(UPDATED_NB_MAX_OF_OCCUPANTS)
            .price(UPDATED_PRICE);

        restHousingTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHousingTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHousingTemplate))
            )
            .andExpect(status().isOk());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
        HousingTemplate testHousingTemplate = housingTemplateList.get(housingTemplateList.size() - 1);
        assertThat(testHousingTemplate.getHousingType()).isEqualTo(UPDATED_HOUSING_TYPE);
        assertThat(testHousingTemplate.getNbOfUnit()).isEqualTo(UPDATED_NB_OF_UNIT);
        assertThat(testHousingTemplate.getNbMaxOfOccupants()).isEqualTo(UPDATED_NB_MAX_OF_OCCUPANTS);
        assertThat(testHousingTemplate.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingHousingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();
        housingTemplate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, housingTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHousingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();
        housingTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHousingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = housingTemplateRepository.findAll().size();
        housingTemplate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingTemplate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HousingTemplate in the database
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHousingTemplate() throws Exception {
        // Initialize the database
        housingTemplateRepository.saveAndFlush(housingTemplate);

        int databaseSizeBeforeDelete = housingTemplateRepository.findAll().size();

        // Delete the housingTemplate
        restHousingTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, housingTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HousingTemplate> housingTemplateList = housingTemplateRepository.findAll();
        assertThat(housingTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
