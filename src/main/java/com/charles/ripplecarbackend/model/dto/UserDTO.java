package com.charles.ripplecarbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Email(message = "email should be valid")
    @NotNull
    @Size(max = 50)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 6, max = 50)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9]([_](?![_])|[a-zA-Z0-9]){1,18}[a-zA-Z0-9]$", message = "O nome pode conter letras, números e caractere underscore separando a cada letra, o primeiro e último caractere deve ser alfanuméricos")
    private String name;
}
