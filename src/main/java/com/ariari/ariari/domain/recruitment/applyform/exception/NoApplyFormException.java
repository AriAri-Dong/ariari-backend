package com.ariari.ariari.domain.recruitment.applyform.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoApplyFormException extends CustomException {

    private static final String MESSAGE = "등록된 지원 형식이 없습니다. 먼저 지원 형식을 등록해주세요.";
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
