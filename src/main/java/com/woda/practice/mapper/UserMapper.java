package com.woda.practice.mapper;

import com.woda.practice.model.UserModel;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Select("SELECT COUNT(1) FROM `users`")
    long count();

    @Select("INSERT INTO `users` (`username`, `password`, `gender`, `avatar`, `email`)" +
            "VALUES (#{username}, #{password}, #{gender}, #{avatar}, #{email})" )
    UserModel create(UserModel user);

    @Select("SELECT * FROM `users` WHERE `id`=#{id}")
    UserModel get(long id);

    @Select("SELECT `id`, `username`, `gender`, `email` FROM `users` LIMIT #{begin}, #{pageSize}")
    List<UserModel> list(int begin, int pageSize);

    @Delete("DELETE FROM `users` WHERE `id`=#{id}")
    UserModel delete(long id);

    @Select("UPDATE `users` " +
            "SET `username`=#{username}, `password`=#{password}, " +
            "`gender`=#{gender}, `avatar`=#{avatar},`email`=#{email} " +
            "WHERE `id`=#{id}")
    UserModel update(UserModel user);


}
