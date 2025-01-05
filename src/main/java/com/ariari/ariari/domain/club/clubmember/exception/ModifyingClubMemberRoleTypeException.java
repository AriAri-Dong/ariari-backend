package com.ariari.ariari.domain.club.clubmember.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ModifyingClubMemberRoleTypeException extends CustomException {

    private static final String MESSAGE = "이 API 는 최고 관리자 권한 위임이 불가능합니다. 최고 관리자 권한은 [최고 관리자 권한 위임] API 를 사용해야 합니다.";
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