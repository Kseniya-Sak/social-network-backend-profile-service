package kata.academy.eurekaprofileservice.service.impl;

import kata.academy.eurekaprofileservice.model.entity.Profile;
import kata.academy.eurekaprofileservice.repository.ProfileRepository;
import kata.academy.eurekaprofileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile addProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public void deleteById(Long profileId) {
        profileRepository.deleteById(profileId);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByIdAndUserId(Long profileId, Long userId) {
        return profileRepository.existsByIdAndUserId(profileId, userId);
    }
}
