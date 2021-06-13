package com.yamibo.secretplacespa.repository;

import com.yamibo.secretplacespa.domain.Rating;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("select rating from Rating rating where rating.userId.login = ?#{principal.username}")
    List<Rating> findByUserIdIsCurrentUser();
}
