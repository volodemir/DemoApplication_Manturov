package ru.manturov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.manturov.api.converter.UserToResponseConverter;
import ru.manturov.api.json.AuthResponse;
import ru.manturov.security.CustomUserDetails;
import ru.manturov.service.RegService;

@RequiredArgsConstructor
@Service
public class CurrentUser {
    private final RegService regService;
    private final UserToResponseConverter converter;

    public AuthResponse getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return converter.convert(regService.getUserById(userDetails.getId()));
    }
}
