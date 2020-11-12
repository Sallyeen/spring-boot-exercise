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
        userMapper.count();
        return 100;
    }

    @PostMapping("/create")
    public UserModel create(@RequestBody UserModel user) {
        // code here
        // create user from request body and return the user with generated id
        return fakeUser(new SecureRandom().nextLong());
    }

    @GetMapping("/get")
    public UserModel get(@RequestParam(defaultValue = "0") long id) {
        // code here
        // get user by id from query parameter or null
        return fakeUser(id);
    }

    @GetMapping("/list")
    public List<UserModel> list(@RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(defaultValue = "0") int pageNo) {
        // code here
        // get user list, pagination
        return LongStream.range((long) pageSize * pageNo, (long) pageSize * (pageNo + 1))
                .mapToObj(UserCtrl::fakeUser)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") long id) {
        // code here
        // delete user by ud from query parameter
        // return "deleted" or "user not found" depend on the deletion result
        return "user not found";
    }

    @PutMapping("/update")
    public UserModel update(@RequestParam(defaultValue = "0") long id, @RequestBody UserModel user) {
        // code here
        // update user from request body and return the user updated
        return fakeUser(id);
    }

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
    }


}
