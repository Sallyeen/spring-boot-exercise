package com.woda.practice;

import com.woda.practice.mapper.UserMapper;
import com.woda.practice.model.UserModel;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.catalina.core.ApplicationContext;
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
import static org.junit.Assert.assertNull;

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
    public void testGet() {
        UserModel user2 = userModel();
        long a = user2.getId();
        UserModel user22 = userMapper.get(a);
        assertEquals("0011@woda.com", user22.getEmail());
    }


@Test
    public void testCreate() {
        UserModel user1 = userModel();
        userMapper.create(user1);
        long d = user1.getId();
        UserModel user11 = userMapper.get(d);
        assertEquals("0011@woda.com", user11.getEmail());
        assertEquals("0011", user11.getPassword());
    }


    @Test
    public void testDelete() {
        UserModel user3 = userModel();
        long b = user3.getId();
        assertEquals(1, userMapper.delete(b));
        assertEquals(0, userMapper.delete(22));
    }

    @Test
    public void testUpdate() {
        UserModel user4 = userModel();
        long c = user4.getId();
        assertEquals("0011@woda.com", userMapper.get(c).getEmail());
        user4.setEmail("0012@woda.com");
        assertEquals("0012@woda.com", userMapper.get(c).getEmail());
        //assertEquals(1, userMapper.update(user4));
    }



}

