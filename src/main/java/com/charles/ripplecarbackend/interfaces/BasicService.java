package com.charles.ripplecarbackend.interfaces;

import com.charles.ripplecarbackend.config.exception.BusinessRuleException;
import com.charles.ripplecarbackend.model.dto.ResponseDTO;

public interface BasicService {

    BusinessRuleException getException(String message);

    ResponseDTO getSuccess(String message);
}
