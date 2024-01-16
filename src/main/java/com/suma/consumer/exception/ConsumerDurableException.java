package com.suma.consumer.exception;

public class ConsumerDurableException extends RuntimeException{

    public ConsumerDurableException(){
    }

    public ConsumerDurableException(String message){ super(message); }
}
