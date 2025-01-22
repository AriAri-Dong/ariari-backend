package com.ariari.ariari.domain.club.notice.exceptions;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class TooManyFixedClubNoticeException extends CustomException {

    private static final String MESSAGE = "상단 고정 공지사항은 3개를 초과할 수 없습니다..";
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