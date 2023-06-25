package cn.wbomb.accounting.dao;


import cn.wbomb.accounting.model.persistence.UserInfo;

/**
 * this is the user DAO.
 * @author George
 */
public interface UserInfoDao {
    UserInfo getUserInfoById(Long id);

    UserInfo getUserInfoByUsername(String username);

    void createNewUser(UserInfo userInfo);
}
