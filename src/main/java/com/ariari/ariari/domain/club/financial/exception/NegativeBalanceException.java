package com.ariari.ariari.domain.club.financial.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NegativeBalanceException extends CustomException {

    private static final String MESSAGE = "해당 장부 기록 이후 잔액이 음수가 됩니다. 잔액은 음수가 될 수 없습니다.";
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