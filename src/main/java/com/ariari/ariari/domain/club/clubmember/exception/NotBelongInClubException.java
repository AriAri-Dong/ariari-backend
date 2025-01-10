package com.ariari.ariari.domain.club.clubmember.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NotBelongInClubException extends CustomException {

    private static final String MESSAGE = "이 동아리에 속하지 않은 동아리 회원입니다.";
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