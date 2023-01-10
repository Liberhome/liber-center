package com.liberhome.liber_center.service;

import com.liberhome.liber_center.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liberhome
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-01-10 19:08:14
*/
public interface UserService extends IService<User> {
    long userRegister(String userAccount, String userPassword, String checkPassword);
}
