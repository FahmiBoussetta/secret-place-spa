package com.yamibo.secretplacespa.repository;

import com.yamibo.secretplacespa.domain.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select booking from Booking booking where booking.jhiUserId.login = ?#{principal.username}")
    List<Booking> findByJhiUserIdIsCurrentUser();
}
