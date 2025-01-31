package com.ariari.ariari.domain.member.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ExistingNicknameException extends CustomException {

    private static final String MESSAGE = "중복 닉네임이 있습니다. 다른 닉네임으로 수정해주세요.";
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