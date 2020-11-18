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

    /*get-查询数据条数*/
    @GetMapping("/count")
    public long count() {
        return userMapper.count();
    }

    /*post-新增 & ！！返回自增id*/
    // create user from request body and return the user with generated id
    @PostMapping("/create")
    public UserModel create(@RequestBody UserModel user) {
        userMapper.create(user);
        return user;
    }

    /*get-查询（通过id查询用户数据） &！！如果id查不到，返回空*/
    // get user by id from query parameter or null
    @GetMapping("/get")
    public UserModel get(@RequestParam(defaultValue = "0") long id) {
        UserModel userModel = userMapper.get(id);
        if (userModel == null) {
            return new UserModel();
        } else {
            return userMapper.get(id);
        }
    }


    // get user list, pagination
    @GetMapping("/list")
    public List<UserModel> list(@RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(defaultValue = "0") int pageNo) {
        int begin = pageNo * pageSize;
        return userMapper.list(begin, pageSize);
    }
    /*  return LongStream.range((long) pageSize * pageNo, (long) pageSize * (pageNo + 1))
                .mapToObj(UserCtrl::fakeUser)
                .collect(Collectors.toList());*/

    // delete user by ud from query parameter
    // return "deleted" or "user not found" depend on the deletion result
    @DeleteMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") long id) {
        UserModel userModel = userMapper.get(id);
        if (userModel == null){
            return "user not found";
        }
        else {
            userMapper.delete(id);
            return "deleted";
        }
    }

    // update user from request body and return the user updated
    @PutMapping("/update")
    public UserModel update(@RequestParam(defaultValue = "0") long id, @RequestBody UserModel user) {
        userMapper.update(user);
        return user;
    }
/*
    private static UserModel fakeUser(long id) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+{}[]:;'?><,.";
        UserModel user = new UserModel();
        user.setId(id);
        user.setUsername(String.valueOf(id));
        user.setPassword(
                IntStream.range(0, 8)
                        .mapToObj(i -> String.valueOf(chars.charAt(new SecureRandom().nextInt(chars.length()))))
                        .collect(Collectors.joining("")));
        user.setEmail(String.format("%d@woda.com", id));
        user.setGender((short) (id % 2));
        return user;
    }*/

}
