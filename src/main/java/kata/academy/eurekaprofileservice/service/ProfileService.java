package kata.academy.eurekaprofileservice.service;

import kata.academy.eurekaprofileservice.model.entity.Profile;

import java.util.Optional;

public interface ProfileService {

    Profile addProfile(Profile profile);

    Profile updateProfile(Profile profile);

    void deleteById(Long profileId);

    boolean existsByIdAndUserId(Long profileId, Long userId);

    Optional<Profile> findById(Long profileId);
}
