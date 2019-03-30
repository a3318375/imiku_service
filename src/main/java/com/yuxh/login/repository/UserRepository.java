package com.yuxh.login.repository;

import com.yuxh.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByOpenid(String openid);

}
