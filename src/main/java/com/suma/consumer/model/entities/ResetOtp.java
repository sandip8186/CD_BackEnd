package com.suma.consumer.model.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reset_otp")
public class ResetOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")

    private String otp;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "otp_expiration_time")
    private Date otpExpirationTime;
}
