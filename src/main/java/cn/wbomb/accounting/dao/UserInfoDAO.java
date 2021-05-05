package cn.wbomb.accounting.dao;


import cn.wbomb.accounting.model.persistence.UserInfo;

public interface UserInfoDAO {
    UserInfo getUserInfoById(Long id);

    void createNewUser(String username, String password);
}
