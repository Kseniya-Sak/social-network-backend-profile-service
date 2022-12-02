package kata.academy.eurekaprofileservice.service;

import kata.academy.eurekaprofileservice.model.entity.Profile;

import java.util.Optional;

public interface ProfileService {

    void addProfile(Profile profile);

    void updateProfile(Profile profile);

    void deleteById(Long profileId);

    boolean existsByIdAndUserId(Long profileId, Long userId);

    Optional<Profile> findById(Long profileId);
}
