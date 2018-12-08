package com.dao.jpa;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by David on 2018/1/30.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto = create"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserContrllerHSQLTest{
  @Autowired
  private UserContrller contrller;
  @Autowired
  private UserRepository repository;

  @Test public void test01_queryUsers() throws Exception {
    Assert.assertEquals(0, contrller.queryUsers().size());
    contrller.addUser("user1");
    contrller.addUser("user2");
    Assert.assertEquals(2, contrller.queryUsers().size());
  }

  @Test public void test02_addUser_success() throws Exception {
    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.addUser("user1"));
  }

  @Test public void test03_addUser_already_exist() throws Exception {
    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.addUser("user1"));
    Assert.assertEquals(contrller.DB_ALREADY_EXSIT_RETCODE, contrller.addUser("user1"));
  }

  @Test public void test04_updateUser_success() throws Exception {
    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.addUser("user1"));
    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.updateUser(contrller.queryUsers().get(0).getId(),"user1_mod"));
  }

  @Transactional
  @Test public void test05_updateUser_not_exist() throws Exception {
    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.addUser("user1"));
    Assert.assertEquals(contrller.DB_NOT_EXSIT_RETCODE, contrller.updateUser(contrller.queryUsers().get(0).getId()+1,"user1_mod"));
  }

  @Test public void test06_deleteUser_success() throws Exception {
    contrller.addUser("user1");

    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.deleteUser(contrller.queryUsers().get(0).getId()));
    Assert.assertEquals(0, contrller.queryUsers().size());
  }

  @Test public void test07_deleteUser_not_exist() throws Exception {
    contrller.addUser("user1");
    Assert.assertEquals(contrller.DB_NOT_EXSIT_RETCODE, contrller.deleteUser(contrller.queryUsers().get(0).getId()+1));
    Assert.assertEquals(1, contrller.queryUsers().size());
  }

}