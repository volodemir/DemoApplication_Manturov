package ru.manturov.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.manturov.web.form.CategoryForm;
import ru.manturov.service.CategoryService;
import ru.manturov.web.form.ReportForm;

import javax.validation.Valid;
import java.time.ZoneId;
import java.util.Date;

@RequiredArgsConstructor
@Controller
@RequestMapping("/web")
public class CategoriesController {
    private final CategoryService service;
    private final CurrUser currUser;

    @GetMapping("/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", service.getAllByUserId(currUser.getCurrentUser().getId()));

        return "categories";
    }

    @GetMapping("/categoryadd")
    public String getAddCategory(Model model) {
        model.addAttribute("categories", service.getAllByUserId(currUser.getCurrentUser().getId()));
        model.addAttribute("form", new CategoryForm());

        return "categoryadd";
    }

    @PostMapping("/categoryadd")
    public String postAddCategory(@ModelAttribute("form") @Valid CategoryForm form,
                                  BindingResult result,
                                  Model model) {
        if (!result.hasErrors()) {
            service.insert(form.getName(), currUser.getCurrentUser().getId());
            result.addError(new FieldError("form", "name", "Поле не может быть пустым"));
            return "redirect:/web/categories";
        }
        model.addAttribute("form", form);

        return "categoryadd";
    }

    @GetMapping("/categoryupd")
    public String getUpdCategory(Model model) {
        model.addAttribute("categories", service.getAllByUserId(currUser.getCurrentUser().getId()));
        model.addAttribute("form", new CategoryForm());

        return "categoryupd";
    }

    @PostMapping("/categoryupd")
    public String postUpdCategory(@ModelAttribute("form") @Valid CategoryForm form,
                                  BindingResult result,
                                  Model model) {
        if (!result.hasErrors()) {
            service.update(form.getId(), form.getName(), currUser.getCurrentUser().getId());
            result.addError(new FieldError("form", "id", "Поле не может быть пустым"));
            result.addError(new FieldError("form", "name", "Поле не может быть пустым"));
            return "redirect:/web/categories";
        }
        model.addAttribute("form", form);

        return "categoryupd";
    }

    @GetMapping("/categorydel")
    public String getDelCategory(Model model) {
        model.addAttribute("categories", service.getAllByUserId(currUser.getCurrentUser().getId()));
        model.addAttribute("form", new CategoryForm());

        return "categorydel";
    }

    @PostMapping("/categorydel")
    public String postDelCategory(@ModelAttribute("form") @Valid CategoryForm form,
                                  BindingResult result,
                                  Model model) {
        if (!result.hasErrors()) {
            service.delete(form.getId(), currUser.getCurrentUser().getId());
            result.addError(new FieldError("form", "id", "Поле не может быть пустым"));
            return "redirect:/web/categories";
        }
        model.addAttribute("form", form);

        return "categorydel";
    }

    @GetMapping("/reportform")
    public String getReportForm(Model model) {
        model.addAttribute("form", new ReportForm());

        return "reportform";
    }

    @PostMapping("/reportform")
    public String postReportForm(@ModelAttribute("form") @Valid ReportForm form,
                                 BindingResult result,
                                 Model model) {
        Long userId = currUser.getCurrentUser().getId();
        if (!result.hasErrors()) {
            model.addAttribute("reportIncome", service.getIncomeByPeriod(
                    userId,
                    Date.from(form.getBeginDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(form.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant())));
            model.addAttribute("reportExpense", service.getExpenseByPeriod(
                    userId,
                    Date.from(form.getBeginDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                    Date.from(form.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant())));
            result.addError(new FieldError("form", "beginDate", "Необходимо ввести дату в формате дд.ММ.гггг!"));
            result.addError(new FieldError("form", "endDate", "Необходимо ввести дату в формате дд.ММ.гггг!"));

            return "report";
        }
        model.addAttribute("form", form);

        return "reportform";
    }
}