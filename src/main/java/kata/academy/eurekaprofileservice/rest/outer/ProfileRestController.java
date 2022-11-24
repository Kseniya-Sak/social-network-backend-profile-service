package kata.academy.eurekaprofileservice.rest.outer;

import kata.academy.eurekaprofileservice.api.Data;
import kata.academy.eurekaprofileservice.model.converter.ProfileMapper;
import kata.academy.eurekaprofileservice.model.dto.ProfileRequestDto;
import kata.academy.eurekaprofileservice.model.entity.Profile;
import kata.academy.eurekaprofileservice.service.ProfileService;
import kata.academy.eurekaprofileservice.util.ApiValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.Optional;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileRestController {

    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<Data<Long>> addProfile(@RequestBody ProfileRequestDto dto,
                                                 @RequestHeader @Positive Long userId) {
        Profile profile = ProfileMapper.toEntity(dto);
        profile.setUserId(userId);
        profileService.addProfile(profile);
        return ResponseEntity.ok(Data.of(profile.getId()));
    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileRequestDto dto,
                                              @PathVariable @Positive Long profileId,
                                              @RequestHeader @Positive Long userId) {
        ApiValidationUtil.requireTrue(profileService.existsByIdAndUserId(profileId, userId),
                String.format("Юзер с userId %d не имеет профиля с profileId %d в базе данных", userId, profileId));
        Profile profile = ProfileMapper.toEntity(dto);
        profile.setId(profileId);
        profile.setUserId(userId);
        profileService.updateProfile(profile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deleteProfile(@PathVariable @Positive Long profileId,
                                              @RequestHeader @Positive Long userId) {
        ApiValidationUtil.requireTrue(profileService.existsByIdAndUserId(profileId, userId),
                String.format("Юзер с userId %d не имеет профиля с profileId %d в базе данных", userId, profileId));
        profileService.deleteById(profileId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<Profile> getProfile(@PathVariable @Positive Long profileId) {
        Optional<Profile> profile = profileService.findById(profileId);

        ApiValidationUtil.requireTrue(profile.isPresent(),
                    String.format("Профиль с таким profileId %d не существует в базе данных", profileId));

        return ResponseEntity.ok(profile.get());
    }
}
