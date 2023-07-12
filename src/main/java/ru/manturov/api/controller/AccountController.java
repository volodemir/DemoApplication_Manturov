package ru.manturov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.manturov.api.converter.AccountToResponseConverter;
import ru.manturov.api.json.AccountRequest;
import ru.manturov.api.json.AccountResponse;
import ru.manturov.service.AccountService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AccountController {
    private final CurrentUser currentUser;
    private final AccountService accountService;
    private final AccountToResponseConverter accountToResponseConverter;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponse>> getAccounts() {
        return ok(accountService.getAllByUserId(currentUser.getCurrentUser().getId()).stream().map(accountToResponseConverter::convert).collect(Collectors.toList()));
    }

    @PostMapping("/account/create")
    public ResponseEntity<AccountResponse> insert(@RequestBody @Valid AccountRequest request) {
        return ok(accountToResponseConverter.convert(accountService.insert(request.getBalance(), request.getName(), currentUser.getCurrentUser().getId())));
    }

    @PostMapping("/account/del")
    public ResponseEntity<Boolean> delete(@RequestBody @Valid AccountRequest request) {
        return ok(accountService.delete(request.getId(), currentUser.getCurrentUser().getId()));
    }

    @PostMapping("/account/upd")
    public ResponseEntity<AccountResponse> update(@RequestBody @Valid AccountRequest request) {
        return ok(accountToResponseConverter.convert(accountService.update(request.getId(), request.getName(), request.getBalance(), currentUser.getCurrentUser().getId())));
    }
}