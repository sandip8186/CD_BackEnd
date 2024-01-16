package com.suma.consumer.repositories.activitylog;

import com.suma.consumer.model.entities.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ActivityLogRepository extends JpaRepository<ActivityLog,Long> {

}
