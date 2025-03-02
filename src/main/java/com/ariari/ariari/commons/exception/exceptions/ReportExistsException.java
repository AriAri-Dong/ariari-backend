package com.ariari.ariari.commons.exception.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ReportExistsException extends CustomException {
    private static final String MESSAGE = "해당 신고가 이미 존재합니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
