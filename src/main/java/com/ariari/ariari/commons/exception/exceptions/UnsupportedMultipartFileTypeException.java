package com.ariari.ariari.commons.exception.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UnsupportedMultipartFileTypeException extends CustomException {
    private static final String MESSAGE = "허용되지 않은 파일 타입입니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNSUPPORTED_MEDIA_TYPE;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}
