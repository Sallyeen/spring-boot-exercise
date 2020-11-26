package com.woda.practice;

import com.woda.practice.mapper.UserMapper;
import com.woda.practice.model.UserModel;
import org.apache.catalina.core.ApplicationContext;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

import static org.apache.ibatis.io.Resources.getResourceAsReader;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserMapper {
    private ApplicationContext applicationContext;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DataSource dataSource;

    @Before
    public void initDb() throws Exception {
        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        runner.runScript(getResourceAsReader("user.sql"));
        runner.closeConnection();
    }

    public static UserModel userModel() {
        UserModel user =new UserModel();
        user.setId(11);
        user.setUsername("0011");
        user.setPassword("0011");
        user.setGender((short) 1);
        user.setAvatar("0");
        user.setEmail("0011@woda.com");
        return user;
    }

    @Test
    public void testCount() {
        assertEquals(0, userMapper.count());
    }

    @Test
    public void testCreate() {
        UserModel user1 = userModel();
        userMapper.create(user1);
        assertEquals("0011@woda.com", user1.getEmail());
        assertEquals("0011", user1.getPassword());
    }

    @Test
    public void testGet() {
        UserModel user2 = userModel();
        userMapper.create(user2);
        long user2Id = user2.getId();
        UserModel user22 = userMapper.get(user2Id);
        assertEquals("0011@woda.com", user22.getEmail());
    }

    @Test
    public void testList() {
        int pageNo = 0;
        int pageSize = 10;
        int begin = pageNo * pageSize;
        for (int i = begin; i <= begin +pageSize; i++) {
            UserModel user = userModel();
            userMapper.create(user);
            user.setId(i);
            long a = user.getId();
            System.out.println(a);}
        List<UserModel> userModelList = userMapper.list(begin,pageSize);
        long id = begin + 1;
        for (UserModel user: userModelList) {
            assertEquals(id, user.getId());
            long a = user.getId();
            System.out.println(a);
        id += 1; }
    }

    @Test
    public void testDelete() {
        UserModel user3 = userModel();
        assertEquals(0, userMapper.delete(11));
        userMapper.create(user3);
        long user3Id = user3.getId();
        assertEquals(1, userMapper.delete(user3Id));
        assertEquals(0, userMapper.delete(user3Id));
    }

    @Test
    public void testUpdate() {
        UserModel user4 = userModel();
        userMapper.create(user4);
        long c = user4.getId();
        assertEquals("0011@woda.com", userMapper.get(c).getEmail());
        user4.setEmail("0012@woda.com");
        UserModel user5 = user4;
        userMapper.update(user5);
        assertEquals("0012@woda.com", userMapper.get(c).getEmail());
    }
}

