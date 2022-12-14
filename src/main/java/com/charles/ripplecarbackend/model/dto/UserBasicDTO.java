package com.charles.ripplecarbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserBasicDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String name;
    private String avatar;
    private Long level;
    private Long coin;
    private Long starCoin;
    private Long fan;
}
