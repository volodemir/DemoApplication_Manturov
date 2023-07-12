package ru.manturov.api.json;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class AuthRequest {

    @Email
    @NotNull
    private String email;

    @NotNull
    @NotEmpty
    private String password;
}
