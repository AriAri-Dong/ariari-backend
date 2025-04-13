package com.ariari.ariari.domain.club.clubmember.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ClubMemberValidatorException  extends CustomException {

    private static final String MESSAGE = "요청한 회원과 동아리 회원이 일치하지 않습니다.";
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
