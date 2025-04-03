package com.ariari.ariari.domain.club.clubmember.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class DelegateAdminException extends CustomException {

    private static final String MESSAGE = "관리 권한을 위임해야 합니다.";
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
