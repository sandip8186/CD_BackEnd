package com.suma.consumer.services.login;

import com.suma.consumer.model.dto.ResetOtp;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;


public interface LoginService {

    UserDetails loadUserByUsername(String username);

    ResponseEntity resetPassword(String userName) throws IOException, javax.mail.MessagingException;

    String generateOTP();

    ResponseEntity setNewPassword(ResetOtp resetData) throws MessagingException, IOException;


}
