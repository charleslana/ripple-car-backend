package com.charles.ripplecarbackend.config.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
    public class BusinessRuleException extends RuntimeException implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private String status;
    private String message;
    private String[] values;

    public BusinessRuleException(String status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public BusinessRuleException(String status, String message, String... values) {
        super(message);
        this.status = status;
        this.message = message;
        this.values = values;
    }
}
