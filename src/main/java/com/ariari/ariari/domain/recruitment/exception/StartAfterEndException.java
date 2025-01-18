package com.ariari.ariari.domain.recruitment.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StartAfterEndException extends CustomException {

    private static final String MESSAGE = "모집 시작 날짜/시간이 모집 종료 날짜/시간보다 이후일 수 없습니다..";
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