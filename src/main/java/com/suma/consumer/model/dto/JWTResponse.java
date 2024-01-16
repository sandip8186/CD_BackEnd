package com.suma.consumer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private  String jwttoken;









}
