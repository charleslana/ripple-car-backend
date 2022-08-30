package com.charles.ripplecarbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class CarBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String image;
    private Long acceleration;
    private Long topSpeed;
    private Long control;
    private Long weight;
    private Long toughness;
    private Long potency;
    private Long nitro;
}
