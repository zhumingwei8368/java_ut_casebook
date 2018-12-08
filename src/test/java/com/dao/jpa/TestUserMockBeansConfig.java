package com.dao.jpa;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Created by David.Zhu on 2017/6/30.
 */

//when test Spring+JPA open this Configuration
//@Configuration
public class TestUserMockBeansConfig {
  @Bean
  @Primary
  public UserRepository createMockPublisherRepository() {
    return Mockito.mock(UserRepository.class);
  }
}
