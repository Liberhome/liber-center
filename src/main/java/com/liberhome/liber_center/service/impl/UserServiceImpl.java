package com.liberhome.liber_center.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liberhome.liber_center.model.domain.User;
import com.liberhome.liber_center.service.UserService;
import com.liberhome.liber_center.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liberhome
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2023-01-10 19:08:14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;
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
        if (!matcher.find()) {
            return -1;
        }
        //密码与校验密码相同
        if (!userPassword.equals(checkPassWord)) {
            return -1;
        }
        // 账户不重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        long count = userMapper.selectCount(queryWrapper);
        queryWrapper.eq("userAccount", userAccount);
        this.count(queryWrapper);
        if (count > 0) {
            return -1;
        }
        //加密
        final String SALT = "leo";
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
}




