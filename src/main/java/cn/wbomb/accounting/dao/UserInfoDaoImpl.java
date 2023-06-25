package cn.wbomb.accounting.dao;

import cn.wbomb.accounting.dao.mapper.UserInfoMapper;
import cn.wbomb.accounting.model.persistence.UserInfo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * User information dao implement class.
 *
 * @author George
 */
@Repository
@Slf4j
public class UserInfoDaoImpl implements UserInfoDao {
    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoDaoImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public UserInfo getUserInfoById(Long id) {
        return userInfoMapper.getUserInfoByUserId(id);
    }

    @Override
    public UserInfo getUserInfoByUsername(String username) {
        return userInfoMapper.getUserInfoByUsername(username);
    }

    @Override
    public void createNewUser(UserInfo userInfo) {
        int row = userInfoMapper.createNewUser(userInfo);
        log.info("Result: {}, user information: {}", row, userInfo);
    }
}
