package com.example.blog;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFountException extends RuntimeException{
    public NotFountException(){
    }

    public NotFountException(String message){
        super(message);
    }

    public NotFountException(String message, Throwable cause){
        super(message, cause);
    }
}
