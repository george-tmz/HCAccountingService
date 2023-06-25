package cn.wbomb.accounting.config;

import cn.wbomb.accounting.manager.UserInfoManager;

import lombok.val;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Shiro realm.
 *
 * @author George
 */
@Component
public class UserRealm extends AuthorizingRealm {

    private final UserInfoManager userInfoManager;

    @Autowired
    public UserRealm(UserInfoManager userInfoManager, HashedCredentialsMatcher matcher) {
        super(matcher);
        this.userInfoManager = userInfoManager;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        val userInfo = userInfoManager.getUserInfoByUsername(username);

        return new SimpleAuthenticationInfo(userInfo.getUsername(),
            userInfo.getPassword(),
            ByteSource.Util.bytes(userInfo.getSalt()),
            this.getName());
    }
}
