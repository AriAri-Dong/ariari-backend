package com.ariari.ariari.commons.auth.oauth.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidSignUpKeyException extends CustomException {

    private static final String MESSAGE = "부적절한 회원가입 키입니다..";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
