package com.ariari.ariari.domain.club.club.invite.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class InvalidSchoolException extends CustomException {

    private static final String MESSAGE = "초대된 회원이 속한 학교와 해당 동아리가 소속된 학교가 일치하지 않습니다.";

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
