package com.ariari.ariari.domain.club.event.attendance.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidAttendanceKeyException extends CustomException {

    private static final String MESSAGE = "출석 키가 유효하지 않습니다. 유효기간이 만료되었거나 잘못된 키입니다.";
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