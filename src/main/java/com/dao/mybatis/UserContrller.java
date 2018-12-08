package com.dao.mybatis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.annotations.VisibleForTesting;

/**
 * Created by David on 2018/1/30.
 */

@Service
@RestController
@Repository
@Component
public class UserContrller {
  protected final String DB_NOT_EXSIT_RETCODE = "502";
  protected final String DB_ALREADY_EXSIT_RETCODE = "503";
  protected final String SUCCESS_RETCODE = "200";

  @Autowired
  private UserMapper userRepository;

  @RequestMapping(value = "/rest/mybatis/users", method = RequestMethod.GET)
  public List<User> queryUsers() {
    return userRepository.findAll();
  }

  @RequestMapping(value = "/rest/mybatis/user", method = RequestMethod.PUT)
  public String addUser(@RequestParam String name) {
    List<User> userList = userRepository.findByUserName(name);
    if (userList.isEmpty()) {
      User user = new User();
      user.setUserName(name);
      userRepository.insertUser(user);
      return SUCCESS_RETCODE;
    }else {
      return DB_ALREADY_EXSIT_RETCODE;
    }
  }

  @RequestMapping(value = "/rest/mybatis/user", method = RequestMethod.POST)
  public String updateUser(@RequestParam long id, @RequestParam String newName) {
    User user = userRepository.findById(id);
    if (user != null) {
      user.setUserName(newName);
      userRepository.updateUser(user);
      return SUCCESS_RETCODE;
    }
    return DB_NOT_EXSIT_RETCODE;
  }

  @RequestMapping(value = "/rest/mybatis/user", method = RequestMethod.DELETE)
  public String deleteUser(@RequestParam int id) {
    User user = userRepository.findById(id);
    if (user != null) {
      userRepository.deleteUser(id);
      return SUCCESS_RETCODE;
    }
    return DB_NOT_EXSIT_RETCODE;
  }

  @VisibleForTesting
  protected void setUserRepository(UserMapper userRepository) {
    this.userRepository = userRepository;
  }
}
