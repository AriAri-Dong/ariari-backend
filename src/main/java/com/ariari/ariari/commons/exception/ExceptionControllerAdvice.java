package com.ariari.ariari.commons.exception;

import com.ariari.ariari.commons.exception.dto.ExceptionRes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ExceptionRes handleInternalException(HttpServletRequest request, Exception e) {
        log.error("exception !!", e);
        return ExceptionRes.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("알 수 없는 에러가 발생했습니다.")
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ExceptionRes handleCustomException(HttpServletRequest request, CustomException e) {
        return ExceptionRes.builder()
                .code(e.getHttpStatus().value())
                .message(e.getMessage())
                .build();
    }

}
