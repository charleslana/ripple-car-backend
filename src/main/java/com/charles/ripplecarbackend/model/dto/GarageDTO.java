package com.charles.ripplecarbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class GarageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;
}
