package com.suma.consumer.services.user.impl;

import com.suma.consumer.constants.field.ConstantFields;
import com.suma.consumer.model.dto.ResetOtp;
import com.suma.consumer.model.dto.ResponseDto;
import com.suma.consumer.model.entities.User;
import com.suma.consumer.repositories.user.UserRepository;
import com.suma.consumer.services.mail.Mailservice;
import com.suma.consumer.services.messagesource.MessageSourceService;
import com.suma.consumer.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public static final long JWT_TOKEN_VALIDITY = 5l*60l*60l;

    /* Following Template is Used to connect and disconnect Socket*/

    /*
    private final SimpMessagingTemplate template;

    public UserServiceImpl(SimpMessagingTemplate template) {
        this.template = template;
    }
    */

    @Autowired
    UserRepository userRepository;

    @Autowired
    Mailservice mailservice;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    MessageSourceService messageSourceService;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User getUserDetails(String email) {
        return userRepository.findByUserName(email);
    }
    @Override
    public void userLoginActivity(User user, String token, String password) {

        log.info("------- old Token " +user.getToken());
        log.info("------- new Token " +token);

        user.setIsLoggedIn(true);
        user.setToken(token);
        user.setTokenExpTime(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000));
        userRepository.save(user);
    }

    @Override
    public ResponseDto logoutUser(Long userId) {
        log.info("----------- logged Out ----------");

        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            User newUser = user.get();

            /* following code is used to disconnect socket when user is logOut
            this.template.convertAndSend("/oldToken", new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
                    messageSourceService.getMessage(ConstantFields.SUCCESS), "logoutAll "+token));
             */

            newUser.setIsLoggedIn(false);
            newUser.setToken(null);
            userRepository.save(newUser);
        }
        return new ResponseDto(Integer.parseInt(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
                messageSourceService.getMessage(ConstantFields.USER_LOGOUT_SUCCESS));
    }

    @Override
    public ResponseDto setNewPassword(ResetOtp resetData) throws MessagingException, IOException {

        User user = userRepository.findByUserName(resetData.getUserName());

        if(user.getIsReset().booleanValue() == false && resetData.getUserName().equals(user.getUser()) && resetData.getOtp().equals(user.getOtp())) {
            if(user.getOtpExpirationTime().before(new Date()) || new Date().getTime() - user.getOtpExpirationTime().getTime() > 10 * 60 * 1000){
                return new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_ERROR)),
                        messageSourceService.getMessage(ConstantFields.PASSWORD_RESET_EXPIRED_OTP));
            }
            user.setPassword(bcryptEncoder.encode(resetData.getNewPass()));
            user.setIsReset(true);
            userRepository.save(user);
            mailservice.sendCredMail(resetData.getUserName());

            log.info("Password updated for user: " + resetData.getUserName());
            return new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
                    messageSourceService.getMessage(ConstantFields.SUCCESS));
        }
        return new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_ERROR)),
                messageSourceService.getMessage(ConstantFields.PASSWORD_RESET_INVALID_OTP));
    }
}
