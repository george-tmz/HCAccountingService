package cn.wbomb.accounting.manager;

import cn.wbomb.accounting.converter.p2c.UserInfoP2CConverter;
import cn.wbomb.accounting.dao.UserInfoDAO;
import cn.wbomb.accounting.exception.ResourceNotFoundException;
import cn.wbomb.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class UserInfoManagerTests {

    private UserInfoManager userInfoManager;

    @Mock
    private UserInfoDAO userInfoDAO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        userInfoManager = new UserInfoManagerImpl(userInfoDAO, new UserInfoP2CConverter());
    }

    @Test
    void testGetUserInfoByUserId() {
        //Arrange
        Long userId = 1L;
        val username = "admin";
        val password = "123456";
        val userInfo = UserInfo.builder()
                .id(userId)
                .username(username)
                .password(password).build();
        doReturn(userInfo).when(userInfoDAO).getUserInfoById(userId);
        //Act
        val result = userInfoManager.getUserInfoByUserId(userId);
        //Assert
        //AssertJ
        org.assertj.core.api.Assertions.assertThat(result).isNotNull()
                .hasFieldOrPropertyWithValue("id", userId)
                .hasFieldOrPropertyWithValue("username", username)
                .hasFieldOrPropertyWithValue("password", password);
        verify(userInfoDAO).getUserInfoById(userId);
    }

    @Test
    void testGetUserInfoByUserIdWithInvalidUserId() {
        //Arrange
        Long userId = -1L;
        doReturn(null).when(userInfoDAO).getUserInfoById(userId);
        //Act & Assert
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> userInfoManager.getUserInfoByUserId(userId));
        verify(userInfoDAO).getUserInfoById(userId);
    }
}
