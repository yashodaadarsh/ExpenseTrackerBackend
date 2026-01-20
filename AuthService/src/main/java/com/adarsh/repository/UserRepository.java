package com.adarsh.repository;

import com.adarsh.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserInfo,String> {
    public UserInfo findByUsername(String username);
}
