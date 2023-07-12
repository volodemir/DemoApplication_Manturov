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
import ru.manturov.dao.ReportModel;
import ru.manturov.entity.Category;
import ru.manturov.entity.User;
import ru.manturov.web.form.CategoryForm;
import ru.manturov.service.CategoryService;
import ru.manturov.service.RegService;
import ru.manturov.web.form.ReportForm;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.manturov.security.UserRole.USER;

@WebMvcTest(CategoriesController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class, CurrUser.class})
@RunWith(SpringRunner.class)
public class CategoriesControllerTest {
    List<Category> categories;
    List<ReportModel> reportIncomeModelList;
    List<ReportModel> reportExpenseModelList;
    LocalDate beginDate;
    LocalDate endDate;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CategoryService categoriesService;

    @MockBean
    RegService regService;

    @Before
    public void setUp() {
        User user = new User()
                .setId(1L)
                .setEmail("vazyan93@gmail.com")
                .setPassword("Qwertasdf99")
                .setRoles(Collections.singleton(USER));

        Category category = new Category()
                .setId(1L)
                .setCategory("Eat")
                .setUser(user);

        Category updCategory = new Category()
                .setId(1L)
                .setCategory("Product")
                .setUser(user);

        categories = new ArrayList<>();
        categories.add(category);

        beginDate = LocalDate.of(2022, 9, 3);
        endDate = LocalDate.of(2022, 9, 9);

        ReportModel reportIncomeModel = new ReportModel()
                .setCategoryName("Eat")
                .setValue(BigDecimal.valueOf(100));

        ReportModel reportExpenseModel = new ReportModel()
                .setCategoryName("Product")
                .setValue(BigDecimal.valueOf(2300));

        reportIncomeModelList = new ArrayList<>();
        reportIncomeModelList.add(reportIncomeModel);

        reportExpenseModelList = new ArrayList<>();
        reportExpenseModelList.add(reportExpenseModel);

        when(regService.getUserById(1L)).thenReturn(user);

        when(categoriesService.getAllByUserId(1L)).thenReturn(categories);
        when(categoriesService.insert("Eat", 1L)).thenReturn(category);
        when(categoriesService.delete(1L, 1L)).thenReturn(true);
        when(categoriesService.update(1L, "Product", 1L)).thenReturn(updCategory);
        when(categoriesService.getIncomeByPeriod(1L,
                Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .thenReturn(reportIncomeModelList);
        when(categoriesService.getExpenseByPeriod(1L,
                Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant())))
                .thenReturn(reportIncomeModelList);

        BindingResult result = mock(BindingResult.class);
        when(!result.hasErrors()).thenReturn(true);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/categories"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", categories))
                .andExpect(view().name("categories"));

        verify(categoriesService, times(1)).getAllByUserId(1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getAddCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/categoryadd"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attribute("form", new CategoryForm()))
                .andExpect(view().name("categoryadd"));

        verify(categoriesService, times(1)).getAllByUserId(1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postAddCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/web/categoryadd").accept(MediaType.TEXT_HTML)
                        .param("name", "Eat"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/web/categories"));
        verify(categoriesService, times(1)).insert("Eat", 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getUpdCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/categoryupd"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attribute("form", new CategoryForm()))
                .andExpect(view().name("categoryupd"));

        verify(categoriesService, times(1)).getAllByUserId(1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postUpdCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/web/categoryupd").accept(MediaType.TEXT_HTML)
                        .param("id", "1")
                        .param("name", "Product"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/web/categories"));
        verify(categoriesService, times(1)).update(1L, "Product", 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getDelCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/categorydel"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attribute("form", new CategoryForm()))
                .andExpect(view().name("categorydel"));

        verify(categoriesService, times(1)).getAllByUserId(1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postDelCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/web/categorydel").accept(MediaType.TEXT_HTML)
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/web/categories"));
        verify(categoriesService, times(1)).delete(1L, 1L);
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getReportForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/web/reportform"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("form", new ReportForm()))
                .andExpect(view().name("reportform"));
    }

    @WithUserDetails(value = "vazyan93@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void postReportForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/web/reportform").accept(MediaType.TEXT_HTML)
                        .param("beginDate", "03.09.2022")
                        .param("endDate", "09.09.2022"))
                .andExpect(status().isOk())
                .andExpect(view().name("report"));

        verify(categoriesService, times(1)).getIncomeByPeriod(1L,
                Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        verify(categoriesService, times(1)).getExpenseByPeriod(1L,
                Date.from(beginDate.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}