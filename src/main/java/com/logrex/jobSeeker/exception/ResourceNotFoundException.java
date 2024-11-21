package com.logrex.jobSeeker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private  String resourceName;
    private  String fieldName;
    private  Long fieldValue;

    public ResourceNotFoundException(String fieldName, String resourceName, Long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName, fieldValue)); // Post not found with id : 1
        this.fieldName = fieldName;
        this.resourceName = resourceName;
        this.fieldValue = fieldValue;
    }

    public ResourceNotFoundException(String fieldName,String resourceName) {
        super(String.format("%s not found with %s : '%s'", resourceName, fieldName));
        this.fieldName = fieldName;
        this.resourceName = resourceName;
    }
}
