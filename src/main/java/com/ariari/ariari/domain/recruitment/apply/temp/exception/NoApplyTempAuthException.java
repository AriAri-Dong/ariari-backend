package com.ariari.ariari.domain.recruitment.apply.temp.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoApplyTempAuthException extends CustomException {

    private static final String MESSAGE = "해당 임시 지원서에 대한 권한이 없습니다.";
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