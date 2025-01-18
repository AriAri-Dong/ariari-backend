package com.ariari.ariari.domain.recruitment.apply.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AlreadyBelongToClubException extends CustomException {

    private static final String MESSAGE = "이미 속해있는 동아리의 모집입니다.";
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