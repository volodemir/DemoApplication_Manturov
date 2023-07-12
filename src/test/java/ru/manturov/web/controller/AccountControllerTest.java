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
import ru.manturov.entity.Account;
import ru.manturov.entity.User;
import ru.manturov.web.form.AccountForm;
import ru.manturov.service.AccountService;
import ru.manturov.service.RegService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.manturov.security.UserRole.USER;

@WebMvcTest(AccountsController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class, CurrUser.class})
@RunWith(SpringRunner.class)
public class AccountControllerTest {
    List<Account> accounts;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountsService;

    @MockBean
    RegService regService;

    @Before
    public void setUp() {
        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com")
                .setPassword("Qwertasdf99")
                .setRoles(Collections.singleton(USER));

        Account account = new Account()
                .setId(1L)
                .setName("Alfa")
                .setBalance(BigDecimal.valueOf(50000))
                .setUser(user);

        accounts = new ArrayList<>();
        accounts.add(account);

        when(regService.getUserById(1L)).thenReturn(user);

        when(accountsService.getAllByUserId(1L)).thenReturn(accounts);
        when(accountsService.insert(BigDecimal.valueOf(50000), "Alfa", 1L)).thenReturn(account);
        when(accountsService.delete(1L, 1L)).thenReturn(true);

        BindingResult result = mock(BindingResult.class);
        when(!result.hasErrors()).thenReturn(true);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getAccounts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/accounts"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("accounts", accounts))
                .andExpect(view().name("accounts"));
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getAddAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/accountadd"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("form", new AccountForm()))
                .andExpect(view().name("accountadd"));
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postAddAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/web/accountadd").accept(MediaType.TEXT_HTML)
                        .param("name", "Alfa")
                        .param("balance", "50000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/web/accounts"));
        verify(accountsService, times(1)).insert(BigDecimal.valueOf(50000), "Alfa", 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getDelAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/accountdelete"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("form", new AccountForm()))
                .andExpect(view().name("accountdelete"));
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postDelAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/web/accountdelete").accept(MediaType.TEXT_HTML)
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/web/accounts"));
        verify(accountsService, times(1)).delete(1L, 1L);
    }
}