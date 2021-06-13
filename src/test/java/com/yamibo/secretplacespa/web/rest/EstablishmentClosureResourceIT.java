package com.yamibo.secretplacespa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yamibo.secretplacespa.IntegrationTest;
import com.yamibo.secretplacespa.domain.EstablishmentClosure;
import com.yamibo.secretplacespa.repository.EstablishmentClosureRepository;
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
 * Integration tests for the {@link EstablishmentClosureResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstablishmentClosureResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/establishment-closures";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstablishmentClosureRepository establishmentClosureRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstablishmentClosureMockMvc;

    private EstablishmentClosure establishmentClosure;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstablishmentClosure createEntity(EntityManager em) {
        EstablishmentClosure establishmentClosure = new EstablishmentClosure()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .cause(DEFAULT_CAUSE);
        return establishmentClosure;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstablishmentClosure createUpdatedEntity(EntityManager em) {
        EstablishmentClosure establishmentClosure = new EstablishmentClosure()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .cause(UPDATED_CAUSE);
        return establishmentClosure;
    }

    @BeforeEach
    public void initTest() {
        establishmentClosure = createEntity(em);
    }

    @Test
    @Transactional
    void createEstablishmentClosure() throws Exception {
        int databaseSizeBeforeCreate = establishmentClosureRepository.findAll().size();
        // Create the EstablishmentClosure
        restEstablishmentClosureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isCreated());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeCreate + 1);
        EstablishmentClosure testEstablishmentClosure = establishmentClosureList.get(establishmentClosureList.size() - 1);
        assertThat(testEstablishmentClosure.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEstablishmentClosure.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEstablishmentClosure.getCause()).isEqualTo(DEFAULT_CAUSE);
    }

    @Test
    @Transactional
    void createEstablishmentClosureWithExistingId() throws Exception {
        // Create the EstablishmentClosure with an existing ID
        establishmentClosure.setId(1L);

        int databaseSizeBeforeCreate = establishmentClosureRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstablishmentClosureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentClosureRepository.findAll().size();
        // set the field null
        establishmentClosure.setStartDate(null);

        // Create the EstablishmentClosure, which fails.

        restEstablishmentClosureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isBadRequest());

        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentClosureRepository.findAll().size();
        // set the field null
        establishmentClosure.setEndDate(null);

        // Create the EstablishmentClosure, which fails.

        restEstablishmentClosureMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isBadRequest());

        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstablishmentClosures() throws Exception {
        // Initialize the database
        establishmentClosureRepository.saveAndFlush(establishmentClosure);

        // Get all the establishmentClosureList
        restEstablishmentClosureMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establishmentClosure.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)));
    }

    @Test
    @Transactional
    void getEstablishmentClosure() throws Exception {
        // Initialize the database
        establishmentClosureRepository.saveAndFlush(establishmentClosure);

        // Get the establishmentClosure
        restEstablishmentClosureMockMvc
            .perform(get(ENTITY_API_URL_ID, establishmentClosure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(establishmentClosure.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE));
    }

    @Test
    @Transactional
    void getNonExistingEstablishmentClosure() throws Exception {
        // Get the establishmentClosure
        restEstablishmentClosureMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstablishmentClosure() throws Exception {
        // Initialize the database
        establishmentClosureRepository.saveAndFlush(establishmentClosure);

        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();

        // Update the establishmentClosure
        EstablishmentClosure updatedEstablishmentClosure = establishmentClosureRepository.findById(establishmentClosure.getId()).get();
        // Disconnect from session so that the updates on updatedEstablishmentClosure are not directly saved in db
        em.detach(updatedEstablishmentClosure);
        updatedEstablishmentClosure.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).cause(UPDATED_CAUSE);

        restEstablishmentClosureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstablishmentClosure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstablishmentClosure))
            )
            .andExpect(status().isOk());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
        EstablishmentClosure testEstablishmentClosure = establishmentClosureList.get(establishmentClosureList.size() - 1);
        assertThat(testEstablishmentClosure.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEstablishmentClosure.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEstablishmentClosure.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    void putNonExistingEstablishmentClosure() throws Exception {
        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();
        establishmentClosure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentClosureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, establishmentClosure.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstablishmentClosure() throws Exception {
        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();
        establishmentClosure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentClosureMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstablishmentClosure() throws Exception {
        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();
        establishmentClosure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentClosureMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstablishmentClosureWithPatch() throws Exception {
        // Initialize the database
        establishmentClosureRepository.saveAndFlush(establishmentClosure);

        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();

        // Update the establishmentClosure using partial update
        EstablishmentClosure partialUpdatedEstablishmentClosure = new EstablishmentClosure();
        partialUpdatedEstablishmentClosure.setId(establishmentClosure.getId());

        partialUpdatedEstablishmentClosure.startDate(UPDATED_START_DATE).cause(UPDATED_CAUSE);

        restEstablishmentClosureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablishmentClosure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstablishmentClosure))
            )
            .andExpect(status().isOk());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
        EstablishmentClosure testEstablishmentClosure = establishmentClosureList.get(establishmentClosureList.size() - 1);
        assertThat(testEstablishmentClosure.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEstablishmentClosure.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEstablishmentClosure.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    void fullUpdateEstablishmentClosureWithPatch() throws Exception {
        // Initialize the database
        establishmentClosureRepository.saveAndFlush(establishmentClosure);

        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();

        // Update the establishmentClosure using partial update
        EstablishmentClosure partialUpdatedEstablishmentClosure = new EstablishmentClosure();
        partialUpdatedEstablishmentClosure.setId(establishmentClosure.getId());

        partialUpdatedEstablishmentClosure.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).cause(UPDATED_CAUSE);

        restEstablishmentClosureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablishmentClosure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstablishmentClosure))
            )
            .andExpect(status().isOk());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
        EstablishmentClosure testEstablishmentClosure = establishmentClosureList.get(establishmentClosureList.size() - 1);
        assertThat(testEstablishmentClosure.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEstablishmentClosure.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEstablishmentClosure.getCause()).isEqualTo(UPDATED_CAUSE);
    }

    @Test
    @Transactional
    void patchNonExistingEstablishmentClosure() throws Exception {
        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();
        establishmentClosure.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentClosureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, establishmentClosure.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstablishmentClosure() throws Exception {
        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();
        establishmentClosure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentClosureMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstablishmentClosure() throws Exception {
        int databaseSizeBeforeUpdate = establishmentClosureRepository.findAll().size();
        establishmentClosure.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentClosureMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishmentClosure))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstablishmentClosure in the database
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstablishmentClosure() throws Exception {
        // Initialize the database
        establishmentClosureRepository.saveAndFlush(establishmentClosure);

        int databaseSizeBeforeDelete = establishmentClosureRepository.findAll().size();

        // Delete the establishmentClosure
        restEstablishmentClosureMockMvc
            .perform(delete(ENTITY_API_URL_ID, establishmentClosure.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstablishmentClosure> establishmentClosureList = establishmentClosureRepository.findAll();
        assertThat(establishmentClosureList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
