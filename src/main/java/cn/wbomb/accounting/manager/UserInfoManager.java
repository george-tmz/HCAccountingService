package cn.wbomb.accounting.manager;

import cn.wbomb.accounting.model.common.UserInfo;

public interface UserInfoManager {
    /**
     * Get user information by user id.
     */
    UserInfo getUserInfoByUserId(Long userId);
}
