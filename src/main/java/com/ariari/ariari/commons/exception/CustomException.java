package com.ariari.ariari.commons.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends RuntimeException {

    public abstract String getMessage();

    public abstract HttpStatus getHttpStatus();

}
