package com.woda.practice;

import com.woda.practice.model.UserModel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUserModel {

    @Test
    public void testSetAndGetId() throws Exception {
        UserModel user = new UserModel();
        user.setId(1L);
        assertEquals(1L, user.getId());
    }
}
