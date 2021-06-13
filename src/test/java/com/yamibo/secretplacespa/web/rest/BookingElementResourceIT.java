package com.yamibo.secretplacespa.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yamibo.secretplacespa.IntegrationTest;
import com.yamibo.secretplacespa.domain.BookingElement;
import com.yamibo.secretplacespa.repository.BookingElementRepository;
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
 * Integration tests for the {@link BookingElementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookingElementResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_ELEMENT_PRICE = 1;
    private static final Integer UPDATED_ELEMENT_PRICE = 2;

    private static final String ENTITY_API_URL = "/api/booking-elements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookingElementRepository bookingElementRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingElementMockMvc;

    private BookingElement bookingElement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookingElement createEntity(EntityManager em) {
        BookingElement bookingElement = new BookingElement()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .elementPrice(DEFAULT_ELEMENT_PRICE);
        return bookingElement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookingElement createUpdatedEntity(EntityManager em) {
        BookingElement bookingElement = new BookingElement()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .elementPrice(UPDATED_ELEMENT_PRICE);
        return bookingElement;
    }

    @BeforeEach
    public void initTest() {
        bookingElement = createEntity(em);
    }

    @Test
    @Transactional
    void createBookingElement() throws Exception {
        int databaseSizeBeforeCreate = bookingElementRepository.findAll().size();
        // Create the BookingElement
        restBookingElementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isCreated());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeCreate + 1);
        BookingElement testBookingElement = bookingElementList.get(bookingElementList.size() - 1);
        assertThat(testBookingElement.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBookingElement.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBookingElement.getElementPrice()).isEqualTo(DEFAULT_ELEMENT_PRICE);
    }

    @Test
    @Transactional
    void createBookingElementWithExistingId() throws Exception {
        // Create the BookingElement with an existing ID
        bookingElement.setId(1L);

        int databaseSizeBeforeCreate = bookingElementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingElementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingElementRepository.findAll().size();
        // set the field null
        bookingElement.setStartDate(null);

        // Create the BookingElement, which fails.

        restBookingElementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isBadRequest());

        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingElementRepository.findAll().size();
        // set the field null
        bookingElement.setEndDate(null);

        // Create the BookingElement, which fails.

        restBookingElementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isBadRequest());

        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkElementPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookingElementRepository.findAll().size();
        // set the field null
        bookingElement.setElementPrice(null);

        // Create the BookingElement, which fails.

        restBookingElementMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isBadRequest());

        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBookingElements() throws Exception {
        // Initialize the database
        bookingElementRepository.saveAndFlush(bookingElement);

        // Get all the bookingElementList
        restBookingElementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingElement.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].elementPrice").value(hasItem(DEFAULT_ELEMENT_PRICE)));
    }

    @Test
    @Transactional
    void getBookingElement() throws Exception {
        // Initialize the database
        bookingElementRepository.saveAndFlush(bookingElement);

        // Get the bookingElement
        restBookingElementMockMvc
            .perform(get(ENTITY_API_URL_ID, bookingElement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookingElement.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.elementPrice").value(DEFAULT_ELEMENT_PRICE));
    }

    @Test
    @Transactional
    void getNonExistingBookingElement() throws Exception {
        // Get the bookingElement
        restBookingElementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBookingElement() throws Exception {
        // Initialize the database
        bookingElementRepository.saveAndFlush(bookingElement);

        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();

        // Update the bookingElement
        BookingElement updatedBookingElement = bookingElementRepository.findById(bookingElement.getId()).get();
        // Disconnect from session so that the updates on updatedBookingElement are not directly saved in db
        em.detach(updatedBookingElement);
        updatedBookingElement.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).elementPrice(UPDATED_ELEMENT_PRICE);

        restBookingElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBookingElement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBookingElement))
            )
            .andExpect(status().isOk());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
        BookingElement testBookingElement = bookingElementList.get(bookingElementList.size() - 1);
        assertThat(testBookingElement.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBookingElement.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBookingElement.getElementPrice()).isEqualTo(UPDATED_ELEMENT_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingBookingElement() throws Exception {
        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();
        bookingElement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookingElement.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookingElement() throws Exception {
        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();
        bookingElement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingElementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookingElement() throws Exception {
        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();
        bookingElement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingElementMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingElement)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBookingElementWithPatch() throws Exception {
        // Initialize the database
        bookingElementRepository.saveAndFlush(bookingElement);

        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();

        // Update the bookingElement using partial update
        BookingElement partialUpdatedBookingElement = new BookingElement();
        partialUpdatedBookingElement.setId(bookingElement.getId());

        restBookingElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookingElement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookingElement))
            )
            .andExpect(status().isOk());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
        BookingElement testBookingElement = bookingElementList.get(bookingElementList.size() - 1);
        assertThat(testBookingElement.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testBookingElement.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testBookingElement.getElementPrice()).isEqualTo(DEFAULT_ELEMENT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateBookingElementWithPatch() throws Exception {
        // Initialize the database
        bookingElementRepository.saveAndFlush(bookingElement);

        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();

        // Update the bookingElement using partial update
        BookingElement partialUpdatedBookingElement = new BookingElement();
        partialUpdatedBookingElement.setId(bookingElement.getId());

        partialUpdatedBookingElement.startDate(UPDATED_START_DATE).endDate(UPDATED_END_DATE).elementPrice(UPDATED_ELEMENT_PRICE);

        restBookingElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookingElement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookingElement))
            )
            .andExpect(status().isOk());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
        BookingElement testBookingElement = bookingElementList.get(bookingElementList.size() - 1);
        assertThat(testBookingElement.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testBookingElement.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testBookingElement.getElementPrice()).isEqualTo(UPDATED_ELEMENT_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingBookingElement() throws Exception {
        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();
        bookingElement.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookingElement.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookingElement() throws Exception {
        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();
        bookingElement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingElementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookingElement() throws Exception {
        int databaseSizeBeforeUpdate = bookingElementRepository.findAll().size();
        bookingElement.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingElementMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookingElement))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookingElement in the database
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBookingElement() throws Exception {
        // Initialize the database
        bookingElementRepository.saveAndFlush(bookingElement);

        int databaseSizeBeforeDelete = bookingElementRepository.findAll().size();

        // Delete the bookingElement
        restBookingElementMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookingElement.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookingElement> bookingElementList = bookingElementRepository.findAll();
        assertThat(bookingElementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
