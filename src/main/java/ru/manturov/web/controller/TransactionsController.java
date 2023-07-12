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
import ru.manturov.web.form.TransactionForm;
import ru.manturov.service.AccountService;
import ru.manturov.service.CategoryService;
import ru.manturov.service.TransactionService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/web")
public class TransactionsController {
    private final AccountService accountService;
    private final CategoryService categoryService;
    private final TransactionService service;
    private final CurrUser currUser;

    @GetMapping("/transactions")
    public String getTransactions(Model model) {
        Long userId = currUser.getCurrentUser().getId();
        model.addAttribute("transactions", service.findAllByUserId(userId, userId));

        return "transactions";
    }

    @GetMapping("/transactionadd")
    public String getTransactionAdd(Model model) {
        Long userId = currUser.getCurrentUser().getId();
        model.addAttribute("accounts", accountService.getAllByUserId(userId));
        model.addAttribute("categories", categoryService.getAllByUserId(userId));
        model.addAttribute("transactionForm", new TransactionForm());

        return "transactionadd";
    }

    @PostMapping("/transactionadd")
    public String postTransactionAdd(@ModelAttribute("transactionForm") @Valid TransactionForm form,
                                     BindingResult result,
                                     Model model) {

        if (!result.hasErrors()) {
            service.insert(form.getValue(), form.getFromAccountId(), form.getToAccountId(), form.getCategories());

            result.addError(new FieldError("transactionForm", "value", "Введено нечисловое значение"));
            result.addError(new FieldError("transactionForm", "fromAccountId", "Введено нечисловое значение"));
            result.addError(new FieldError("transactionForm", "toAccountId", "Введено нечисловое значение"));
            result.addError(new FieldError("transactionForm", "categories", "Поле не может быть пустым"));
            return "redirect:/web/transactions";
        }
        model.addAttribute("transactionForm", form);

        return "transactionadd";
    }
}
