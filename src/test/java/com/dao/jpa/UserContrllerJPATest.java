package com.dao.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by David on 2018/1/30.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.schema=classpath:jpa/schema.sql",
    "spring.datasource.data=classpath:jpa/testdata.sql"})
public class UserContrllerJPATest {
  @Autowired
  private UserContrller contrller;

  @Test
  public void test_queryUsers() throws Exception {
    List<User> users = contrller.queryUsers();
    System.out.println(users);
    assertEquals(3, users.size());
    assertEquals("user2", users.get(1).getUserName());
  }
}