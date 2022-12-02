package kata.academy.eurekaprofileservice.service.impl;

import java.util.Optional;
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
    public void addProfile(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    public void updateProfile(Profile profile) {
        profileRepository.save(profile);
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Profile> findById(Long profileId) {
        return profileRepository.findById(profileId);
    }
}
