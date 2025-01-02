package com.ariari.ariari.domain.club.question.answer.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoClubAnswerException extends CustomException {

    private static final String MESSAGE = "해당 동아리 질문에는 답변이 없습니다..";
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