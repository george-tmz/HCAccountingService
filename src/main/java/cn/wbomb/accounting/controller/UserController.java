package cn.wbomb.accounting.controller;

import cn.wbomb.accounting.converter.c2s.UserInfoC2SConverter;
import cn.wbomb.accounting.exception.InvalidParameterException;
import cn.wbomb.accounting.manager.UserInfoManager;
import cn.wbomb.accounting.model.service.UserInfo;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is userinfo controller.
 *
 * @author George
 */
@RestController
@RequestMapping("v1/users")
@Slf4j
public class UserController {
    private final UserInfoManager userInfoManager;
    private final UserInfoC2SConverter userInfoC2SConverter;

    @Autowired
    public UserController(UserInfoManager userInfoManager,
                          final UserInfoC2SConverter userInfoC2SConverter
    ) {
        this.userInfoManager = userInfoManager;
        this.userInfoC2SConverter = userInfoC2SConverter;
    }

    /**
     * Get user information by user id.
     *
     * @param userId User data id
     * @return User Information
     */
    @GetMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserInfo> getUserInfoByUserId(@PathVariable("id") Long userId) {
        log.debug("Get user info by user id {}", userId);
        if (userId <= 0L) {
            throw new InvalidParameterException("The user id s% is invalid");
        }
        val userInfo = userInfoManager.getUserInfoByUserId(userId);
        return ResponseEntity.ok(userInfoC2SConverter.convert(userInfo));
    }

    /**
     * Register with username and password.
     * @param userInfo userInfo
     * @return  The response for register
     */
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<UserInfo> register(@RequestBody UserInfo userInfo) {
        val userInfoReturn = userInfoManager.register(userInfo.getUsername(), userInfo.getPassword());
        return ResponseEntity.ok(userInfoC2SConverter.convert(userInfoReturn));
    }
}
