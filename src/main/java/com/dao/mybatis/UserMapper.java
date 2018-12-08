package com.dao.mybatis;

import java.util.List;

/**
 * Created by David on 2017/6/29.
 */

public interface UserMapper {
  List<User> findAll();
  User findById(long id);
  List<User> findByUserName(String userName);
  void insertUser(User user);
  void updateUser(User user);
  void deleteUser(long id);
}
