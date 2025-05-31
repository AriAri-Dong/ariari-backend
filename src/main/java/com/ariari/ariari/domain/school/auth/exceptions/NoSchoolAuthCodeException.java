package com.ariari.ariari.domain.school.auth.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoSchoolAuthCodeException extends CustomException {

    private static final String MESSAGE = "존재하지 않는 학교 인증 코드입니다. 학교 인증 코드가 발급되지 않았거나 만료되었을 수 있습니다. (만료시간 : 5분)";
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