package cn.wbomb.accounting.converter.c2s;

import cn.wbomb.accounting.model.common.UserInfo;
import com.google.common.base.Converter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserInfoConverter extends Converter<UserInfo,
        cn.wbomb.accounting.model.service.UserInfo> {

    @Override
    protected cn.wbomb.accounting.model.service.UserInfo doForward(UserInfo userInfo) {
        return cn.wbomb.accounting.model.service.UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }

    @Override
    protected UserInfo doBackward(cn.wbomb.accounting.model.service.UserInfo userInfo) {
        return UserInfo.builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .build();
    }
}
