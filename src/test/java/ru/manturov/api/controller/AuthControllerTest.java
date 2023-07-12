package ru.manturov.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;
import ru.manturov.MockSecurityConfiguration;
import ru.manturov.api.converter.UserToResponseConverter;
import ru.manturov.api.json.AuthRequest;
import ru.manturov.api.json.AuthResponse;
import ru.manturov.config.SecurityConfiguration;
import ru.manturov.entity.User;
import ru.manturov.service.RegService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class, CurrentUser.class})
@RunWith(SpringRunner.class)
public class AuthControllerTest {
    AuthRequest authRequest;
    User user;

    @Autowired
    ObjectMapper om;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegService regService;

    @SpyBean
    UserToResponseConverter userToResponseConverter;

    @Before
    public void setUp() {
        user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com")
                .setPassword("Qwertasdf99");

        authRequest = new AuthRequest()
                .setEmail("vazyan93@gmail.com")
                .setPassword("Qwertasdf99");

        when(userToResponseConverter.convert(user))
                .thenReturn(new AuthResponse(1L, "vazyan93@gmail.com"));

        when(regService.getUserById(1L))
                .thenReturn(user);

        when(regService.registration("vazyan93@gmail.com", "Qwertasdf99"))
                .thenReturn(user);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getUserInfo() throws Exception {
        mockMvc.perform(get("/api/getuserinfo"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"email\": \"vazyan93@gmail.com\"\n" +
                        "}"));
    }

    @Test
    public void registration() throws Exception {
        mockMvc.perform(post("/api/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\":" +
                        " 1,\n" +
                        "  \"email\": \"vazyan93@gmail.com\"\n" +
                        "}"));

        verify(regService, times(1)).registration("vazyan93@gmail.com", "Qwertasdf99");
        verify(userToResponseConverter, times(1)).convert(user);
    }

    @Test(expected = NestedServletException.class)
    public void registration_NullPass() throws Exception {
        mockMvc.perform(post("/api/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(
                                new AuthRequest()
                                        .setEmail("vazyan93@gmail.com")
                                        .setPassword(null))))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(regService);
        verifyNoInteractions(userToResponseConverter);
    }
}