package com.liberhome.liber_center.service;

import com.liberhome.liber_center.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author liberhome
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-01-10 19:08:14
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     *用户登录
     * @param userAccount
     * @param userPassword
     * @return 脱敏后的信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
}
