package com.woda.practice;

import com.google.gson.Gson;
import com.woda.practice.mapper.UserMapper;
import com.woda.practice.model.UserModel;
import org.junit.Test;
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

    public UserModel userTest() {
        UserModel user = new UserModel();
        user.setId(1);
        user.setUsername("0001");
        user.setPassword("0001");
        user.setGender((short) 0);
        user.setAvatar("0");
        user.setEmail("0001@woda.com");
        return user;
    }

    public List<UserModel> list(int begin, int pageSize) {
        List<UserModel> listUser = new ArrayList<>();
        for (int i = begin + 1; i <= (begin + pageSize); i++) {
            UserModel user = userTest();
            user.setId(i);
            listUser.add(user);
        }
        return listUser;
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
        UserModel user = userTest();
        doNothing().when(userMapper).create(any());
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
        UserModel user = userTest();
        given(userMapper.get(anyLong())).willReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/user/get")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(new Gson().toJson(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(user)))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).get(anyLong());
    }

    @Test
    public void testList() throws Exception {
        int pageNo = 0;
        int pageSize = 10;
        int begin = pageNo * pageSize;
        List<UserModel> listUser= list(begin, pageSize);
        given(userMapper.list(anyInt(), anyInt())).willReturn(listUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(listUser)))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).list(anyInt(), anyInt());
    }

    @Test
    public void testDelete() throws Exception {
        when(userMapper.delete(anyLong())).thenAnswer(i -> i.getArgument(0));

        long id1 = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete?id={id}", id1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("deleted"))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).delete(id1);

        long id2 = 0L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete?id={id}", id2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user not found"))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).delete(id2);
    }

    @Test
    public void testUpdate() throws Exception {
        UserModel user = userTest();
        mockMvc.perform(MockMvcRequestBuilders.put("/user/update")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new Gson().toJson(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new Gson().toJson(user)))
                .andDo(MockMvcResultHandlers.print());
        verify(userMapper, times(1)).update(user);
    }
}
