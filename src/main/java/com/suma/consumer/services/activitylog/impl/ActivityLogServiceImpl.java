package com.suma.consumer.services.activitylog.impl;

import com.suma.consumer.constants.activity.ActivityMesaage;
import com.suma.consumer.model.entities.ActivityLog;
import com.suma.consumer.repositories.activitylog.ActivityLogRepository;
import com.suma.consumer.repositories.user.UserRepository;
import com.suma.consumer.services.activitylog.ActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ActivityLogServiceImpl implements ActivityLogService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private ActivityLogRepository activityLogRepository;


    @Override
    public void save() {

        ActivityMesaage activityMesaage=new ActivityMesaage();

        Map<String,String> urlMappingMap=activityMesaage.myVal();

        String requestFullUrl=request.getRequestURL().toString();
        String requestUrl=request.getRequestURI();

        if(requestFullUrl.contains("localhost")){
            requestUrl="/cd"+requestUrl;
        }
        String logMessage;
        if (urlMappingMap.containsKey(requestUrl)) {
            ActivityLog log = new ActivityLog();
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            String userName = "";
            if (principal instanceof UserDetails) {
                userName = ((UserDetails) principal).getUsername();
            } else {
                userName = principal.toString();
            }
            if(userName!=null){
                Long userId= userRepository.findByUserName(userName).getId();

                logMessage=urlMappingMap.get(requestUrl);

                log.setUserName(userName);
                log.setUserId(userId);
                log.setRequestUrl(request.getRequestURL().toString());
                log.setLogMessage(logMessage);
                log.setActivityTime(LocalDateTime.now());
                try {
                    activityLogRepository.save(log);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveLoginActivity(String userName,String logMessage){
        ActivityLog log = new ActivityLog();
        Long userId= userRepository.findByUserName(userName).getId();

        log.setUserName(userName);
        log.setRequestUrl(request.getRequestURL().toString());
        log.setLogMessage(logMessage);
        log.setActivityTime(LocalDateTime.now());
        log.setUserId(userId);
        activityLogRepository.save(log);
    }
}
