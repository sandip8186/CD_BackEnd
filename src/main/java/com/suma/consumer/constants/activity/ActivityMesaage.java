package com.suma.consumer.constants.activity;

import java.util.HashMap;
import java.util.Map;

public class ActivityMesaage {

    public Map<String, String> myVal() {
        Map<String, String> urlMappingMap = new HashMap<>();

        urlMappingMap.put("/etex/deals/getmydeals", "My Company Deals.");
        urlMappingMap.put("/etex/user/getcompany", "New User Creation.");
        urlMappingMap.put("/etex/user/getadminandtraderdetails", "User Access Management.");
        urlMappingMap.put("/etex/user/resetpassword", "Reset Password API");
        urlMappingMap.put("/etex/user/setnewpassword", "Set New Password");
        return urlMappingMap;
    }

}
