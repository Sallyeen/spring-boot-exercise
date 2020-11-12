package com.woda.practice;

import com.woda.practice.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserCtrl {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void testCount() throws Exception {
        long expected = 100L;
        given(userMapper.count()).willReturn(expected);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/count"))
                .andExpect(MockMvcResultMatchers.content().json(String.valueOf(expected)))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).count();
    }
}
