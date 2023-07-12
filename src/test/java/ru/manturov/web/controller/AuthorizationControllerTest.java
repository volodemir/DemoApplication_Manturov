package ru.manturov.web.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;
import ru.manturov.MockSecurityConfiguration;
import ru.manturov.config.SecurityConfiguration;
import ru.manturov.entity.User;
import ru.manturov.service.RegService;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.manturov.security.UserRole.USER;

@WebMvcTest(AuthorizationController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class, CurrUser.class})
@RunWith(SpringRunner.class)
public class AuthorizationControllerTest {
    User user;
    User newUser;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegService regService;

    @Before
    public void setUp() {
        user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com")
                .setPassword("Qwertasdf99")
                .setRoles(Collections.singleton(USER));

        newUser = new User()
                .setId(1L)
                .setEmail("va93@gmail.com")
                .setPassword("Qwertasdf99")
                .setRoles(Collections.singleton(USER));

        when(regService.getUserById(1L)).thenReturn(user);
        when(regService.registration(newUser.getEmail(), newUser.getPassword())).thenReturn(newUser);

        BindingResult result = mock(BindingResult.class);
        when(!result.hasErrors()).thenReturn(true);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/personal-area"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 1L))
                .andExpect(model().attribute("name", "vazyan93@gmail.com"))
                .andExpect(view().name("personal-area"));

        verify(regService, times(1)).getUserById(1L);
    }

    @Test
    public void getLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/login-form"))
                .andExpect(status().isOk())
                .andExpect(view().name("login-form"));
    }

    @Test
    public void getRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/registration"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("form", new User()))
                .andExpect(view().name("registration"));
    }

    @Test
    public void postRegistration() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/web/registration")
                .accept(MediaType.TEXT_HTML)
                .param("email", "va93@gmail.com")
                .param("password", "Qwertasdf99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login-form"));

        verify(regService, times(1)).registration(newUser.getEmail(), newUser.getPassword());
    }
}