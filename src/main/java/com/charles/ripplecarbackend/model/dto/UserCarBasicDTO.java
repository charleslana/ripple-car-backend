package com.charles.ripplecarbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserCarBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long level;
    private Long experience;
    private Boolean broken;
    private Boolean busted;
    private CarBasicDTO car;
}
