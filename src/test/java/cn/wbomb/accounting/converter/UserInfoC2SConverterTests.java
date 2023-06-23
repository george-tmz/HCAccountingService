package cn.wbomb.accounting.converter;

import cn.wbomb.accounting.converter.c2s.UserInfoC2SConverter;
import cn.wbomb.accounting.model.common.UserInfo;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserInfoC2SConverterTests {
    private final UserInfoC2SConverter userInfoC2SConverter = new UserInfoC2SConverter();
    @Test
    void testDoForward(){
        val userInfoCommon = UserInfo.builder()
                .id(1L)
                .username("george")
                .password("123456")
                .build();
        val userInfoService =  userInfoC2SConverter.convert(userInfoCommon);
        assertThat(userInfoService).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("username", "george");
    }

    @Test
    void testDoBackward(){
        val userInfoService = cn.wbomb.accounting.model.service.UserInfo.builder()
                .id(1L)
                .username("george")
                .build();
        val userInfoCommon =  userInfoC2SConverter.reverse().convert(userInfoService);
        assertThat(userInfoCommon).isNotNull()
                .hasFieldOrPropertyWithValue("id", 1L)
                .hasFieldOrPropertyWithValue("password", null);
    }
}
