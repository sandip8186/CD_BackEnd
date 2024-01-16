package com.suma.consumer.model.dto;


import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetOtp {

    private String userName;
    private String newPass;
    private String otp;


}
