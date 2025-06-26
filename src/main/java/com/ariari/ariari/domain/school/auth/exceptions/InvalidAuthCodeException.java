package com.ariari.ariari.domain.school.auth.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidAuthCodeException extends CustomException {

    private static final String MESSAGE = "잘못된 학교 인증 코드입니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNPROCESSABLE_ENTITY;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }

}