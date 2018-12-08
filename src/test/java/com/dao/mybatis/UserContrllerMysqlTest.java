package com.dao.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by David on 2018/1/31.
 * Attention: this test need real Mysql exist!
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MybatisSpringApplication.class})
@MapperScan("com.dao.mybatis")
@Transactional  //
public class UserContrllerMysqlTest {
  @Autowired
  private UserContrller contrller;

  @Test public void test_queryUsers() throws Exception {
    //TODO
  }

}