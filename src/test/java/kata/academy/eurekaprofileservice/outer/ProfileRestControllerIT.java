package kata.academy.eurekaprofileservice.outer;

import kata.academy.eurekaprofileservice.SpringSimpleContextTest;
import kata.academy.eurekaprofileservice.model.dto.ProfileRequestDto;
import kata.academy.eurekaprofileservice.model.enums.Gender;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProfileRestControllerIT extends SpringSimpleContextTest {

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/ProfileRestController/getProfile_SuccessfulProfileGetTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/ProfileRestController/getProfile_SuccessfulProfileGetTest/After.sql")
    public void getProfile_SuccessfulProfileGetTest() throws Exception {
        Long profileId = 1L;
        mockMvc.perform(get("/api/v1/profiles/{profileId}", profileId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(profileId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("firstName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value("2000-12-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gender").value("MALE"));
    }

    @Test
    public void getProfile_unSuccessfulProfileGetTest() throws Exception {
        Long profileId = 1L;
        mockMvc.perform(get("/api/v1/profiles/{profileId}", profileId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Is.is(
                        String.format("Профиль с таким profileId %d не существует в базе данных", profileId)
                )));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/ProfileRestController/deleteProfile_SuccessfulProfileDeleteTest/Before.sql")
    public void deleteProfile_SuccessfulProfileDeleteTest() throws Exception {
        Long profileId = 1L;
        Long userId = 1L;
        mockMvc.perform(delete("/api/v1/profiles/{profileId}", profileId)
                        .header("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(p.id) < 1
                                FROM Profile p
                                WHERE p.id = :id
                                AND p.userId = :userId
                                """, Boolean.class)
                .setParameter("id", profileId)
                .setParameter("userId", userId)
                .getSingleResult());
    }

    @Test
    public void deleteProfile_unSuccessfulProfileDeleteTest() throws Exception {
        Long profileId = 1L;
        Long userId = 1L;
        mockMvc.perform(delete("/api/v1/profiles/{profileId}", profileId)
                        .header("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Is.is(
                        String.format("Юзер с userId %d не имеет профиля с profileId %d в базе данных", userId, profileId)
                )));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, value = "/scripts/outer/ProfileRestController/putProfile_SuccessfulProfilePutTest/Before.sql")
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/ProfileRestController/putProfile_SuccessfulProfilePutTest/After.sql")
    public void putProfile_SuccessfulProfilePutTest() throws Exception {
        Long profileId = 1L;
        Long userId = 1L;
        mockMvc.perform(put("/api/v1/profiles/{profileId}", profileId)
                        .content(objectMapper.writeValueAsString(new ProfileRequestDto("firstName",
                                "lastName", LocalDate.of(2000, 12, 12), Gender.MALE)))
                        .header("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(p.id) = 1
                                FROM Profile p
                                WHERE p.id = :id
                                AND p.userId = :userId
                                AND p.firstName = :firstName
                                AND p.lastName = :lastName
                                AND p.birthdate = :birthdate
                                AND p.gender = :gender
                                """, Boolean.class)
                .setParameter("id", profileId)
                .setParameter("userId", userId)
                .setParameter("firstName", "firstName")
                .setParameter("lastName", "lastName")
                .setParameter("birthdate", LocalDate.of(2000, 12, 12))
                .setParameter("gender", Gender.MALE)
                .getSingleResult());
    }

    @Test
    public void putProfile_unSuccessfulProfilePutTest() throws Exception {
        Long profileId = 1L;
        Long userId = 1L;
        mockMvc.perform(put("/api/v1/profiles/{profileId}", profileId)
                        .content(objectMapper.writeValueAsString(new ProfileRequestDto("firstName",
                                "lastName", LocalDate.of(2000, 12, 12), Gender.MALE)))
                        .header("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.text", Is.is(
                        String.format("Юзер с userId %d не имеет профиля с profileId %d в базе данных", userId, profileId)
                )));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, value = "/scripts/outer/ProfileRestController/postProfile_SuccessfulProfilePostTest/After.sql")
    public void postProfile_SuccessfulProfilePostTest() throws Exception {
        Long userId = 1L;
        mockMvc.perform(post("/api/v1/profiles")
                        .content(objectMapper.writeValueAsString(new ProfileRequestDto( "firstName",
                                "lastName", LocalDate.of(2000, 12, 12), Gender.MALE)))
                        .header("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(1));

        assertTrue(entityManager.createQuery(
                        """
                                SELECT COUNT(p.id) = 1
                                FROM Profile p
                                WHERE p.id = :id
                                AND p.userId = :userId
                                AND p.firstName = :firstName
                                AND p.lastName = :lastName
                                AND p.birthdate = :birthdate
                                AND p.gender = :gender
                                """, Boolean.class)
                .setParameter("id", 1L)
                .setParameter("userId", userId)
                .setParameter("firstName", "firstName")
                .setParameter("lastName", "lastName")
                .setParameter("birthdate", LocalDate.of(2000, 12, 12))
                .setParameter("gender", Gender.MALE)
                .getSingleResult());
    }
}
