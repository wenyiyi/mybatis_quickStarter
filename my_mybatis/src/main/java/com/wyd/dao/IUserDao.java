package com.wyd.dao;

import com.wyd.pojo.User;

import java.util.List;

public interface IUserDao {

    List<User> findAll();

    /**
     * 多条件组合查询：演示if
     */
    List<User> findByCondition(User user);

    List<User> findByIds(List<Integer> ids);

}
