package com.ariari.ariari.domain.club.club.invite.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ExistsClubMemberException extends CustomException {

    private static final String MESSAGE = "이미 해당 동아리 회원입니다.";
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
