package com.yamibo.secretplacespa.repository;

import com.yamibo.secretplacespa.domain.HousingTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HousingTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HousingTemplateRepository extends JpaRepository<HousingTemplate, Long> {}
