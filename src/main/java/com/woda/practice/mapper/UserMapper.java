package com.woda.practice.mapper;

import com.woda.practice.model.UserModel;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserMapper {


    @Select("SELECT COUNT(1) FROM `users`")
    long count();

    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @Insert("INSERT INTO `users` (`username`, `password`, `gender`, `avatar`, `email`)" +
            "VALUES (#{username}, #{password}, #{gender}, #{avatar}, #{email})")
    void create(UserModel user);


    @Select("SELECT * FROM `users` WHERE `id`=#{id}")
    UserModel get(long id);

    @Select("SELECT * FROM `users` LIMIT #{begin}, #{pageSize}")
    List<UserModel> list(int begin, int pageSize);

    @Delete("DELETE FROM `users` WHERE `id`=#{id}")
    long delete(long id);

    @Update("UPDATE `users` " +
            "SET `username`=#{username}, `password`=#{password}, " +
            "`gender`=#{gender}, `avatar`=#{avatar}, `email`=#{email} " +
            "WHERE `id`=#{id}")
    long update(UserModel user);

}
