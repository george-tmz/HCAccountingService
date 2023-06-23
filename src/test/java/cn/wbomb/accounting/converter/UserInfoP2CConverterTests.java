package cn.wbomb.accounting.converter;

import cn.wbomb.accounting.converter.p2c.UserInfoP2CConverter;
import cn.wbomb.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoP2CConverterTests {
    private final UserInfoP2CConverter userInfoP2CConverter = new UserInfoP2CConverter();
    @Test
    void testDoForward(){
        val userInfoPersistence = UserInfo.builder()
                .id(1L)
                .username("admin")
                .password("123456")
                .createTime(LocalDate.now())
                .updateTime(LocalDate.now())
                .build();
        val userInfoCommon =  userInfoP2CConverter.convert(userInfoPersistence);
        assertThat(userInfoCommon).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("username", "admin")
                .hasFieldOrPropertyWithValue("password", "123456");
    }

    @Test
    void testDoBackward(){
        val userInfoCommon = cn.wbomb.accounting.model.common.UserInfo.builder()
                .id(1L)
                .username("george")
                .password("123456")
                .build();
        val userInfoPersistence =  userInfoP2CConverter.reverse().convert(userInfoCommon);
        assertThat(userInfoPersistence).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("username", "george")
                .hasFieldOrPropertyWithValue("createTime", null)
                .hasFieldOrPropertyWithValue("password", "123456");
    }
}
