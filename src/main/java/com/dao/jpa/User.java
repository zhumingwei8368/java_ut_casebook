package com.dao.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Created by David on 2017/6/29.
 */

@Data @Entity @Table(name = "tbl_jpa_user")
public class User {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private int id;

  @Column(name = "user_name")
  private String userName;
}
