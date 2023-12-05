package com.example.demo.reposity;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.User;


public interface UserRepo extends JpaRepository<User, Long >{
    User findByUsername(String username);


}
