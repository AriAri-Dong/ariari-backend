package com.ariari.ariari.domain.system.image.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NotBelongInSystemNoticeException extends CustomException {

    private static final String MESSAGE = "공지사항 이미지를 삭제할 수 없습니다. 이 시스템 공지사항에 속하지 않은 이미지입니다.";
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
