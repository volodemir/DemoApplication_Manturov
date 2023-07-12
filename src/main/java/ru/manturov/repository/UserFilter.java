package ru.manturov.repository;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.manturov.security.UserRole;

@Data
@Accessors(chain = true)
public class UserFilter {
    private String emailLike;
    private UserRole userRole;
}
