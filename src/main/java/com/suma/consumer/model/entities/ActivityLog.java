package com.suma.consumer.model.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity_log")
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator="seq")
    @GenericGenerator(name = "seq", strategy="increment")
    private long id;

    @Column(name="user_name")
    private String userName;

    @Column(name="user_id")
    private Long userId;

    @Column(name="request_url")
    private String requestUrl;

    @Column(name="log_message")
    private String logMessage;

    @Column(name="activity_time")
    private LocalDateTime activityTime;

}
