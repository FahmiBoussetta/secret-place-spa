package com.yamibo.secretplacespa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yamibo.secretplacespa.IntegrationTest;
import com.yamibo.secretplacespa.domain.Manage;
import com.yamibo.secretplacespa.repository.ManageRepository;
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
 * Integration tests for the {@link ManageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ManageResourceIT {

    private static final String ENTITY_API_URL = "/api/manages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ManageRepository manageRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManageMockMvc;

    private Manage manage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manage createEntity(EntityManager em) {
        Manage manage = new Manage();
        return manage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manage createUpdatedEntity(EntityManager em) {
        Manage manage = new Manage();
        return manage;
    }

    @BeforeEach
    public void initTest() {
        manage = createEntity(em);
    }

    @Test
    @Transactional
    void createManage() throws Exception {
        int databaseSizeBeforeCreate = manageRepository.findAll().size();
        // Create the Manage
        restManageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isCreated());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeCreate + 1);
        Manage testManage = manageList.get(manageList.size() - 1);
    }

    @Test
    @Transactional
    void createManageWithExistingId() throws Exception {
        // Create the Manage with an existing ID
        manage.setId(1L);

        int databaseSizeBeforeCreate = manageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isBadRequest());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllManages() throws Exception {
        // Initialize the database
        manageRepository.saveAndFlush(manage);

        // Get all the manageList
        restManageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manage.getId().intValue())));
    }

    @Test
    @Transactional
    void getManage() throws Exception {
        // Initialize the database
        manageRepository.saveAndFlush(manage);

        // Get the manage
        restManageMockMvc
            .perform(get(ENTITY_API_URL_ID, manage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(manage.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingManage() throws Exception {
        // Get the manage
        restManageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewManage() throws Exception {
        // Initialize the database
        manageRepository.saveAndFlush(manage);

        int databaseSizeBeforeUpdate = manageRepository.findAll().size();

        // Update the manage
        Manage updatedManage = manageRepository.findById(manage.getId()).get();
        // Disconnect from session so that the updates on updatedManage are not directly saved in db
        em.detach(updatedManage);

        restManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedManage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedManage))
            )
            .andExpect(status().isOk());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
        Manage testManage = manageList.get(manageList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingManage() throws Exception {
        int databaseSizeBeforeUpdate = manageRepository.findAll().size();
        manage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, manage.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(manage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManage() throws Exception {
        int databaseSizeBeforeUpdate = manageRepository.findAll().size();
        manage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(manage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManage() throws Exception {
        int databaseSizeBeforeUpdate = manageRepository.findAll().size();
        manage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManageWithPatch() throws Exception {
        // Initialize the database
        manageRepository.saveAndFlush(manage);

        int databaseSizeBeforeUpdate = manageRepository.findAll().size();

        // Update the manage using partial update
        Manage partialUpdatedManage = new Manage();
        partialUpdatedManage.setId(manage.getId());

        restManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManage))
            )
            .andExpect(status().isOk());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
        Manage testManage = manageList.get(manageList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateManageWithPatch() throws Exception {
        // Initialize the database
        manageRepository.saveAndFlush(manage);

        int databaseSizeBeforeUpdate = manageRepository.findAll().size();

        // Update the manage using partial update
        Manage partialUpdatedManage = new Manage();
        partialUpdatedManage.setId(manage.getId());

        restManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManage))
            )
            .andExpect(status().isOk());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
        Manage testManage = manageList.get(manageList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingManage() throws Exception {
        int databaseSizeBeforeUpdate = manageRepository.findAll().size();
        manage.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, manage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(manage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManage() throws Exception {
        int databaseSizeBeforeUpdate = manageRepository.findAll().size();
        manage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(manage))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManage() throws Exception {
        int databaseSizeBeforeUpdate = manageRepository.findAll().size();
        manage.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(manage)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Manage in the database
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManage() throws Exception {
        // Initialize the database
        manageRepository.saveAndFlush(manage);

        int databaseSizeBeforeDelete = manageRepository.findAll().size();

        // Delete the manage
        restManageMockMvc
            .perform(delete(ENTITY_API_URL_ID, manage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Manage> manageList = manageRepository.findAll();
        assertThat(manageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
