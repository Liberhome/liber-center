package com.liberhome.liber_center.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liberhome.liber_center.model.domain.User;
import com.liberhome.liber_center.service.UserService;
import com.liberhome.liber_center.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liberhome
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-01-10 19:08:14
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;
    /**
     * 盐值
     */
    private static final String SALT = "liberhome";
    /**
     * 用户登录态键
     */
    public static final String USER_LOGIN_STATE = "user_login_state";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassWord) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassWord)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassWord.length() < 8) {
            return -1;
        }
        //账户名特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        //密码与校验密码相同
        if (!userPassword.equals(checkPassWord)) {
            return -1;
        }
        // 账户不重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        //加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //插入数据到db
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.校验
          if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
        }
        //账户名特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        //加密
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptedPassword);
        User one = userMapper.selectOne(queryWrapper);
        if (one == null) {
            return null;
        }
        // 脱敏
        User safetyUser = new User();
        safetyUser.setId(one.getId());
        safetyUser.setUsername(one.getUsername());
        safetyUser.setUserAccount(one.getUserAccount());
        safetyUser.setAvatarUrl(one.getAvatarUrl());
        safetyUser.setGender(one.getGender());
        safetyUser.setPhone(one.getPhone());
        safetyUser.setEmail(one.getEmail());
        safetyUser.setUserStatus(one.getUserStatus());
        safetyUser.setCreateTime(one.getCreateTime());
        // 记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATE, one);
        return safetyUser;
    }
}




