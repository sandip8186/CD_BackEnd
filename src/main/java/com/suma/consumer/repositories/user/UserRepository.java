package com.suma.consumer.repositories.user;

import com.suma.consumer.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "SELECT * FROM user_master WHERE user_name = :userName", nativeQuery = true)
    User findByUserName(String userName);

}
