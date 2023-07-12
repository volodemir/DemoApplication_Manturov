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
import ru.manturov.web.form.AccountForm;
import ru.manturov.service.AccountService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/web")
public class AccountsController {
    private final AccountService service;
    private final CurrUser currUser;

    @GetMapping("/accounts")
    public String getAccounts(Model model) {
        model.addAttribute(
                "accounts",
                service.getAllByUserId(currUser.getCurrentUser().getId()));

        return "accounts";
    }

    @GetMapping("/accountadd")
    public String getAddAccount(Model model) {
        model.addAttribute("form", new AccountForm());
        return "accountadd";
    }

    @PostMapping("/accountadd")
    public String postAddAccount(@ModelAttribute("form") @Valid AccountForm form,
                                 BindingResult result,
                                 Model model) {
        if (!result.hasErrors()) {
            service.insert(form.getBalance(), form.getName(), currUser.getCurrentUser().getId());
            result.addError(new FieldError("form", "balance", "Введено нечисловое значение"));
            return "redirect:/web/accounts";
        }
        model.addAttribute("form", form);

        return "accountadd";
    }

    @GetMapping("/accountdelete")
    public String getDelAccount(Model model) {
        model.addAttribute(
                "accounts",
                service.getAllByUserId(currUser.getCurrentUser().getId()));

        model.addAttribute("form", new AccountForm());
        return "accountdelete";
    }

    @PostMapping("/accountdelete")
    public String postDelAccount(@ModelAttribute("form") @Valid AccountForm form,
                                 BindingResult result,
                                 Model model) {

        if (!result.hasErrors()) {
            service.delete(form.getId(), currUser.getCurrentUser().getId());
            result.addError(new FieldError("form", "id", "Введено нечисловое значение"));
            return "redirect:/web/accounts";
        }
        model.addAttribute("form", form);

        return "accountdelete";
    }
}