package com.ariari.ariari.domain.school.auth.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ClubMemberRoleAdminLockException extends CustomException {

    private static final String MESSAGE = "내가 동아리장인 교내 동아리가 있습니다. 동아리장 권한을 타인에게 양도하거나 동아리를 폐쇄 후 학교 인증 취소 해주세요.";
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