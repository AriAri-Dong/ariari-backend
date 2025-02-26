package com.ariari.ariari.domain.member.member.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoMemberSearchingAuthException extends CustomException {

    private static final String MESSAGE = "회원 리스트 검색을 할 권한이 없습니다. 어떠한 동아리의 관리자 혹은 최고관리자 권한이 있어야 합니다.";
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