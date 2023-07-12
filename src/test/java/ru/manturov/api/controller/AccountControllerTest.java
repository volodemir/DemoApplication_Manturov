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
import ru.manturov.MockSecurityConfiguration;
import ru.manturov.api.converter.AccountToResponseConverter;
import ru.manturov.api.converter.UserToResponseConverter;
import ru.manturov.api.json.AccountRequest;
import ru.manturov.api.json.AccountResponse;
import ru.manturov.api.json.AuthResponse;
import ru.manturov.config.SecurityConfiguration;
import ru.manturov.entity.Account;
import ru.manturov.entity.User;
import ru.manturov.service.AccountService;
import ru.manturov.service.RegService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class, CurrentUser.class})
@RunWith(SpringRunner.class)
public class AccountControllerTest {
    Account account;
    Account accountUpd;
    AccountRequest accountRequest;
    AccountRequest accountRequestUpd;

    @Autowired
    ObjectMapper om;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegService regService;

    @MockBean
    AccountService accountService;

    @SpyBean
    AccountToResponseConverter accountToResponseConverter;

    @SpyBean
    UserToResponseConverter userToResponseConverter;

    @Before
    public void setUp() {
        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com");

        account = new Account()
                .setId(1L)
                .setName("AccountName")
                .setBalance(BigDecimal.valueOf(12334533));

        accountUpd = new Account()
                .setId(1L)
                .setName("AccountNewName")
                .setBalance(BigDecimal.valueOf(1233433));

        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        accountRequest = new AccountRequest()
                .setId(1L)
                .setBalance(BigDecimal.valueOf(12334533))
                .setName("AccountName");

        accountRequestUpd = new AccountRequest()
                .setId(1L)
                .setBalance(BigDecimal.valueOf(1233433))
                .setName("AccountNewName");

        when(userToResponseConverter
                .convert(user))
                .thenReturn(new AuthResponse(1L, "vazyan93@gmail.com"));

        when(accountToResponseConverter.convert(account))
                .thenReturn(new AccountResponse(
                        1L,
                        "AccountName",
                        BigDecimal.valueOf(12334533)));

        when(accountToResponseConverter.convert(accountUpd))
                .thenReturn(new AccountResponse(
                        1L,
                        "AccountNewName",
                        BigDecimal.valueOf(1233433)));

        when(regService
                .getUserById(1L))
                .thenReturn(user);

        when(accountService
                .getAllByUserId(1L))
                .thenReturn(accountList);

        when(accountService.insert(
                BigDecimal.valueOf(12334533),
                "AccountName",
                1L))
                .thenReturn(account);

        when(accountService.delete(
                1L,
                1L))
                .thenReturn(true);

        when(accountService.update(
                1L,
                "AccountNewName",
                BigDecimal.valueOf(1233433),
                1L))
                .thenReturn(accountUpd);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getAccounts() throws Exception {
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "  " +
                        "{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"AccountName\",\n" +
                        "    \"balance\": 12334533\n" +
                        "  }\n" +
                        "]"));

        verify(regService, times(1)).getUserById(1L);
        verify(accountService, times(1)).getAllByUserId(1L);
        verify(accountToResponseConverter, times(1)).convert(account);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insert() throws Exception {
        mockMvc.perform(post("/api/account/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\":" +
                        "1,\n" +
                        "  \"name\": \"AccountName\",\n" +
                        "  \"balance\":" +
                        " 12334533\n" +
                        "}"));

        verify(regService, times(1)).getUserById(1L);
        verify(accountService, times(1)).insert(BigDecimal.valueOf(12334533), "AccountName", 1L);
        verify(accountToResponseConverter, times(1)).convert(account);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void delete() throws Exception {
        mockMvc.perform(post("/api/account/del")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(regService, times(1)).getUserById(1L);
        verify(accountService, times(1)).delete(1L, 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void update() throws Exception {
        mockMvc.perform(post("/api/account/upd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(accountRequestUpd)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\":" +
                        "1,\n" +
                        "  \"name\": \"AccountNewName\",\n" +
                        "  \"balance\":" +
                        " 1233433\n" +
                        "}"));

        verify(regService, times(1)).getUserById(1L);
        verify(accountService, times(1)).update(1L, "AccountNewName", BigDecimal.valueOf(1233433), 1L);
        verify(accountToResponseConverter, times(1)).convert(accountUpd);
    }
}