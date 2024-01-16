package com.suma.consumer.services.login.impl;

import com.suma.consumer.config.jwt.JwtTokenUtil;
import com.suma.consumer.constants.field.ConstantFields;
import com.suma.consumer.model.dto.JWTResponse;
import com.suma.consumer.model.dto.JwtRequest;
import com.suma.consumer.model.dto.ResponseDto;
import com.suma.consumer.model.entities.User;
import com.suma.consumer.repositories.user.UserRepository;
import com.suma.consumer.services.activitylog.ActivityLogService;
import com.suma.consumer.services.login.JwtAuthService;
import com.suma.consumer.services.login.LoginService;
import com.suma.consumer.services.messagesource.MessageSourceService;
import com.suma.consumer.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class JwtAuthServiceImpl implements JwtAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    LoginService loginService;

    @Autowired
    ActivityLogService activityLogService;

    @Autowired
    MessageSourceService messageSourceService;



    @Override
    public ResponseEntity<ResponseDto> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception {

//        if (authenticationRequest.getPassword().equals("")){
//            final UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getUsername());
//            User user =userService.getUserDetails(authenticationRequest.getUsername());
////            ArrayList<Long> roleIdlist=userService.getUserRoleId(user.getId());
////            ArrayList<String> roleList=userService.getRole(roleIdlist);
//
//            ArrayList<String> roleList = new ArrayList<>();
//            roleList.add("Admin");
//
//            String token = jwtTokenUtil.generateToken(userDetails,roleList);
//
//            userService.userLoginActivity(user,token,authenticationRequest.getPassword());
//
//            return ResponseEntity.ok(new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
//                    messageSourceService.getMessage(ConstantFields.SUCCESS), new JWTResponse(token)));
//        }

        ResponseDto authResponse = authenticate(authenticationRequest.getUsername(),
                authenticationRequest.getPassword());
        //if we get Success response from AuthenticationManager

        if(authResponse.getMessage().equals(messageSourceService.getMessage(ConstantFields.SUCCESS))){
            // check user details
            try {
                final UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getUsername());

                User user =userService.getUserDetails(authenticationRequest.getUsername());
//
//                ArrayList<Long> roleIdlist=userService.getUserRoleId(user.getId());
//                ArrayList<String> roleList=userService.getRole(roleIdlist);
                ArrayList<String> roleList=new ArrayList<>();
                roleList.add("Admin");

                String token = jwtTokenUtil.generateToken(userDetails,roleList);

                activityLogService.saveLoginActivity(user.getUser(), messageSourceService.getMessage(ConstantFields.ACTIVITY_LOG_LOGIN_SUCCESS));

                if(user.getTokenExpTime() != null && user.getTokenExpTime().before(new Date())) {
                    user.setIsLoggedIn(false);
                    user.setToken("");
                    userRepository.save(user);
                }

//                if (user.getId() == 1){
//                    userService.userLoginActivity(user, token, authenticationRequest.getPassword());
//                    return ResponseEntity.ok(new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
//                            messageSourceService.getMessage(ConstantFields.SUCCESS), new JWTResponse(token)));
//                } else {
                    if(user.getIsLoggedIn().equals(false) || authenticationRequest.getForcedLogin().equals(true)) {
                        userService.userLoginActivity(user, token, authenticationRequest.getPassword());
                        return ResponseEntity.ok(new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
                                messageSourceService.getMessage(ConstantFields.SUCCESS), new JWTResponse(token)));

                    } else {
                        return ResponseEntity.ok(new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
                                messageSourceService.getMessage(ConstantFields.SUCCESS), "Already Logged In"));
                    }
//                }

            } catch (UsernameNotFoundException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_ERROR)),
                                messageSourceService.getMessage(ConstantFields.USER_NOT_FOUND)));
            }
        }
        //if we get Error response from AuthenticationManager
        else {
            // return auth error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse);
        }
    }


    public ResponseDto authenticate(String username, String password) throws Exception {

        User users = userRepository.findByUserName(username);
        if (users == null) {
            return new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_ERROR)),
                    messageSourceService.getMessage(ConstantFields.USER_NOT_FOUND));
        }

//        boolean isUserActive = checkIfActiveUser(username);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            if(!isUserActive) {
//                return new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_ERROR)),
//                        messageSourceService.getMessage(ConstantFields.USER_INACTIVE));
//            }
//            else {
                return new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_SUCCESS)),
                        messageSourceService.getMessage(ConstantFields.SUCCESS));
//            }
        } catch (BadCredentialsException e) {
            return new ResponseDto(Integer.valueOf(messageSourceService.getMessage(ConstantFields.RESPONSE_ERROR)),
                    messageSourceService.getMessage(ConstantFields.BAD_CREDS));
        }
    }
}
