package com.woda.practice;

import com.woda.practice.mapper.UserMapper;
import com.woda.practice.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserCtrl {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/count")
    public long count() throws IOException {
        return userMapper.count();
    }

    @PostMapping("/create")
    public UserModel create(@RequestBody UserModel user) throws IOException {
        // code here
        // create user from request body and return the user with generated id
        return null;
    }

    @GetMapping("/get")
    public UserModel get(@RequestParam String id) throws IOException {
        // code here
        // get user by id from query parameter or null
        return null;
    }

    @GetMapping("/list")
    public List<UserModel> list(@RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(defaultValue = "0") int pageNo) throws IOException {
        // code here
        // get user list, pagination
        return null;
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String id) throws IOException {
        // code here
        // delete user by ud from query parameter
        // return "deleted" or "user not found" depend on the deletion result
        return null;
    }

    @PutMapping("/update")
    public UserModel update(@RequestParam String id, @RequestBody UserModel user) throws IOException {
        // code here
        // update user from request body and return the user updated
        return null;
    }
}
