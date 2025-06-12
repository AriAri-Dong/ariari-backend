package com.ariari.ariari.domain.club.club.invite.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidClubInviteException extends CustomException {

    private static final String MESSAGE = "초대 받은 동아리가 일치 하지 않습니다.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    @Override
    public String getMessage() {
        return MESSAGE;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HTTP_STATUS;
    }
}