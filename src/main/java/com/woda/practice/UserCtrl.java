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

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserCtrl {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/count")
    public long count() {
        return userMapper.count();
    }

    @PostMapping("/create")
    public UserModel create(@RequestBody UserModel user) {
        userMapper.create(user);
        return user;
    }

    @GetMapping("/get")
    public UserModel get(@RequestParam(defaultValue = "0") long id) {
        return userMapper.get(id);
    }

    @GetMapping("/list")
    public List<UserModel> list(@RequestParam(defaultValue = "10") int pageSize,
                                @RequestParam(defaultValue = "0") int pageNo) {
        int begin = pageNo * pageSize;
        return userMapper.list(begin, pageSize);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(defaultValue = "0") long id) {
        long a = userMapper.delete(id);
        if (a > 0) {
            return "deleted";
        } else {
            return "user not found";
        }
    }

    @PutMapping("/update")
    public UserModel update(@RequestParam(defaultValue = "0") long id, @RequestBody UserModel user) {
        userMapper.update(user);
        return user;
    }
}
