package com.yamibo.secretplacespa.repository;

import com.yamibo.secretplacespa.domain.EstablishmentClosure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EstablishmentClosure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstablishmentClosureRepository extends JpaRepository<EstablishmentClosure, Long> {}
