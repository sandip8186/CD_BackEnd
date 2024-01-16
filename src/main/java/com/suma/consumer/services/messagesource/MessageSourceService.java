package com.suma.consumer.services.messagesource;

public interface MessageSourceService {

    /**
     * Method responsible for obtaining a message according to its key.
     */
    String getMessage(String messageKey);

    /**
     * Method responsible for obtaining a message according to its key and their params.
     */
    String getMessage(String messageKey, String... args);
}
