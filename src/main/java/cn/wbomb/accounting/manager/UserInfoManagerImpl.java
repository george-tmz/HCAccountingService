package cn.wbomb.accounting.manager;

import cn.wbomb.accounting.converter.p2c.UserInfoP2CConverter;
import cn.wbomb.accounting.dao.UserInfoDao;
import cn.wbomb.accounting.exception.ResourceNotFoundException;
import cn.wbomb.accounting.model.common.UserInfo;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoManagerImpl implements UserInfoManager {

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
}
