package com.woda.practice;

import com.google.gson.Gson;
import com.woda.practice.mapper.UserMapper;
import com.woda.practice.model.UserModel;
import org.junit.Before;
import org.junit.Test;;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestUserCtrl {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private UserMapper userMapper;

    @Before
    public void prepare() {
        System.out.println("Before");
    }

    public List<UserModel> list(int pageSize, int pageNo) {
        int begin = pageNo * pageSize;
        List<UserModel> userModelList = new ArrayList<>();
        for (int i = begin + 1; i <= (begin + pageSize); i++) {
            UserModel user = new UserModel();
            user.setId(i);
            userModelList.add(user);
        }
        return userModelList;
    }

    @Test
    public void testCount() throws Exception {
        long expected = 100L;
        given(userMapper.count()).willReturn(expected);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/user/count"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(String.valueOf(expected)))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).count();
    }

    @Test
    public void testCreate() throws Exception {
        UserModel user = new UserModel();
        user.setId(1);
        user.setUsername("0001");
        user.setPassword("0001");
        user.setGender((short) 0);
        user.setAvatar("0");
        user.setEmail("0001@woda.com");
        given(userMapper.create(any(UserModel.class))).willReturn(user);
        //doNothing().when(userMapper).create(any());//无法运行通过
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/user/create")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new Gson().toJson(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(user)))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).create(any(UserModel.class));
    }

    @Test
    public void testGet() throws Exception {
        UserModel user = new UserModel();
        user.setId(2);
        user.setPassword("0002");
        user.setUsername("0002");
        user.setGender((short) 1);
        user.setEmail("0002@woda.com");
        user.setAvatar("0");
        given(userMapper.get(anyLong())).willReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/user/get")//?id={id}",1,id=1为何能运行通过
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new Gson().toJson(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(user)))
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.username").value("0002"))
                .andExpect(MockMvcResultMatchers.jsonPath("password").value("0002"))
                .andExpect(MockMvcResultMatchers.jsonPath("gender").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("avatar").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("email").value("0002@woda.com"))*/
                //单哥说response是一个流，只能被读一次，为什么上面这几条可以同时被读并校验？
                //$.带不带有什么区别
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).get(anyLong());
    }

    @Test
    public void testList() throws Exception {
        int pageNo = 0;
        int pageSize = 10;
        int begin = pageNo * pageSize;
        List<UserModel> list = list(begin, pageSize);
        given(userMapper.list(anyInt(), anyInt())).willReturn(list);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(String.valueOf(list)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDelete() throws Exception {
        UserModel user = new UserModel();
        user.setPassword("0003");
        user.setUsername("0003");
        user.setGender((short) 1);
        user.setEmail("0003@woda.com");
        user.setAvatar("0");
        user.setId(3);
        long expected1 = 0L;
        long expected2 = 1L;
        when(userMapper.delete(anyLong())).thenAnswer(i -> i.getArgument(0));//?
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete?id={id}", expected1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user not found"))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).delete(0);
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete?id={id}", expected2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("deleted"))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).delete(1);
    }

    @Test
    public void testUpdate() throws Exception {
        String content = "{\"username\":\"0022\",\"password\":\"0022\",\"gender\":\"1\",\"avatar\":\"0\",\"email\":\"0022@woda.com\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("username").value("0022"))
                .andExpect(MockMvcResultMatchers.jsonPath("gender").value("1"))
                .andDo(MockMvcResultHandlers.print());
    }

}
