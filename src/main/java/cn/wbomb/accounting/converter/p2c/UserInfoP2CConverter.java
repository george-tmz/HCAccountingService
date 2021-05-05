package cn.wbomb.accounting.converter.p2c;

import cn.wbomb.accounting.model.persistence.UserInfo;
import com.google.common.base.Converter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserInfoP2CConverter extends Converter<UserInfo, cn.wbomb.accounting.model.common.UserInfo> {

    @Override
    protected cn.wbomb.accounting.model.common.UserInfo doForward(UserInfo userInfo) {
        return cn.wbomb.accounting.model.common.UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }

    @Override
    protected UserInfo doBackward(cn.wbomb.accounting.model.common.UserInfo userInfo) {
        return UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
}
