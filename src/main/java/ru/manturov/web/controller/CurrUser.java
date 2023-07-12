package ru.manturov.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.manturov.entity.User;
import ru.manturov.security.CustomUserDetails;
import ru.manturov.service.RegService;

@RequiredArgsConstructor
@Service
public class CurrUser {
    private final RegService regsService;

    public User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return regsService.getUserById(userDetails.getId());
    }
}