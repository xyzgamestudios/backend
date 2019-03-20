package com.xwszt.backend.dao;

import com.xwszt.backend.po.UserEntity;

import java.util.List;

/**
 * 用户DAO
 *
 * @author xwszt
 */
//@Mapper
public interface UserMapper {

    /**
     * 获得所有用户信息
     *
     * @return
     */
//    @Select("SELECT * FROM users")
    List<UserEntity> getAll();
}
