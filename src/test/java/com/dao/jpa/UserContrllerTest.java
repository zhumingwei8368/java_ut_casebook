package com.dao.jpa;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by David on 2018/1/30.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {JpaSpringApplication.class, TestUserMockBeansConfig.class})
@TestPropertySource(properties = {
    "spring.datasource.url = jdbc:h2:mem:test",
    "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect"})
public class UserContrllerTest {
  @Autowired
  private UserContrller contrller;
  @Autowired
  private UserRepository repository;

  private List<User> cacheUsers = Lists.newArrayList();

  @Before public void setup() throws Exception {
    //repository = PowerMockito.mock(UserRepository.class);
    //contrller.setUserRepository(repository);
  }

  @After public void tearDown() {
    Mockito.reset(repository);
  }

  @Test public void test_queryUsers() throws Exception {
    prepareUsers(3);
    Mockito.when(repository.findAll()).thenReturn(cacheUsers);

    List<User> users =  contrller.queryUsers();

    Assert.assertEquals(3, users.size());
    Assert.assertEquals("user2", users.get(1).getUserName());
    Assert.assertEquals(2, users.get(1).getId());
  }

  @Test public void test_addUser_success() throws Exception {
    Mockito.when(repository.findByUserName("user1")).thenReturn(Lists.<User>newArrayList());
    Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(new User());

    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.addUser("user1"));
  }

  @Test public void test_addUser_dberror() throws Exception {
    Mockito.when(repository.findByUserName("user1")).thenReturn(Lists.<User>newArrayList());
    Mockito.when(repository.save(Mockito.any(User.class))).thenReturn(null);

    Assert.assertEquals(contrller.DBACCESS_ERROR_RETCODE, contrller.addUser("user1"));
  }

  @Test public void test_addUser_already_exist() throws Exception {
    prepareUsers(1);
    Mockito.when(repository.findByUserName("user1")).thenReturn(cacheUsers);

    Assert.assertEquals(contrller.DB_ALREADY_EXSIT_RETCODE, contrller.addUser("user1"));
  }

  @Test public void test_updateUser_success() throws Exception {
    User user = new User();
    user.setUserName("user1");
    Mockito.when(repository.findById(1)).thenReturn(user);
    Mockito.when(repository.save(user)).thenReturn(user);

    String retcode = contrller.updateUser(1,"user1_mod");
    Assert.assertEquals("user1_mod", user.getUserName());
    Assert.assertEquals(contrller.SUCCESS_RETCODE, retcode);
  }

  @Test public void test_updateUser_dberror() throws Exception {
    User user = new User();
    user.setUserName("user1");
    Mockito.when(repository.findById(1)).thenReturn(user);
    Mockito.when(repository.save(user)).thenReturn(null);

    String retcode = contrller.updateUser(1,"user1_mod");
    Assert.assertEquals("user1_mod", user.getUserName());
    Assert.assertEquals(contrller.DBACCESS_ERROR_RETCODE, retcode);
  }

  @Test public void test_updateUser_not_exist() throws Exception {
    Mockito.when(repository.findById(1)).thenReturn(null);

    Assert.assertEquals(contrller.DB_NOT_EXSIT_RETCODE, contrller.updateUser(1,"user1_mod"));
  }

  @Test public void test_deleteUser_success() throws Exception {
    User user = new User();
    user.setId(1);
    user.setUserName("user1");
    Mockito.when(repository.findById(1)).thenReturn(user);
    PowerMockito.doNothing().when(repository).delete(1);

    Assert.assertEquals(contrller.SUCCESS_RETCODE, contrller.deleteUser(1));
  }

  @Test public void test_deleteUser_not_exist() throws Exception {
    Mockito.when(repository.findById(1)).thenReturn(null);

    Assert.assertEquals(contrller.DB_NOT_EXSIT_RETCODE, contrller.deleteUser(1));
  }

  private void prepareUsers(int num) {
    cacheUsers.clear();
    for (int i = 0; i < num; i++) {
      User user = new User();
      user.setId(i+1);
      user.setUserName("user"+(i+1));
      cacheUsers.add(user);
    }
  }
}