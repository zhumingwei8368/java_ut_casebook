package com.dao.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by David on 2017/6/29.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findById(Integer id);
  List<User> findByUserName(String userName);
}
