package com.bestteam.urlshorter;

import com.bestteam.urlshorter.auth.RegistrationRequest;
import com.bestteam.urlshorter.constants.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class AuthenticationControllerTest extends SpringBootApplicationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void registrationTest() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstname("Ivan");
        request.setLastname("Argon");
        request.setEmail("ivanargon@gmail.com");
        request.setPassword("223123123gsgd");
        request.setRole(Role.USER);

        mockMvc.perform(post("/api/v1/auth/registration")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty());
    }

    @Test
    void registrationTest_IfPasswordIsNullThenReturnBadRequest() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstname("Ivan");
        request.setLastname("Argon");
        request.setEmail("ivanargon@gmail.com");
        request.setPassword(null);
        request.setRole(Role.USER);

        mockMvc.perform(post("/api/v1/auth/registration")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
}