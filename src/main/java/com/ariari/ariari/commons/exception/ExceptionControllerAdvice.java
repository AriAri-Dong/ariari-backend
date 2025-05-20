package com.ariari.ariari.commons.exception;

import com.ariari.ariari.commons.exception.dto.ExceptionRes;
import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ExceptionRes handleInternalException(HttpServletRequest request, Exception e) {
        log.error("exception !!", e);
        handleSentryError(request, e, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ExceptionRes.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage()) // 배포 후 수정 예정
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException.class)
    public ExceptionRes handleNullPointerException(HttpServletRequest request, NullPointerException e){
        log.info("exception", e);
        handleSentryError(request, e, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부 오류가 발생했습니다. 관리자에게 문의하세요.");
        return ExceptionRes.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public ExceptionRes handleIllegalStateException(HttpServletRequest request, IllegalStateException e){
        log.info("exception", e);
        handleSentryError(request, e, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 상태가 올바르지 않습니다. 잠시 후 다시 시도해주세요.");
        return ExceptionRes.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ExceptionRes handleCustomException(HttpServletRequest request, CustomException e) {
        log.info("exception", e);
        //handleSentryError(request, e, HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ExceptionRes.builder()
                .code(e.getHttpStatus().value())
                .message(e.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ExceptionRes handleNoResourceFoundException(HttpServletRequest request, NoResourceFoundException e) {
        log.info("exception", e);
        return ExceptionRes.builder()
                .code(404)
                .message("존재하지 않는 경로입니다.")
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataAccessException.class)
    public ExceptionRes handleDataAccessException(HttpServletRequest request, DataAccessException e) {
        log.error("db 에러 : ", e);
        handleSentryError(request, e, HttpStatus.BAD_REQUEST.value(), "DB 접근 중 에러가 발생했습니다." + e.getMessage());
        return ExceptionRes.builder()
                .code(400)
                .message("DB 접근 중 에러가 발생했습니다." + e.getMessage())
                .build();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(HttpServletRequest request, MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        //handleSentryError(request, ex, HttpStatus.BAD_REQUEST.value(), "검증 오류입니다.");
        // 검증 실패한 필드와 그에 대한 오류 메시지를 반환
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        // 400 응답과 함께 오류 메시지 반환
        return ResponseEntity.badRequest().body(errors);
    }

    private void handleSentryError(HttpServletRequest request, Exception e, int httpStatus, String message){

            Sentry.withScope(scope -> {
                scope.setTag("HTTP_STATUS", String.valueOf(httpStatus));
                scope.setTag("API", request.getRequestURI());
                scope.setExtra("QueryParams", request.getQueryString());
                scope.setTag("OS", request.getHeader("User-Agent")); // 운영체제, 브라우저 정보
                scope.setExtra("AppVersion", request.getHeader("X-App-Version")); // 앱 버전 (모바일이면)
                Sentry.captureException(e);
                Sentry.captureMessage(request.getRequestURI() + " - " + message);
                Sentry.flush(3000);
            });

    }

}
