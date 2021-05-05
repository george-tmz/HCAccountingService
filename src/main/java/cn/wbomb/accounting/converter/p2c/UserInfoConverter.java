package cn.wbomb.accounting.converter.p2c;

import cn.wbomb.accounting.model.persistence.UserInfo;
import com.google.common.base.Converter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class UserInfoConverter extends Converter<UserInfo, cn.wbomb.accounting.model.common.UserInfo> {

    @Override
    protected cn.wbomb.accounting.model.common.UserInfo doForward(UserInfo userInfo) {
        return cn.wbomb.accounting.model.common.UserInfo.builder().build();
    }

    @Override
    protected UserInfo doBackward(cn.wbomb.accounting.model.common.UserInfo userInfo) {
        return UserInfo.builder().build();
    }
}
