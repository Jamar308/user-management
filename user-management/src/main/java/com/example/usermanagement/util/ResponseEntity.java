package com.example.usermanagement.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseEntity<T> {
    private String message;
    private int statusCode;
    private T entity;


    public ResponseEntity(String success, HttpStatus httpStatus) {
    }
}
