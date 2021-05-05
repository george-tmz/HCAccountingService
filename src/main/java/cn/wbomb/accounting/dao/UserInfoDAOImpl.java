package cn.wbomb.accounting.dao;

import cn.wbomb.accounting.model.persistence.UserInfo;
import org.springframework.stereotype.Repository;

@Repository
public class UserInfoDAOImpl implements UserInfoDAO{
    @Override
    public UserInfo getUserInfoById(Long id) {
        return null;
    }

    @Override
    public void createNewUser(String username, String password) {

    }
}
