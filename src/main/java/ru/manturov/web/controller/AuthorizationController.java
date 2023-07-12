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
import ru.manturov.entity.User;
import ru.manturov.web.form.LoginForm;
import ru.manturov.service.RegService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/web")
public class AuthorizationController {
    private final RegService service;
    private final CurrUser currUser;

    @GetMapping("/personal-area")
    public String index(Model model) {
        User user = currUser.getCurrentUser();
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getEmail());

        return "personal-area";
    }

    @GetMapping("/login-form")
    public String getLogin() {
        return "login-form";
    }

    @GetMapping("/registration")
    public String getRegistration(Model model) {
        model.addAttribute("form", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute("form") @Valid LoginForm form,
                                   BindingResult result,
                                   Model model) {
        if (!result.hasErrors()) {
            if (service.registration(form.getEmail(), form.getPassword()) == null) {
                result.addError(new FieldError("form", "email", "Неверный email"));
                result.addError(new FieldError("form", "password", "Неверный пароль"));
                return "redirect:/login-form";
            }
        } else {
            model.addAttribute(new FieldError("form",
                    "email",
                    "Пользователь с email" + form.getEmail() + " уже существует"));
            return "registration";
        }
        model.addAttribute("form", form);
        return "redirect:/login-form";
    }
}