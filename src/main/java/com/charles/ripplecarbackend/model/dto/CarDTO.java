package com.charles.ripplecarbackend.model.dto;

import com.charles.ripplecarbackend.enums.CarClassEnum;
import com.charles.ripplecarbackend.enums.RarityEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class CarDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    private String image;

    @NotNull
    @Min(0)
    private Long acceleration;

    @NotNull
    @Min(0)
    private Long topSpeed;

    @NotNull
    @Min(0)
    private Long control;

    @NotNull
    @Min(0)
    private Long weight;

    @NotNull
    @Min(0)
    private Long toughness;

    @NotNull
    @Min(0)
    private Long potency;

    @NotNull
    @Min(0)
    private Long nitro;

    @NotNull
    private RarityEnum rarity;

    @NotNull
    private CarClassEnum carClass;
}
