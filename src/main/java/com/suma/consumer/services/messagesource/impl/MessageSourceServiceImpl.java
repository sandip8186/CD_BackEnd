package com.suma.consumer.services.messagesource.impl;

import com.suma.consumer.services.messagesource.MessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageSourceServiceImpl implements MessageSourceService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String messageKey) {
        return messageSource.getMessage(messageKey,null, LocaleContextHolder.getLocale());
    }

    @Override
    public String getMessage(String messageKey, String... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }
}
