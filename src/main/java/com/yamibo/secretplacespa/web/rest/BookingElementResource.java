package com.yamibo.secretplacespa.web.rest;

import com.yamibo.secretplacespa.domain.BookingElement;
import com.yamibo.secretplacespa.repository.BookingElementRepository;
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
 * REST controller for managing {@link com.yamibo.secretplacespa.domain.BookingElement}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BookingElementResource {

    private final Logger log = LoggerFactory.getLogger(BookingElementResource.class);

    private static final String ENTITY_NAME = "bookingElement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookingElementRepository bookingElementRepository;

    public BookingElementResource(BookingElementRepository bookingElementRepository) {
        this.bookingElementRepository = bookingElementRepository;
    }

    /**
     * {@code POST  /booking-elements} : Create a new bookingElement.
     *
     * @param bookingElement the bookingElement to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookingElement, or with status {@code 400 (Bad Request)} if the bookingElement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/booking-elements")
    public ResponseEntity<BookingElement> createBookingElement(@Valid @RequestBody BookingElement bookingElement)
        throws URISyntaxException {
        log.debug("REST request to save BookingElement : {}", bookingElement);
        if (bookingElement.getId() != null) {
            throw new BadRequestAlertException("A new bookingElement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BookingElement result = bookingElementRepository.save(bookingElement);
        return ResponseEntity
            .created(new URI("/api/booking-elements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /booking-elements/:id} : Updates an existing bookingElement.
     *
     * @param id the id of the bookingElement to save.
     * @param bookingElement the bookingElement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookingElement,
     * or with status {@code 400 (Bad Request)} if the bookingElement is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookingElement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/booking-elements/{id}")
    public ResponseEntity<BookingElement> updateBookingElement(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BookingElement bookingElement
    ) throws URISyntaxException {
        log.debug("REST request to update BookingElement : {}, {}", id, bookingElement);
        if (bookingElement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookingElement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookingElementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BookingElement result = bookingElementRepository.save(bookingElement);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookingElement.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /booking-elements/:id} : Partial updates given fields of an existing bookingElement, field will ignore if it is null
     *
     * @param id the id of the bookingElement to save.
     * @param bookingElement the bookingElement to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookingElement,
     * or with status {@code 400 (Bad Request)} if the bookingElement is not valid,
     * or with status {@code 404 (Not Found)} if the bookingElement is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookingElement couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/booking-elements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<BookingElement> partialUpdateBookingElement(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BookingElement bookingElement
    ) throws URISyntaxException {
        log.debug("REST request to partial update BookingElement partially : {}, {}", id, bookingElement);
        if (bookingElement.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookingElement.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookingElementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookingElement> result = bookingElementRepository
            .findById(bookingElement.getId())
            .map(
                existingBookingElement -> {
                    if (bookingElement.getStartDate() != null) {
                        existingBookingElement.setStartDate(bookingElement.getStartDate());
                    }
                    if (bookingElement.getEndDate() != null) {
                        existingBookingElement.setEndDate(bookingElement.getEndDate());
                    }
                    if (bookingElement.getElementPrice() != null) {
                        existingBookingElement.setElementPrice(bookingElement.getElementPrice());
                    }

                    return existingBookingElement;
                }
            )
            .map(bookingElementRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bookingElement.getId().toString())
        );
    }

    /**
     * {@code GET  /booking-elements} : get all the bookingElements.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookingElements in body.
     */
    @GetMapping("/booking-elements")
    public List<BookingElement> getAllBookingElements() {
        log.debug("REST request to get all BookingElements");
        return bookingElementRepository.findAll();
    }

    /**
     * {@code GET  /booking-elements/:id} : get the "id" bookingElement.
     *
     * @param id the id of the bookingElement to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookingElement, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/booking-elements/{id}")
    public ResponseEntity<BookingElement> getBookingElement(@PathVariable Long id) {
        log.debug("REST request to get BookingElement : {}", id);
        Optional<BookingElement> bookingElement = bookingElementRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(bookingElement);
    }

    /**
     * {@code DELETE  /booking-elements/:id} : delete the "id" bookingElement.
     *
     * @param id the id of the bookingElement to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/booking-elements/{id}")
    public ResponseEntity<Void> deleteBookingElement(@PathVariable Long id) {
        log.debug("REST request to delete BookingElement : {}", id);
        bookingElementRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
