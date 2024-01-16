package com.suma.consumer.services.activitylog;

public interface ActivityLogService {

    void save();

    void saveLoginActivity(String userName,String logMessage);
}
