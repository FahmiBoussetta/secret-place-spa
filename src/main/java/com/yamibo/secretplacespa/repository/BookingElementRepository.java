package com.yamibo.secretplacespa.repository;

import com.yamibo.secretplacespa.domain.BookingElement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BookingElement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingElementRepository extends JpaRepository<BookingElement, Long> {}
