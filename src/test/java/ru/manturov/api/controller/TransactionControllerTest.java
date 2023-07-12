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
import ru.manturov.api.converter.TransactionToResponseConverter;
import ru.manturov.api.converter.UserToResponseConverter;
import ru.manturov.api.json.AuthResponse;
import ru.manturov.api.json.TransactionRequest;
import ru.manturov.api.json.TransactionResponse;
import ru.manturov.config.SecurityConfiguration;
import ru.manturov.entity.Account;
import ru.manturov.entity.Transaction;
import ru.manturov.entity.User;
import ru.manturov.service.RegService;
import ru.manturov.service.TransactionService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class, CurrentUser.class})
@RunWith(SpringRunner.class)
public class TransactionControllerTest {
    Transaction transaction;
    TransactionRequest transactionRequest;

    @Autowired
    ObjectMapper om;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegService regService;

    @MockBean
    TransactionService transactionService;

    @SpyBean
    UserToResponseConverter userToResponseConverter;

    @SpyBean
    TransactionToResponseConverter transactionToResponseConverter;

    @Before
    public void setUp() {
        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com");

        Account account = new Account()
                .setId(2L)
                .setName("AccountName2")
                .setBalance(BigDecimal.valueOf(123.0));

        transaction = new Transaction()
                .setId(1L)
                .setValue(BigDecimal.valueOf(32455))
                .setFromAccount(account)
                .setToAccount(null)
                .setCreatedDate(new GregorianCalendar(2022, 8, 3, 0, 0).getTime());

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        transactionRequest = new TransactionRequest()
                .setId(1L)
                .setValue(BigDecimal.valueOf(32455))
                .setFromAccountId(2L)
                .setToAccountId(null)
                .setCategories(Collections.singletonList(1L));

        when(userToResponseConverter.convert(user))
                .thenReturn(new AuthResponse(1L, "vazyan93@gmail.com"));

        when(transactionToResponseConverter.convert(transaction))
                .thenReturn(new TransactionResponse(
                        1L,
                        BigDecimal.valueOf(32455),
                        new GregorianCalendar(2022, 8, 3, 0, 0).getTime(),
                        2L,
                        null));

        when(regService.getUserById(1L))
                .thenReturn(user);

        when(transactionService.findAllByUserId(1L, 1L))
                .thenReturn(transactionList);

        when(transactionService.insert(
                BigDecimal.valueOf(32455),
                2L,
                null,
                Collections.singletonList(1L)))
                .thenReturn(transaction);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void transactions() throws Exception {
        mockMvc.perform(get("/api/transaction/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\n" +
                        "  \"id\": " +
                        "1,\n" +
                        "  \"value\": 32455,\n" +
                        "  \"date\":" +
                        " \"2022-09-02T19:00:00.000+00:00\",\n" +
                        "  \"fromAccId\": 2,\n" +
                        "  \"toAccId\": null\n" +
                        "}]"));

        verify(regService, times(1)).getUserById(1L);
        verify(transactionToResponseConverter, times(1)).convert(transaction);
        verify(transactionService, times(1)).findAllByUserId(1L, 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insert() throws Exception {
        mockMvc.perform(post("/api/transaction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": " +
                        "1,\n" +
                        "  \"value\": 32455,\n" +
                        "  \"date\":" +
                        " \"2022-09-02T19:00:00.000+00:00\",\n" +
                        "  \"fromAccId\": 2,\n" +
                        "  \"toAccId\": null\n" +
                        "}"));

        verify(transactionToResponseConverter, times(1)).convert(transaction);
        verify(transactionService, times(1)).insert(BigDecimal.valueOf(32455), 2L, null, Collections.singletonList(1L));
    }
}