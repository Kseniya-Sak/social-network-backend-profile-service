package kata.academy.eurekaprofileservice.repository;

import kata.academy.eurekaprofileservice.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    boolean existsByIdAndUserId(Long profileId, Long userId);
}
