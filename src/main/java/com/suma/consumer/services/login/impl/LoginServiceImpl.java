package com.suma.consumer.services.login.impl;

import com.suma.consumer.config.jwt.JwtTokenUtil;
import com.suma.consumer.constants.field.ConstantFields;
import com.suma.consumer.model.dto.ResetOtp;
import com.suma.consumer.model.dto.ResponseDto;
import com.suma.consumer.model.entities.User;
import com.suma.consumer.repositories.user.UserRepository;
import com.suma.consumer.services.login.LoginService;
import com.suma.consumer.services.mail.Mailservice;
import com.suma.consumer.services.messagesource.MessageSourceService;
import com.suma.consumer.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageSourceService messageSourceService;

    @Autowired
    LoginService loginService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    Mailservice mailService;

    @Autowired
    UserService userService;

    @Value("${reset.password.url}")
    private String resetPasswordURL;

    Date otpExpirationTime = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 10 minutes


    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUser(), user.getPassword(),
                new ArrayList<>());
    }
    @Override
    public ResponseEntity resetPassword(String userName) throws IOException, javax.mail.MessagingException {
        try {
            UserDetails userDetails = loginService.loadUserByUsername(userName);

            ArrayList<String> roleList=new ArrayList<>();

            String jwtToken = jwtTokenUtil.generateToken(userDetails,roleList);

            User user = userRepository.findByUserName(userName);

            String resetURL = resetPasswordURL+"resetpassword?email="+userName+"&token="+jwtToken;

            /* Otp Process Start*/

            String otp = loginService.generateOTP();
            System.out.println("-----------OTP -------------"+otp);
            user.setOtp(otp);
            user.setOtpExpirationTime(this.otpExpirationTime);
            user.setIsReset(false);
            userRepository.save(user);

            mailService.sendmail(userDetails, resetURL, "Sandip",otp);

            /* Otp Process Start*/

            return ResponseEntity.ok(new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
                    messageSourceService.getMessage(ConstantFields.SUCCESS)));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_ERROR)),
                            messageSourceService.getMessage(ConstantFields.USER_NOT_FOUND)));
        }
    }

    @Override
    public String generateOTP() {
        // Generate a 6-digit random OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public ResponseEntity setNewPassword(ResetOtp resetData) throws MessagingException, IOException {
        return ResponseEntity.ok(userService.setNewPassword(resetData));
    }

}
