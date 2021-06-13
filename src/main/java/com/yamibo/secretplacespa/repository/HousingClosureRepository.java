package com.yamibo.secretplacespa.repository;

import com.yamibo.secretplacespa.domain.HousingClosure;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HousingClosure entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HousingClosureRepository extends JpaRepository<HousingClosure, Long> {}
