package com.ariari.ariari.commons.auth.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ExistingAdminRoleException extends CustomException {

    private static final String MESSAGE = "내가 동아리장인 동아리가 있습니다. 동아리장 권한을 타인에게 양도하거나 동아리를 폐쇄 후 회원 탈퇴 해주세요.";
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
