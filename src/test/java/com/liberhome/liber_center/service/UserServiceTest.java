package com.liberhome.liber_center.service;
import java.util.Date;

import com.liberhome.liber_center.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAdduser() {
        User user = new User();
        user.setUsername("leoleo");
        user.setUserAccount("leoleoleo");
        user.setAvatarUrl("https://avatar-static.segmentfault.com/274/037/2740371703-61baf9dec42e4_huge256");
        user.setGender(0);
        user.setUserPassword("12345678");
        user.setPhone("123");
        user.setEmail("456");
        boolean res = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(res);
    }

    @Test
    void userRegister() {
        String userAccount = "liberhome";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "li";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "liber";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "li ber";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "lllllllokaka";
        userPassword = "12345678";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
        userAccount = "liber";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);
    }
}