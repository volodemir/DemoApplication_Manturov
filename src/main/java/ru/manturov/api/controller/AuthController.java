package ru.manturov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.manturov.api.converter.UserToResponseConverter;
import ru.manturov.api.json.AuthRequest;
import ru.manturov.api.json.AuthResponse;
import ru.manturov.service.RegService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class AuthController {
    private final CurrentUser currentUser;
    private final RegService regsService;
    private final UserToResponseConverter converter;

    @PostMapping("/registration")
    public ResponseEntity<AuthResponse> registration(@RequestBody @Valid AuthRequest request,
                                                     HttpServletRequest httpServletRequest) {
        AuthResponse user = converter.convert(regsService.registration(request.getEmail(), request.getPassword()));
        setSessionAttributeUserId(httpServletRequest, user);
        return ok(user);
    }

    @GetMapping("/getuserinfo")
    public @ResponseBody ResponseEntity<AuthResponse> getUserInfo() {
        return ok(currentUser.getCurrentUser());
    }

    public void setSessionAttributeUserId(HttpServletRequest request, AuthResponse user) {
        request.getSession().setAttribute("userId", user.getId());
    }
}