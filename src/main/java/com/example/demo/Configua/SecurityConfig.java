package com.example.demo.Configua;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.UserService;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig   {

	@Bean 
	UserDetailsService userDetailsService()
	{
		 return new UserService();
			
	}	
	


	  @Bean
	   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		    http
		        .authorizeHttpRequests((authz) -> authz
		        .antMatchers("/link/**").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/uploads/**").permitAll()
                .antMatchers("/CSS/**").permitAll()
                .antMatchers("/submit-cart").permitAll()
	            .antMatchers("/userLoginFirstTime","/change-pass","/changed-pass","/user-change-pass","/link","page-buy-product").permitAll()
	            .antMatchers("/register").hasAnyRole("ADMIN")
	            .antMatchers("/sendMail").hasAnyRole("ADMIN")
	            .antMatchers("/").hasAnyRole("ADMIN")
	            .antMatchers("/checkout").hasAnyRole("USER")
	            .antMatchers("/admin-productManager").hasRole("ADMIN")
	            .antMatchers("/invoice").hasAnyRole("USER")
	            .antMatchers("/admin-page").hasRole("ADMIN")
	          
	            .antMatchers("/analysis").hasRole("ADMIN")
	            
	            .antMatchers("/detail-user/**").hasRole("ADMIN")
	            .antMatchers("/admin/product/edit/**").hasRole("ADMIN")
	            .antMatchers("/admin/product/edit").hasRole("ADMIN")
	            .antMatchers("/handleUser").hasAnyRole("ADMIN")
	            .antMatchers("/page-handle-user").hasAnyRole("ADMIN")
	            .anyRequest().authenticated())
		        .formLogin()
		        .defaultSuccessUrl("/index")
		            .and()
		        .logout()
		            .logoutUrl("/logout")
		            .logoutSuccessUrl("/login")
		            .invalidateHttpSession(true)
	                .clearAuthentication(true)
		            .and()
		            .csrf().disable()
	            .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/login?expired");
		       
		        
		    return http.build();
		}

  @Bean
   PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }

@Bean
AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
}

protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(inMemoryUserDetailsManager()).passwordEncoder(passwordEncoder());
}

@Bean
public InMemoryUserDetailsManager inMemoryUserDetailsManager() {

    UserDetails user = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();

    return new InMemoryUserDetailsManager(user);
}





  
  
}
