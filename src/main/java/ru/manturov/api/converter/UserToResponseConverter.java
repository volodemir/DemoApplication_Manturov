package ru.manturov.api.converter;

import org.springframework.stereotype.Component;
import ru.manturov.entity.User;
import ru.manturov.api.json.AuthResponse;

@Component
public class UserToResponseConverter implements Converter<User, AuthResponse> {

    @Override
    public AuthResponse convert(User user) {
        return new AuthResponse(user.getId(), user.getEmail());
    }
}
