package com.suma.consumer.constants.field;

public final class ConstantFields {

    private ConstantFields(){
    }
    // Resposne Dto status code
    public static final String RESPONSE_ERROR = "response.status.error";
    public static final String RESPONSE_SUCCESS = "response.status.success";
    public static final String USER_LOGOUT_SUCCESS = "response.status.success";
    //Jwt
    public static final String BAD_CREDS = "jwt.error.bad.creds";
    public static final String SUCCESS = "jwt.message.success";
    public static final String USER_NOT_FOUND = "jwt.error.user.found";
    public static final String ACTIVITY_LOG_LOGIN_SUCCESS = "activity.log.login.success";


    // OTP
    public static final String PASSWORD_RESET_INVALID_OTP = "password.reset.inavlid.otp";
    public static final String PASSWORD_RESET_EXPIRED_OTP = "password.reset.expired.otp";







}
