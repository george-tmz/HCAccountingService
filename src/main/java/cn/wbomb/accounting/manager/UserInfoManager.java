package cn.wbomb.accounting.manager;

import cn.wbomb.accounting.model.common.UserInfo;

public interface UserInfoManager {
    /**
     * Get user information by user id.
     *
     * @param userId user id
     */
    UserInfo getUserInfoByUserId(Long userId);

    /**
     * Get user information by username.
     * @param username customer name
     * @return User information common model
     */
    UserInfo getUserInfoByUsername(String username);

    /**
     * Login with username and password.
     *
     * @param username username
     * @param password the related password
     */
    void login(String username, String password);

    /**
     * Register new user with username and password.
     * @param username username
     * @param password the related password
     * @return UserInfo common model
     */
    UserInfo register(String username, String password);
}
