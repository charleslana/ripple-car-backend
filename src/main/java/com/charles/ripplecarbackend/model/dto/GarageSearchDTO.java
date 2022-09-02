package com.charles.ripplecarbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class GarageSearchDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private UserBasicDTO user;
    private List<UserCarBasicDTO> userCars;
}
