package com.ariari.ariari.domain.school.exceptions;


import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoSchoolAuthException extends CustomException {

    private static final String MESSAGE = "학교 인증이 필요합니다.";
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
