package com.sparta.deliveryproject.exception;

public class NotValidCategoryException extends IllegalArgumentException{
    public NotValidCategoryException (String message){
        super(message);
    }
}
