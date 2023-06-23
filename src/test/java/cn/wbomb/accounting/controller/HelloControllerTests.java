package cn.wbomb.accounting.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HelloControllerTests {

    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
    }

    @Test
    void testSayHello() throws Exception {
        //Arrange & Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/greeting").param("name", "george"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"content\":\"Hello, george!\"}"));
    }
}
