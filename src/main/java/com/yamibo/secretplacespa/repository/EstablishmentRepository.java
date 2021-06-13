package com.yamibo.secretplacespa.repository;

import com.yamibo.secretplacespa.domain.Establishment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Establishment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstablishmentRepository extends JpaRepository<Establishment, Long> {}
