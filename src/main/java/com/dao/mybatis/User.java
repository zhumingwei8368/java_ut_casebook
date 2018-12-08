package com.dao.mybatis;

import javax.persistence.GeneratedValue;

import lombok.Data;

/**
 * Created by David on 2017/6/29.
 */

@Data
public class User {
  @GeneratedValue
  private int id;
  private String userName;
}
