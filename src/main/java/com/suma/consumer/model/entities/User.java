package com.suma.consumer.model.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user_master")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq")
    @GenericGenerator(name = "seq", strategy = "increment")
    private Long id;

    @Column(name = "user_name")
    private String user;

    private String password;

    private String role;

    @Column(name = "token")
    private String token;

    @Column(name = "token_exp_time")
    private Date tokenExpTime;

    @Column(name="is_loggedIn")
    @Builder.Default
    private Boolean isLoggedIn=false;

    @Column(name = "user_otp")
    private String otp;

    @Column(name = "otp_exp_time")
    private Date otpExpirationTime;

    @Column(name="is_Reset_Pass")
    @Builder.Default
    private Boolean isReset = false;

}
