package cn.wbomb.accounting.controller;

import cn.wbomb.accounting.manager.UserInfoManager;
import cn.wbomb.accounting.model.service.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/users")
public class UserController {
    private final UserInfoManager userInfoManager;

    @Autowired
    public UserController(UserInfoManager userInfoManager) {
        this.userInfoManager = userInfoManager;
    }


    @GetMapping("/{id}")
    public UserInfo getUserInfoByUserId(@PathVariable("id") Long userId) {
        cn.wbomb.accounting.model.common.UserInfo userInfo = userInfoManager.getUserInfoByUserId(userId);
        return null;
    }
}
