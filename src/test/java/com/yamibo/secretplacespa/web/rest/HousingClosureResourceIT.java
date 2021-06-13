package com.yamibo.secretplacespa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yamibo.secretplacespa.IntegrationTest;
import com.yamibo.secretplacespa.domain.HousingClosure;
import com.yamibo.secretplacespa.repository.HousingClosureRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link HousingClosureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class HousingClosureResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/housing-closures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HousingClosureRepository housingClosureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHousingClosureMockMvc;

    private HousingClosure housingClosure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HousingClosure createEntity(EntityManager em) {
        HousingClosure housingClosure = new HousingClosure().startDate(DEFAULT_START_DATE).endDate(DEFAULT_END_DATE).cause(DEFAULT_CAUSE);
        return housingClosure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HousingClosure createUpdatedEntity(EntityManager em) {
        HousingClosure housingClosure = new HousingClosure().startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).cause(UPDATED_CAUSE);
        return housingClosure;
    }

    @BeforeEach
    public void initTest() {
        housingClosure = createEntity(em);
    }

    @Test
    @Transactional
    void createHousingClosure() throws Exception {
        int databaseSizeBeforeCreate = housingClosureRepository.findAll().size();
        // Create the HousingClosure
        restHousingClosureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isCreated());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeCreate + 1);
        HousingClosure testHousingClosure = housingClosureList.get(housingClosureList.size() - 1);
        assertThat(testHousingClosure.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testHousingClosure.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testHousingClosure.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    void createHousingClosureWithExistingId() throws Exception {
        // Create the HousingClosure with an existing ID
        housingClosure.setId(1L);

        int databaseSizeBeforeCreate = housingClosureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHousingClosureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingClosureRepository.findAll().size();
        // set the field null
        housingClosure.setStartDate(null);

        // Create the HousingClosure, which fails.

        restHousingClosureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isBadRequest());

        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = housingClosureRepository.findAll().size();
        // set the field null
        housingClosure.setEndDate(null);

        // Create the HousingClosure, which fails.

        restHousingClosureMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isBadRequest());

        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHousingClosures() throws Exception {
        // Initialize the database
        housingClosureRepository.saveAndFlush(housingClosure);

        // Get all the housingClosureList
        restHousingClosureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(housingClosure.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)));
    }

    @Test
    @Transactional
    void getHousingClosure() throws Exception {
        // Initialize the database
        housingClosureRepository.saveAndFlush(housingClosure);

        // Get the housingClosure
        restHousingClosureMockMvc
            .perform(get(ENTITY_API_URL_ID, housingClosure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(housingClosure.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE));
    }

    @Test
    @Transactional
    void getNonExistingHousingClosure() throws Exception {
        // Get the housingClosure
        restHousingClosureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHousingClosure() throws Exception {
        // Initialize the database
        housingClosureRepository.saveAndFlush(housingClosure);

        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();

        // Update the housingClosure
        HousingClosure updatedHousingClosure = housingClosureRepository.findById(housingClosure.getId()).get();
        // Disconnect from session so that the updates on updatedHousingClosure are not directly saved in db
        em.detach(updatedHousingClosure);
        updatedHousingClosure.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).cause(UPDATED_CAUSE);

        restHousingClosureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHousingClosure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHousingClosure))
            )
            .andExpect(status().isOk());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
        HousingClosure testHousingClosure = housingClosureList.get(housingClosureList.size() - 1);
        assertThat(testHousingClosure.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHousingClosure.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHousingClosure.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    void putNonExistingHousingClosure() throws Exception {
        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();
        housingClosure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingClosureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, housingClosure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHousingClosure() throws Exception {
        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();
        housingClosure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingClosureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHousingClosure() throws Exception {
        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();
        housingClosure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingClosureMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(housingClosure)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHousingClosureWithPatch() throws Exception {
        // Initialize the database
        housingClosureRepository.saveAndFlush(housingClosure);

        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();

        // Update the housingClosure using partial update
        HousingClosure partialUpdatedHousingClosure = new HousingClosure();
        partialUpdatedHousingClosure.setId(housingClosure.getId());

        partialUpdatedHousingClosure.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).cause(UPDATED_CAUSE);

        restHousingClosureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHousingClosure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHousingClosure))
            )
            .andExpect(status().isOk());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
        HousingClosure testHousingClosure = housingClosureList.get(housingClosureList.size() - 1);
        assertThat(testHousingClosure.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHousingClosure.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHousingClosure.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    void fullUpdateHousingClosureWithPatch() throws Exception {
        // Initialize the database
        housingClosureRepository.saveAndFlush(housingClosure);

        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();

        // Update the housingClosure using partial update
        HousingClosure partialUpdatedHousingClosure = new HousingClosure();
        partialUpdatedHousingClosure.setId(housingClosure.getId());

        partialUpdatedHousingClosure.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).cause(UPDATED_CAUSE);

        restHousingClosureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHousingClosure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHousingClosure))
            )
            .andExpect(status().isOk());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
        HousingClosure testHousingClosure = housingClosureList.get(housingClosureList.size() - 1);
        assertThat(testHousingClosure.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testHousingClosure.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testHousingClosure.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    void patchNonExistingHousingClosure() throws Exception {
        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();
        housingClosure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHousingClosureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, housingClosure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHousingClosure() throws Exception {
        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();
        housingClosure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingClosureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHousingClosure() throws Exception {
        int databaseSizeBeforeUpdate = housingClosureRepository.findAll().size();
        housingClosure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHousingClosureMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(housingClosure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HousingClosure in the database
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHousingClosure() throws Exception {
        // Initialize the database
        housingClosureRepository.saveAndFlush(housingClosure);

        int databaseSizeBeforeDelete = housingClosureRepository.findAll().size();

        // Delete the housingClosure
        restHousingClosureMockMvc
            .perform(delete(ENTITY_API_URL_ID, housingClosure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HousingClosure> housingClosureList = housingClosureRepository.findAll();
        assertThat(housingClosureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
