package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Token;
import com.example.demo.reposity.TokenRepository;

@Service
public class TokenService {

	  private static final int TOKEN_EXPIRATION_MINUTES = 1;

	    @Autowired
	    private TokenRepository tokenRepository;

	    public String generateToken() {
		        String token = generateUniqueToken();
		        LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(TOKEN_EXPIRATION_MINUTES);
		        Token tokenEntity = new Token();
		        tokenEntity.setToken(token);
		        tokenEntity.setExpirationDateTime(expirationDateTime);
		        tokenRepository.save(tokenEntity);
		        return token;
	    }

	    public boolean isTokenValid(String token) {
	        Token tokenEntity = tokenRepository.findByToken(token);
	        return tokenEntity != null && LocalDateTime.now().isBefore(tokenEntity.getExpirationDateTime());
	    }

	    private String generateUniqueToken() { 	
	        // Generate a unique token
	        return UUID.randomUUID().toString();
	    }

}
