package cn.wbomb.accounting.dao;

import cn.wbomb.accounting.dao.mapper.UserInfoMapper;
import cn.wbomb.accounting.model.persistence.UserInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * User information dao implement class.
 * @author George
 */
@Repository
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
    public void createNewUser(String username, String password) {

    }
}