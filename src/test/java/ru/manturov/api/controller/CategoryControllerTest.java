package ru.manturov.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import ru.manturov.api.converter.CategoryToResponseConverter;
import ru.manturov.api.converter.UserToResponseConverter;
import ru.manturov.api.json.AuthResponse;
import ru.manturov.api.json.CategoryRequest;
import ru.manturov.api.json.CategoryResponse;
import ru.manturov.api.json.ReportRequest;
import ru.manturov.config.SecurityConfiguration;
import ru.manturov.dao.ReportModel;
import ru.manturov.entity.Category;
import ru.manturov.entity.User;
import ru.manturov.service.CategoryService;
import ru.manturov.service.RegService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class, CurrentUser.class})
@RunWith(SpringRunner.class)
public class CategoryControllerTest {
    Category category;
    Category categoryUpd;
    LocalDate beginDate;
    LocalDate endDate;
    CategoryRequest categoryRequest;
    CategoryRequest categoryRequestUpd;
    ReportRequest reportRequest;

    @Autowired
    ObjectMapper om;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RegService regService;

    @MockBean
    CategoryService categoryService;

    @SpyBean
    UserToResponseConverter userToResponseConverter;

    @SpyBean
    CategoryToResponseConverter categoryToResponseConverter;

    @Before
    public void setUp() {
        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com");

        category = new Category()
                .setId(1L)
                .setCategory("Eat");

        categoryUpd = new Category()
                .setId(1L)
                .setCategory("Product");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);

        categoryRequest = new CategoryRequest()
                .setId(1L)
                .setName("Eat");

        categoryRequestUpd = new CategoryRequest()
                .setId(1L)
                .setName("Product");

        beginDate = LocalDate.of(2022, 6, 1);
        endDate = LocalDate.of(2022, 12, 1);

        List<ReportModel> reportModelIncomeList = new ArrayList<>();
        reportModelIncomeList.add(
                new ReportModel()
                        .setCategoryName("Eat")
                        .setValue(BigDecimal.valueOf(300)));

        List<ReportModel> reportModelExpenseList = new ArrayList<>();
        reportModelExpenseList.add(
                new ReportModel()
                        .setCategoryName("Product")
                        .setValue(BigDecimal.valueOf(2300)));

        reportRequest = new ReportRequest()
                .setBeginDate(beginDate)
                .setEndDate(endDate);

        when(userToResponseConverter
                .convert(user))
                .thenReturn(new AuthResponse(1L, "vazyan93@gmail.com"));

        when(categoryToResponseConverter
                .convert(category))
                .thenReturn(new CategoryResponse(1L, "Eat"));

        when(categoryToResponseConverter
                .convert(categoryUpd))
                .thenReturn(new CategoryResponse(1L, "Product"));

        when(regService.getUserById(1L)).thenReturn(user);

        when(categoryService.getAllByUserId(1L)).thenReturn(categoryList);
        when(categoryService.insert("Eat", 1L)).thenReturn(category);
        when(categoryService.update(1L, "Product", 1L)).thenReturn(categoryUpd);
        when(categoryService.delete(1L, 1L)).thenReturn(true);
        when(categoryService.getIncomeByPeriod(
                1L,
                Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .thenReturn(reportModelIncomeList);
        when(categoryService.getExpenseByPeriod(
                1L,
                Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .thenReturn(reportModelExpenseList);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void categories() throws Exception {
        mockMvc.perform(get("/api/category/all"))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "  " +
                        "{\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"Eat\"\n" +
                        "  }\n" +
                        "]"));

        verify(regService, times(1)).getUserById(1L);
        verify(categoryToResponseConverter, times(1)).convert(category);
        verify(categoryService, times(1)).getAllByUserId(1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void insert() throws Exception {
        mockMvc.perform(post("/api/category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"name\": \"Eat\"\n" +
                        "}"));

        verify(regService, times(1)).getUserById(1L);
        verify(categoryToResponseConverter, times(1)).convert(category);
        verify(categoryService, times(1)).insert("Eat", 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void delete() throws Exception {
        mockMvc.perform(post("/api/category/del")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(regService, times(1)).getUserById(1L);
        verify(categoryService, times(1)).delete(1L, 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void update() throws Exception {
        mockMvc.perform(post("/api/category/upd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(categoryRequestUpd)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"name\": \"Product\"\n" +
                        "}"));

        verify(regService, times(1)).getUserById(1L);
        verify(categoryToResponseConverter, times(1)).convert(categoryUpd);
        verify(categoryService, times(1)).update(1L, "Product", 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getIncomeByPeriod() throws Exception {
        om = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        mockMvc.perform(get("/api/report/income")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(reportRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"categoryName\": \"Eat\",\n" +
                        "    \"value\": 300\n" +
                        "  }\n" +
                        "]"));

        verify(regService, times(1)).getUserById(1L);
        verify(categoryService, times(1)).getIncomeByPeriod(
                1L,
                Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getExpenseByPeriod() throws Exception {
        om = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();

        mockMvc.perform(get("/api/report/expense")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(reportRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "  {\n" +
                        "    \"categoryName\": \"Product\",\n" +
                        "    \"value\": 2300\n" +
                        "  }\n" +
                        "]"));

        verify(regService, times(1)).getUserById(1L);
        verify(categoryService, times(1)).getExpenseByPeriod(
                1L,
                Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}