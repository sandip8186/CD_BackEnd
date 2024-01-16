package com.suma.consumer.services.user;

import com.suma.consumer.model.dto.ResetOtp;
import com.suma.consumer.model.dto.ResponseDto;
import com.suma.consumer.model.entities.User;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.io.IOException;

public interface UserService {

    User getUserDetails(String email);

    void userLoginActivity(User user, String token, String password);

    ResponseDto logoutUser(Long userId);

    ResponseDto setNewPassword(ResetOtp resetData) throws MessagingException, IOException;


}
