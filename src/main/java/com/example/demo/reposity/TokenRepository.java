package com.example.demo.reposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Token;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    Token findByToken(String token);

}