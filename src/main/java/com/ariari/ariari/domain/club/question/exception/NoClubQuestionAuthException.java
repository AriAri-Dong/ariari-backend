package com.ariari.ariari.domain.club.question.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoClubQuestionAuthException extends CustomException {

    private static final String MESSAGE = "해당 동아리 질문에 대한 권한이 없습니다.";
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