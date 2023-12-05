package com.example.demo.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.reposity.RoleRepo;
import com.example.demo.reposity.UserRepo;

@Service
public class UserService  implements UserDetailsService{

	@Autowired
	RoleRepo roleRepo; 
	@Autowired
	UserRepo userRepo;
    @Autowired
	private PasswordEncoder passwordEncoder;
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        if(user.isEnabled() == true)
        {
        	throw new DisabledException("User account is not active");
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password((user.getPassword()))
                .disabled(user.isEnabled())
                .authorities(getAuthorities(user.getRoles()))
                .build();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }
	   
    public void addUser(User user)
    {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	userRepo.save(user);
    	Role role = new Role(null, user, "ROLE_ADMIN");
    	Role role2 = new Role(null, user, "ROLE_USER");

    	roleRepo.save(role);
    	roleRepo.save(role2);

    	System.out.println("oke");
    }
    
    public void addUserAgent(User user)
    {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	userRepo.save(user);
    	Role role = new Role(null, user, "ROLE_USER");
    	roleRepo.save(role);
    	System.out.println("oke");
    }
    public void activeUser(String name)
    {
    	
    	User userUpdate = userRepo.findByUsername(name);

    	if (userUpdate != null) {
    		if(userUpdate.isEnabled() == false)
    		{
        		userUpdate.setEnabled(true);
    		}
    		else
    		{
    			userUpdate.setEnabled(false);
    		}
    		 userRepo.save(userUpdate);
    	}
    	
    	
    }
    public List<User> findAll()
    {
    	return userRepo.findAll();
    }
    public User findbyUsername(String name)
    {
    	return userRepo.findByUsername(name);
    }

	public Optional<User> findById(Long id) {
	     	return userRepo.findById(id);

	}
}
