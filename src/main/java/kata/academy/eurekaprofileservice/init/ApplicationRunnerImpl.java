package kata.academy.eurekaprofileservice.init;

import kata.academy.eurekaprofileservice.model.enums.Gender;
import kata.academy.eurekaprofileservice.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
@Profile("dev")
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final ProfileService profileService;

    @Override
    public void run(ApplicationArguments args) {
        for (int i = 1; i <= 30; i++) {
            String firstName = "fn" + i;
            String lastName = "ln" + i;
            LocalDate birthDate = LocalDate.of((1960 + i), (int) (Math.random() * 11) + 1, (i < 29) ? i : 28);
            Gender gender = (i % 2 == 0) ? Gender.MALE : Gender.FEMALE;
            kata.academy.eurekaprofileservice.model.entity.Profile profile = new kata.academy.eurekaprofileservice.model.entity.Profile();
            profile.setUserId((long) i);
            profile.setFirstName(firstName);
            profile.setLastName(lastName);
            profile.setBirthdate(birthDate);
            profile.setGender(gender);
            profileService.addProfile(profile);
        }
    }
}
