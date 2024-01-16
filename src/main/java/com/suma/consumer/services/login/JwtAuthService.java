package com.suma.consumer.services.login;

import com.suma.consumer.model.dto.JwtRequest;
import com.suma.consumer.model.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public interface JwtAuthService {

    ResponseEntity<ResponseDto> createAuthenticationToken(JwtRequest authenticationRequest) throws Exception;
}
