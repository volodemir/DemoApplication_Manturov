package ru.manturov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.manturov.api.converter.TransactionToResponseConverter;
import ru.manturov.api.json.TransactionRequest;
import ru.manturov.api.json.TransactionResponse;
import ru.manturov.service.TransactionService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TransactionController {
    private final CurrentUser currentUser;
    private final TransactionService service;
    private final TransactionToResponseConverter converter;

    @GetMapping("/transaction/all")
    public ResponseEntity<List<TransactionResponse>> transactions() {
        Long userId = currentUser.getCurrentUser().getId();
        return ok(service.findAllByUserId(userId, userId).stream().map(converter::convert).collect(Collectors.toList()));
    }

    @PostMapping("/transaction/create")
    public ResponseEntity<TransactionResponse> insert(@RequestBody @Valid TransactionRequest request) {
        return ok(converter.convert(service.insert(request.getValue(), request.getFromAccountId(), request.getToAccountId(), request.getCategories())));
    }
}