package com.suma.consumer.constants.path;

public final class PathContatnt {

    private PathContatnt(){
    }

//    public static final String CD_PROJECT = "/consumerdurable";
    // --------------- User -------------------
    public static final String USER = "/user";
    public static final String GET_USER = "/getuser";
    public static final String SAVE_USER = "/adduser";

    public static final String GET_ALL_ADMINVP_USERS = "/getallusers";

    public static final String GET_ADMIN_TRADER_USER = "/getadminandtraderdetails";

    public static final String GET_TOKEN = "/getusertoken";

    //-------------- login ---------------------
    public static final String LOGIN = "/user";
    public static final String LOGIN_USER = "/login";

    public static final String RESET_PASSWORD = "/resetpassword";

    public static final String SET_NEW_PASSWORD = "/setnewpassword";
    public static final String LOGOUT = "/logout";
}
