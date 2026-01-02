package com.ariari.ariari.commons.exception;

import org.springframework.http.HttpStatus;

public class FileControlException extends CustomException {

    private static final String MESSAGE = "파일 처리 중 에러가 발생하였습니다.";
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