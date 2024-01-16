package com.suma.consumer.controllers.login;


import com.suma.consumer.constants.path.PathContatnt;
import com.suma.consumer.model.dto.JwtRequest;
import com.suma.consumer.model.dto.ResetOtp;
import com.suma.consumer.model.dto.ResponseDto;
import com.suma.consumer.services.login.JwtAuthService;
import com.suma.consumer.services.login.LoginService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping(PathContatnt.LOGIN)
@Api(tags="Login" ,value = "Login" )
public class LoginController {

    @Autowired
    private JwtAuthService jwtAuthService;

    @Autowired
    private LoginService loginService;

    @PostMapping(PathContatnt.LOGIN_USER)
    public ResponseEntity<ResponseDto> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        ResponseEntity<ResponseDto> authenticationToken = jwtAuthService.createAuthenticationToken(authenticationRequest);

        return authenticationToken;
    }

    @GetMapping(PathContatnt.RESET_PASSWORD)
    public ResponseEntity<ResponseDto> resetPassword(@RequestParam String userName) throws IOException, javax.mail.MessagingException {
        return loginService.resetPassword(userName);
    }

    @PostMapping(PathContatnt.SET_NEW_PASSWORD)
    public ResponseEntity<ResponseDto> setNewPassword(@RequestBody ResetOtp authenticationRequest) throws MessagingException, IOException {
        return loginService.setNewPassword(authenticationRequest);
    }

}
