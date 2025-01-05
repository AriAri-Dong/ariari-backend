package com.ariari.ariari.domain.club.bookmark.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class AlreadyExistsClubBookmarkException extends CustomException {

    private static final String MESSAGE = "이미 존재하는 동아리 북마크입니다.";
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