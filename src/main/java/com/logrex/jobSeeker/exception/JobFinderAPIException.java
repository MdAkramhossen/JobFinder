package com.logrex.jobSeeker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JobFinderAPIException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public JobFinderAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public JobFinderAPIException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }


}
