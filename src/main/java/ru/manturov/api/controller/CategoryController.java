package ru.manturov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.manturov.api.converter.CategoryToResponseConverter;
import ru.manturov.api.json.CategoryRequest;
import ru.manturov.api.json.CategoryResponse;
import ru.manturov.api.json.ReportRequest;
import ru.manturov.dao.ReportModel;
import ru.manturov.service.CategoryService;

import javax.validation.Valid;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CategoryController {
    private final CurrentUser currentUser;
    private final CategoryService service;
    private final CategoryToResponseConverter converter;

    @GetMapping("/category/all")
    public ResponseEntity<List<CategoryResponse>> categories() {
        return ok(service.getAllByUserId(currentUser.getCurrentUser().getId()).stream().map(converter::convert).collect(Collectors.toList()));
    }

    @PostMapping("/category/create")
    public ResponseEntity<CategoryResponse> insert(@RequestBody @Valid CategoryRequest request) {
        return ok(converter.convert(service.insert(request.getName(), currentUser.getCurrentUser().getId())));
    }

    @PostMapping("/category/del")
    public ResponseEntity<Boolean> delete(@RequestBody @Valid CategoryRequest request) {
        return ok(service.delete(request.getId(), currentUser.getCurrentUser().getId()));
    }

    @PostMapping("/category/upd")
    public ResponseEntity<CategoryResponse> update(@RequestBody @Valid CategoryRequest request) {
        return ok(converter.convert(service.update(request.getId(), request.getName(), currentUser.getCurrentUser().getId())));
    }

    @GetMapping("/report/income")
    public ResponseEntity<List<ReportModel>> getIncomeByPeriod(@RequestBody @Valid ReportRequest request) {
        return ok(service.getIncomeByPeriod(
                currentUser.getCurrentUser().getId(),
                Date.from(request.getBeginDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(request.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }

    @GetMapping("/report/expense")
    public ResponseEntity<List<ReportModel>> getExpenseByPeriod(@RequestBody @Valid ReportRequest request) {
        return ok(service.getExpenseByPeriod(
                currentUser.getCurrentUser().getId(),
                Date.from(request.getBeginDate().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(request.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant())));
    }
}