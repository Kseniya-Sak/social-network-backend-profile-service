package kata.academy.eurekaprofileservice.model.converter;

import kata.academy.eurekaprofileservice.model.dto.ProfileRequestDto;
import kata.academy.eurekaprofileservice.model.entity.Profile;

public final class ProfileMapper {

    private ProfileMapper() {
    }

    public static Profile toEntity(ProfileRequestDto dto) {
        return Profile
                .builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .birthdate(dto.birthdate())
                .gender(dto.gender())
                .build();
    }
}
