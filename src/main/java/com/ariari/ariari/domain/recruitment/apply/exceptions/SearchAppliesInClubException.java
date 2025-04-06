package com.ariari.ariari.domain.recruitment.apply.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class SearchAppliesInClubException extends CustomException {

    private static final String MESSAGE = "날짜 검색 시 둘 중 하나만 null 일 수 없습니다.";
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