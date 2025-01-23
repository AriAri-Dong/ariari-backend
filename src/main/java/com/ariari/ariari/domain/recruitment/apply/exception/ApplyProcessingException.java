package com.ariari.ariari.domain.recruitment.apply.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ApplyProcessingException extends CustomException {

    private static final String MESSAGE = "지원서를 처리할 수 없습니다. 이미 거절이나 승인된 지원서에 대해 다른 처리를 할 수 없습니다.";
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