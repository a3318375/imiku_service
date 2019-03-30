package com.yuxh.login.service;

import com.yuxh.blog.contact.BaseJsonResult;
import com.yuxh.login.domain.User;
import com.yuxh.login.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public BaseJsonResult saveUser(User user) {
        User oldUser;
        Date date = new Date();
        if (StringUtils.isNoneBlank(user.getOpenid())) {
            oldUser = userRepository.findByOpenid(user.getOpenid());
            if (oldUser != null) {
                user.setId(oldUser.getId());
                user.setCreateTime(oldUser.getCreateTime());
            } else {
                user.setCreateTime(date);
            }
        } else {
            user.setCreateTime(date);
        }
        user.setUpdateTime(date);
        userRepository.save(user);
        return BaseJsonResult.success();
    }

    public BaseJsonResult findLatelyUser() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "updateTime"));
        Page<User> postIn = userRepository.findAll(pageable);
        return BaseJsonResult.success("page", postIn);
    }
}
