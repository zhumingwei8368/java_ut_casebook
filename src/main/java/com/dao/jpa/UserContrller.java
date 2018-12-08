package com.dao.jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

/**
 * Created by David on 2018/1/30.
 */

@Service
@RestController
public class UserContrller {
  @Autowired
  private UserRepository userRepository;

  protected final String DB_NOT_EXSIT_RETCODE = "502";
  protected final String DB_ALREADY_EXSIT_RETCODE = "503";
  protected final String DBACCESS_ERROR_RETCODE = "504";
  protected final String SUCCESS_RETCODE = "200";

  @RequestMapping(value = "/rest/jpa/users", method = RequestMethod.GET)
  public List<User> queryUsers() {
    List<User> users =Lists.newArrayList();
    for( User user: userRepository.findAll()){
      users.add(user);
    }
    return users;
  }

  @RequestMapping(value = "/rest/jpa/user", method = RequestMethod.PUT)
  public String addUser(@RequestParam String name) {
    List<User> userList = userRepository.findByUserName(name);
    if (userList.isEmpty()) {
      User user = new User();
      user.setUserName(name);
      return userRepository.save(user) == null ? DBACCESS_ERROR_RETCODE : SUCCESS_RETCODE;
    }else {
      return DB_ALREADY_EXSIT_RETCODE;
    }
  }

  @RequestMapping(value = "/rest/jpa/user", method = RequestMethod.POST)
  public String updateUser(@RequestParam int id, @RequestParam String newName) {
    User user = userRepository.findById(id);
    if (user != null) {
      user.setUserName(newName);
      return userRepository.save(user) == null ? DBACCESS_ERROR_RETCODE : SUCCESS_RETCODE;
    }
    return DB_NOT_EXSIT_RETCODE;
  }

  @RequestMapping(value = "/rest/jpa/user", method = RequestMethod.DELETE)
  public String deleteUser(@RequestParam int id) {
    User user = userRepository.findById(id);
    if (user != null) {
      userRepository.delete(id);
      return SUCCESS_RETCODE;
    }
    return DB_NOT_EXSIT_RETCODE;
  }

  @VisibleForTesting
  protected void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
}
