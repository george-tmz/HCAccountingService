package cn.wbomb.accounting.dao;

import cn.wbomb.accounting.dao.mapper.UserInfoMapper;
import cn.wbomb.accounting.model.persistence.UserInfo;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserInfoDaoTests {

    @Mock
    private UserInfoMapper userInfoMapper;
    private UserInfoDao userInfoDAO;

    @BeforeEach
    public void setup() {
        userInfoDAO = new UserInfoDaoImpl(userInfoMapper);
    }

    @Test
    public void testGetUserInfoById() {
        //Arrange
        val userId = 100L;
        val userInfo = UserInfo.builder()
                .id(userId)
                .username("admin")
                .password("123456")
                .createTime(LocalDate.now())
                .updateTime(LocalDate.now())
                .build();
        doReturn(userInfo).when(userInfoMapper).getUserInfoByUserId(userId);
        //Act
        val result = userInfoDAO.getUserInfoById(userId);
        //Assert
        assertEquals(userInfo, result);
        verify(userInfoMapper).getUserInfoByUserId(userId);
    }
}
