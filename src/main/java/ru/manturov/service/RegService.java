package ru.manturov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.manturov.entity.User;
import ru.manturov.exception.NoEntityException;
import ru.manturov.repository.UserRepository;
import ru.manturov.security.UserRole;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RegService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public boolean isExistUser(User user) {
        return repository.findByEmail(user.getEmail()).orElse(null) != null;
    }

    public User registration(String email, String password) {
        User user;
        if (email != null && password != null) {
            user = new User();
            user.setEmail(email);
            if (!isExistUser(user)) {
                user.setRoles(Collections.singleton(UserRole.USER));
                user.setPassword(passwordEncoder.encode(password));
                repository.save(user);
                return user;
            }
        }
        return null;
    }

    public User getUserByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new NoEntityException(email));
    }

    public User getUserById(Long userId) {
        return repository.findById(userId).orElseThrow(() -> new NoEntityException(userId));
    }
}