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

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@RestController
@RequestMapping(value = "/user")
public class UserCtrl {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/count")
    public long count() {
        return userMapper.count();
    }

    // create user from request body and return the user with generated id
    @PostMapping("/create")
    public UserModel create(@RequestBody UserModel user) {
        userMapper.create(user);
        return user;
    }


    // get user by id from query parameter or null
    @GetMapping("/get")
    public UserModel get(@RequestParam(defaultValue = "0") long id) {
        return userMapper.get(id);
    }


    // get user list, pagination
    @GetMapping("/list")
    public List<UserModel> list(@RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(defaultValue = "0") int pageNo) {
        int begin = pageNo * pageSize;
        return userMapper.list(begin, pageSize);
    }


    // delete user by ud from query parameter
    // return "deleted" or "user not found" depend on the deletion result
    @DeleteMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") long id) {
        long a = userMapper.delete(id);
        if (a > 0) {
            return "deleted";
        } else {
            return "user not found";
        }
    }

    // update user from request body and return the user updated
    @PutMapping("/update")
    public UserModel update(@RequestParam(defaultValue = "0") long id, @RequestBody UserModel user) {
        userMapper.update(user);
        return user;
    }
}
