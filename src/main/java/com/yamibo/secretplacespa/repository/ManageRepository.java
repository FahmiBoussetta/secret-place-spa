package com.yamibo.secretplacespa.repository;

import com.yamibo.secretplacespa.domain.Manage;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Manage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManageRepository extends JpaRepository<Manage, Long> {
    @Query("select manage from Manage manage where manage.userId.login = ?#{principal.username}")
    List<Manage> findByUserIdIsCurrentUser();
}
