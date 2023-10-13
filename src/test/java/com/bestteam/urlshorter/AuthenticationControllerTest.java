package com.bestteam.urlshorter;

import com.bestteam.urlshorter.auth.AuthenticationRequest;
import com.bestteam.urlshorter.auth.AuthenticationResponse;
import com.bestteam.urlshorter.auth.RegistrationRequest;
import com.bestteam.urlshorter.constants.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
        request.setUsername("Ivan1");
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
        request.setUsername("Ivanargon");
        request.setEmail("ivanargon@gmail.com");
        request.setPassword(null);
        request.setRole(Role.USER);

        mockMvc.perform(post("/api/v1/auth/registration")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void authenticateTest() throws Exception {
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("Ivanoletes");
        request.setEmail("oletes@gmail.com");
        request.setPassword("asfggdhgh2");
        request.setRole(Role.USER);

        mockMvc.perform(post("/api/v1/auth/registration")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty());

        AuthenticationRequest authenticationRequest = new  AuthenticationRequest();

        authenticationRequest.setEmail("oletes@gmail.com");
        authenticationRequest.setPassword("asfggdhgh2");

        mockMvc.perform(post("/api/v1/auth/authenticate")
                        .content(objectMapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty());
    }

    @Test
    void refreshTokenTest() throws Exception {
        // Registration
        RegistrationRequest request = new RegistrationRequest();
        request.setUsername("Ivankof");
        request.setEmail("kofnanf@gmail.com");
        request.setPassword("asfggdhgh2");
        request.setRole(Role.USER);

        mockMvc.perform(post("/api/v1/auth/registration")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty());

        // Authentication
        AuthenticationRequest authenticationRequest = new  AuthenticationRequest();

        authenticationRequest.setEmail("kofnanf@gmail.com");
        authenticationRequest.setPassword("asfggdhgh2");

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/auth/authenticate")
                        .content(objectMapper.writeValueAsString(authenticationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty())
                .andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        String refreshToken;
        AuthenticationResponse authenticationResponse = objectMapper.readValue(contentAsString, AuthenticationResponse.class);
        refreshToken = authenticationResponse.getRefreshToken();
        // RefreshToken


        mockMvc.perform(post("/api/v1/auth/refresh-token")
                        .header("Authorization", "Bearer " + refreshToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.access_token").isNotEmpty())
                .andExpect(jsonPath("$.refresh_token").isNotEmpty());
    }
}