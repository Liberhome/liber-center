package com.liberhome.liber_center.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author liberhome@163.com
 */

@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 463629699537518639L;
    private String userAccount, userPassword;
}
