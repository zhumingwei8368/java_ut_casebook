package com.dao.mybatis;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by David on 2018/1/31.
 */
@RunWith(SpringRunner.class)
@MybatisTest
@TestPropertySource(properties = {
    "mybatis.mapper-locations=classpath:mybatis/*Mapper.xml",
    "mybatis.type-aliases-package=mybatis",
    "spring.datasource.schema=classpath:mybatis/user.sql"})
@Import(UserContrller.class)
public class UserContrllerHSQLTest {
  @Autowired
  private UserContrller contrller;

  @Test public void test_queryUsers() throws Exception {
    Assert.assertEquals(0, contrller.queryUsers().size());

    contrller.addUser("user1");
    contrller.addUser("user1");
    contrller.addUser("user2");

    Assert.assertEquals(2, contrller.queryUsers().size());
  }

  @Test public void test_addUser_success() throws Exception {
    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.addUser("user1"));
  }

  @Test public void test_addUser_already_exist() throws Exception {
    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.addUser("user1"));
    Assert.assertEquals(contrller.DB_ALREADY_EXSIT_RETCODE, contrller.addUser("user1"));
  }

  @Test public void test_updateUser_success() throws Exception {
    contrller.addUser("user1");
    List<User> users = contrller.queryUsers();

    String retcode = contrller.updateUser(users.get(0).getId(),"user1_mod");
    Assert.assertEquals(contrller.SUCCESS_RETCODE, retcode);
    Assert.assertEquals("user1_mod", contrller.queryUsers().get(0).getUserName());
  }

  @Test public void test_updateUser_not_exist() throws Exception {
    contrller.addUser("user1");
    List<User> users = contrller.queryUsers();

    Assert.assertEquals(contrller.DB_NOT_EXSIT_RETCODE,
        contrller.updateUser(users.get(0).getId()+1,"user1_mod"));
  }

  @Test public void test_deleteUser_success() throws Exception {
    contrller.addUser("user1");
    List<User> users = contrller.queryUsers();

    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.deleteUser(users.get(0).getId()));
    Assert.assertEquals(0, contrller.queryUsers().size());
  }

  @Test public void test_deleteUser_not_exist() throws Exception {
    contrller.addUser("user1");
    List<User> users = contrller.queryUsers();

    Assert.assertEquals(contrller.DB_NOT_EXSIT_RETCODE, contrller.deleteUser(users.get(0).getId()+1));
    Assert.assertEquals(1, contrller.queryUsers().size());
  }
}