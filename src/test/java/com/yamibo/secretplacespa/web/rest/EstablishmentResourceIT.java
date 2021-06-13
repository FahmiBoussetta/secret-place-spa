package com.yamibo.secretplacespa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yamibo.secretplacespa.IntegrationTest;
import com.yamibo.secretplacespa.domain.Establishment;
import com.yamibo.secretplacespa.domain.enumeration.EstablishmentType;
import com.yamibo.secretplacespa.repository.EstablishmentRepository;
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
 * Integration tests for the {@link EstablishmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstablishmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final Float DEFAULT_GLOBAL_RATE = 1F;
    private static final Float UPDATED_GLOBAL_RATE = 2F;

    private static final EstablishmentType DEFAULT_ESTABLISHMENT_TYPE = EstablishmentType.SECRETPLACESPA;
    private static final EstablishmentType UPDATED_ESTABLISHMENT_TYPE = EstablishmentType.SECRETPLACESPA;

    private static final Boolean DEFAULT_HAS_PARKING = false;
    private static final Boolean UPDATED_HAS_PARKING = true;

    private static final Boolean DEFAULT_HAS_RESTAURANT = false;
    private static final Boolean UPDATED_HAS_RESTAURANT = true;

    private static final Boolean DEFAULT_HAS_FREE_WIFI = false;
    private static final Boolean UPDATED_HAS_FREE_WIFI = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/establishments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstablishmentRepository establishmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstablishmentMockMvc;

    private Establishment establishment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Establishment createEntity(EntityManager em) {
        Establishment establishment = new Establishment()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .globalRate(DEFAULT_GLOBAL_RATE)
            .establishmentType(DEFAULT_ESTABLISHMENT_TYPE)
            .hasParking(DEFAULT_HAS_PARKING)
            .hasRestaurant(DEFAULT_HAS_RESTAURANT)
            .hasFreeWifi(DEFAULT_HAS_FREE_WIFI)
            .description(DEFAULT_DESCRIPTION);
        return establishment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Establishment createUpdatedEntity(EntityManager em) {
        Establishment establishment = new Establishment()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .globalRate(UPDATED_GLOBAL_RATE)
            .establishmentType(UPDATED_ESTABLISHMENT_TYPE)
            .hasParking(UPDATED_HAS_PARKING)
            .hasRestaurant(UPDATED_HAS_RESTAURANT)
            .hasFreeWifi(UPDATED_HAS_FREE_WIFI)
            .description(UPDATED_DESCRIPTION);
        return establishment;
    }

    @BeforeEach
    public void initTest() {
        establishment = createEntity(em);
    }

    @Test
    @Transactional
    void createEstablishment() throws Exception {
        int databaseSizeBeforeCreate = establishmentRepository.findAll().size();
        // Create the Establishment
        restEstablishmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isCreated());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeCreate + 1);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEstablishment.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testEstablishment.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testEstablishment.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testEstablishment.getGlobalRate()).isEqualTo(DEFAULT_GLOBAL_RATE);
        assertThat(testEstablishment.getEstablishmentType()).isEqualTo(DEFAULT_ESTABLISHMENT_TYPE);
        assertThat(testEstablishment.getHasParking()).isEqualTo(DEFAULT_HAS_PARKING);
        assertThat(testEstablishment.getHasRestaurant()).isEqualTo(DEFAULT_HAS_RESTAURANT);
        assertThat(testEstablishment.getHasFreeWifi()).isEqualTo(DEFAULT_HAS_FREE_WIFI);
        assertThat(testEstablishment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createEstablishmentWithExistingId() throws Exception {
        // Create the Establishment with an existing ID
        establishment.setId(1L);

        int databaseSizeBeforeCreate = establishmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstablishmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentRepository.findAll().size();
        // set the field null
        establishment.setName(null);

        // Create the Establishment, which fails.

        restEstablishmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentRepository.findAll().size();
        // set the field null
        establishment.setAddress(null);

        // Create the Establishment, which fails.

        restEstablishmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstablishmentTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentRepository.findAll().size();
        // set the field null
        establishment.setEstablishmentType(null);

        // Create the Establishment, which fails.

        restEstablishmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHasParkingIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentRepository.findAll().size();
        // set the field null
        establishment.setHasParking(null);

        // Create the Establishment, which fails.

        restEstablishmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHasRestaurantIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentRepository.findAll().size();
        // set the field null
        establishment.setHasRestaurant(null);

        // Create the Establishment, which fails.

        restEstablishmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHasFreeWifiIsRequired() throws Exception {
        int databaseSizeBeforeTest = establishmentRepository.findAll().size();
        // set the field null
        establishment.setHasFreeWifi(null);

        // Create the Establishment, which fails.

        restEstablishmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isBadRequest());

        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstablishments() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        // Get all the establishmentList
        restEstablishmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(establishment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].globalRate").value(hasItem(DEFAULT_GLOBAL_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].establishmentType").value(hasItem(DEFAULT_ESTABLISHMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].hasParking").value(hasItem(DEFAULT_HAS_PARKING.booleanValue())))
            .andExpect(jsonPath("$.[*].hasRestaurant").value(hasItem(DEFAULT_HAS_RESTAURANT.booleanValue())))
            .andExpect(jsonPath("$.[*].hasFreeWifi").value(hasItem(DEFAULT_HAS_FREE_WIFI.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        // Get the establishment
        restEstablishmentMockMvc
            .perform(get(ENTITY_API_URL_ID, establishment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(establishment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.globalRate").value(DEFAULT_GLOBAL_RATE.doubleValue()))
            .andExpect(jsonPath("$.establishmentType").value(DEFAULT_ESTABLISHMENT_TYPE.toString()))
            .andExpect(jsonPath("$.hasParking").value(DEFAULT_HAS_PARKING.booleanValue()))
            .andExpect(jsonPath("$.hasRestaurant").value(DEFAULT_HAS_RESTAURANT.booleanValue()))
            .andExpect(jsonPath("$.hasFreeWifi").value(DEFAULT_HAS_FREE_WIFI.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingEstablishment() throws Exception {
        // Get the establishment
        restEstablishmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Update the establishment
        Establishment updatedEstablishment = establishmentRepository.findById(establishment.getId()).get();
        // Disconnect from session so that the updates on updatedEstablishment are not directly saved in db
        em.detach(updatedEstablishment);
        updatedEstablishment
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .globalRate(UPDATED_GLOBAL_RATE)
            .establishmentType(UPDATED_ESTABLISHMENT_TYPE)
            .hasParking(UPDATED_HAS_PARKING)
            .hasRestaurant(UPDATED_HAS_RESTAURANT)
            .hasFreeWifi(UPDATED_HAS_FREE_WIFI)
            .description(UPDATED_DESCRIPTION);

        restEstablishmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEstablishment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEstablishment))
            )
            .andExpect(status().isOk());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEstablishment.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEstablishment.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEstablishment.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testEstablishment.getGlobalRate()).isEqualTo(UPDATED_GLOBAL_RATE);
        assertThat(testEstablishment.getEstablishmentType()).isEqualTo(UPDATED_ESTABLISHMENT_TYPE);
        assertThat(testEstablishment.getHasParking()).isEqualTo(UPDATED_HAS_PARKING);
        assertThat(testEstablishment.getHasRestaurant()).isEqualTo(UPDATED_HAS_RESTAURANT);
        assertThat(testEstablishment.getHasFreeWifi()).isEqualTo(UPDATED_HAS_FREE_WIFI);
        assertThat(testEstablishment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, establishment.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(establishment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(establishment)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstablishmentWithPatch() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Update the establishment using partial update
        Establishment partialUpdatedEstablishment = new Establishment();
        partialUpdatedEstablishment.setId(establishment.getId());

        partialUpdatedEstablishment
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .globalRate(UPDATED_GLOBAL_RATE)
            .hasParking(UPDATED_HAS_PARKING)
            .hasRestaurant(UPDATED_HAS_RESTAURANT)
            .description(UPDATED_DESCRIPTION);

        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablishment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstablishment))
            )
            .andExpect(status().isOk());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEstablishment.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEstablishment.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEstablishment.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testEstablishment.getGlobalRate()).isEqualTo(UPDATED_GLOBAL_RATE);
        assertThat(testEstablishment.getEstablishmentType()).isEqualTo(DEFAULT_ESTABLISHMENT_TYPE);
        assertThat(testEstablishment.getHasParking()).isEqualTo(UPDATED_HAS_PARKING);
        assertThat(testEstablishment.getHasRestaurant()).isEqualTo(UPDATED_HAS_RESTAURANT);
        assertThat(testEstablishment.getHasFreeWifi()).isEqualTo(DEFAULT_HAS_FREE_WIFI);
        assertThat(testEstablishment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateEstablishmentWithPatch() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();

        // Update the establishment using partial update
        Establishment partialUpdatedEstablishment = new Establishment();
        partialUpdatedEstablishment.setId(establishment.getId());

        partialUpdatedEstablishment
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .globalRate(UPDATED_GLOBAL_RATE)
            .establishmentType(UPDATED_ESTABLISHMENT_TYPE)
            .hasParking(UPDATED_HAS_PARKING)
            .hasRestaurant(UPDATED_HAS_RESTAURANT)
            .hasFreeWifi(UPDATED_HAS_FREE_WIFI)
            .description(UPDATED_DESCRIPTION);

        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstablishment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstablishment))
            )
            .andExpect(status().isOk());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
        Establishment testEstablishment = establishmentList.get(establishmentList.size() - 1);
        assertThat(testEstablishment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEstablishment.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testEstablishment.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testEstablishment.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testEstablishment.getGlobalRate()).isEqualTo(UPDATED_GLOBAL_RATE);
        assertThat(testEstablishment.getEstablishmentType()).isEqualTo(UPDATED_ESTABLISHMENT_TYPE);
        assertThat(testEstablishment.getHasParking()).isEqualTo(UPDATED_HAS_PARKING);
        assertThat(testEstablishment.getHasRestaurant()).isEqualTo(UPDATED_HAS_RESTAURANT);
        assertThat(testEstablishment.getHasFreeWifi()).isEqualTo(UPDATED_HAS_FREE_WIFI);
        assertThat(testEstablishment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, establishment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(establishment))
            )
            .andExpect(status().isBadRequest());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstablishment() throws Exception {
        int databaseSizeBeforeUpdate = establishmentRepository.findAll().size();
        establishment.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstablishmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(establishment))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Establishment in the database
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstablishment() throws Exception {
        // Initialize the database
        establishmentRepository.saveAndFlush(establishment);

        int databaseSizeBeforeDelete = establishmentRepository.findAll().size();

        // Delete the establishment
        restEstablishmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, establishment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Establishment> establishmentList = establishmentRepository.findAll();
        assertThat(establishmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
