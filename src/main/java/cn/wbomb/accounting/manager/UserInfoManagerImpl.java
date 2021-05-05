package cn.wbomb.accounting.manager;

import cn.wbomb.accounting.dao.UserInfoDAO;
import cn.wbomb.accounting.model.common.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserInfoManagerImpl implements UserInfoManager {

    private final UserInfoDAO userInfoDAO;

    @Autowired
    public UserInfoManagerImpl(UserInfoDAO userInfoDAO) {
        this.userInfoDAO = userInfoDAO;
    }

    @Override
    public UserInfo getUserInfoByUserId(Long userId) {
        userInfoDAO.getUserInfoById(userId);
        return null;
    }
}
