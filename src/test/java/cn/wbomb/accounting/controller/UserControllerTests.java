package cn.wbomb.accounting.controller;

import cn.wbomb.accounting.converter.c2s.UserInfoC2SConverter;
import cn.wbomb.accounting.exception.GlobalExceptionHandler;
import cn.wbomb.accounting.exception.ResourceNotFoundException;
import cn.wbomb.accounting.manager.UserInfoManager;
import cn.wbomb.accounting.model.common.UserInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    private MockMvc mockMvc;
    @Mock
    private UserInfoManager userInfoManager;
    private final UserInfoC2SConverter userInfoC2SConverter = new UserInfoC2SConverter();

    @BeforeEach
    void setup() {
        UserController userController = new UserController(userInfoManager, new UserInfoC2SConverter());
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test
    void testGetUserInfoByUserId() throws Exception {
        val userId = 10L;
        val userName = "george";
        val password = "123456";

        val userInfoCommon = UserInfo.builder()
            .id(userId)
            .username(userName)
            .password(password)
            .build();
        doReturn(userInfoCommon).when(userInfoManager).getUserInfoByUserId(anyLong());
        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/" + userId)
                .contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string(new ObjectMapper().writeValueAsString(
                userInfoC2SConverter.convert(userInfoCommon)
            )));
        verify(userInfoManager).getUserInfoByUserId(anyLong());
    }

    @Test
    void testGetUserInfoByUserIdWithInvalidUserId() throws Exception {
        val userId = -100L;
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/" + userId)
                .contentType("application/json"))
            .andExpect(status().is4xxClientError())
            .andExpect(content().contentType("application/json"))
            .andExpect(content().string(
                "{\"code\":\"INVALID_PARAMETER_EXCEPTION\",\"errorType\":\"Client\",\"message\":\"The user id s% is invalid\",\"statusCode\":400}"));
    }
}
