package kata.academy.eurekaprofileservice.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kata.academy.eurekaprofileservice.model.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ProfileRequestDto(
        String firstName,
        String lastName,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate birthdate,
        Gender gender) {
}
