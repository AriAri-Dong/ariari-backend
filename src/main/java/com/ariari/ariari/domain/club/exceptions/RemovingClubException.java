package com.ariari.ariari.domain.club.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class RemovingClubException extends CustomException {

    private static final String MESSAGE = "동아리를 삭제할 수 없습니다. 모든 동아리 회원을 삭제한 뒤 동아리 삭제가 가능합니다.";
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