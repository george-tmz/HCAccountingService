package cn.wbomb.accounting.manager;

import cn.wbomb.accounting.converter.p2c.UserInfoP2CConverter;
import cn.wbomb.accounting.dao.UserInfoDao;
import cn.wbomb.accounting.exception.InvalidParameterException;
import cn.wbomb.accounting.exception.ResourceNotFoundException;
import cn.wbomb.accounting.model.common.UserInfo;

import lombok.val;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserInfoManagerImpl implements UserInfoManager {

    public static final int HASH_ITERATIONS = 1000;
    private final UserInfoDao userInfoDao;
    private final UserInfoP2CConverter userInfoP2CConverter;

    @Autowired
    public UserInfoManagerImpl(final UserInfoDao userInfoDao,
                               final UserInfoP2CConverter userInfoP2CConverter) {
        this.userInfoDao = userInfoDao;
        this.userInfoP2CConverter = userInfoP2CConverter;
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        val userInfo = Optional.ofNullable(userInfoDao.getUserInfoById(userId))
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("User %s was not found", userId)));
        return userInfoP2CConverter.convert(userInfo);
    }

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        val userInfo = Optional.ofNullable(
                userInfoDao.getUserInfoByUsername(username))
            .orElseThrow(() -> new ResourceNotFoundException(
                String.format("Username %s was not found", username)
            ));
        return userInfoP2CConverter.convert(userInfo);
    }

    @Override
    public void login(String username, String password) {
        // Get subject
        val subject = SecurityUtils.getSubject();
        // Construct token
        val usernamePasswordToken = new UsernamePasswordToken(username, password);
        //login
        subject.login(usernamePasswordToken);
    }

    @Override
    public UserInfo register(String username, String password) {
        val userInfo = userInfoDao.getUserInfoByUsername(username);
        if (userInfo != null) {
            throw new InvalidParameterException(
                String.format("The user %s was register.", username)
            );
        }
        // Set random salt
        String salt = UUID.randomUUID().toString();
        String encryptedPassword = new Sha256Hash(password, salt, HASH_ITERATIONS).toBase64();
        cn.wbomb.accounting.model.persistence.UserInfo userInfoPersistence =
            cn.wbomb.accounting.model.persistence.UserInfo.builder()
                .username(username)
                .password(encryptedPassword)
                .salt(salt)
                .createTime(LocalDate.now())
                .updateTime(LocalDate.now())
                .build();
        userInfoDao.createNewUser(userInfoPersistence);
        return userInfoP2CConverter.convert(userInfoPersistence);
    }
}
