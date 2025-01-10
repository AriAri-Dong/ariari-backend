package com.ariari.ariari.domain.recruitment.exception;

import com.ariari.ariari.commons.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ExistsDuplicatePeriodRecruitment extends CustomException {

    private static final String MESSAGE = "모집 기간이 겹치는 모집이 있습니다. 해당 모집을 조기종료하거나 다른 모집 기간으로 설정 후 등록해주세요.";
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