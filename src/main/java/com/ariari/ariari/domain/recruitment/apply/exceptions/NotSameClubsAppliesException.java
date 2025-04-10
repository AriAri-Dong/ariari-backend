package com.ariari.ariari.domain.recruitment.apply.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NotSameClubsAppliesException extends CustomException {

    private static final String MESSAGE = "지원서에 대한 배치 처리는 같은 동아리 내의 지원서에 대해서만 가능합니다.";
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