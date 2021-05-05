package cn.wbomb.accounting.controller;

import cn.wbomb.accounting.converter.c2s.UserInfoC2SConverter;
import cn.wbomb.accounting.exception.ErrorResponse;
import cn.wbomb.accounting.exception.ResourceNotFoundException;
import cn.wbomb.accounting.exception.ServiceException;
import cn.wbomb.accounting.manager.UserInfoManager;
import cn.wbomb.accounting.model.service.UserInfo;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfoByUserId(@PathVariable("id") Long userId) {
        log.debug("Get user info by user id {}", userId);
        try {
            val userInfo = userInfoManager.getUserInfoByUserId(userId);
            return ResponseEntity.ok(userInfoC2SConverter.convert(userInfo));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.builder()
                            .code("USER_NOT_FOUND")
                            .errorType(ServiceException.ErrorType.Client)
                            .message(e.getMessage())
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
    }
}
