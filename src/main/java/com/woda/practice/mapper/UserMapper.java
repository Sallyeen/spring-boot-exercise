package com.woda.practice.mapper;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("SELECT COUNT(1) FROM `users`")
    long count();

}
