package com.maroc_ouvrage.semployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoEmployeesFoundException extends RuntimeException {
    public NoEmployeesFoundException(Long deptId) {
        super("No employees found for department with ID: " + deptId);
    }
}

