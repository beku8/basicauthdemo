package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@EnableWebSecurity
@RestController
public class BasicauthdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicauthdemoApplication.class, args);
	}
	
	@Configuration
  public static class HttpBasicSecurityConfig extends WebSecurityConfigurerAdapter {
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
        .requestMatchers()
          .requestMatchers(EndpointRequest.toAnyEndpoint())
      .and()
        .authorizeRequests()
        .requestMatchers(EndpointRequest.to("health", "info")).permitAll()
        .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
        .antMatchers("/error").permitAll() // To give proper error on failed authentication, https://github.com/spring-projects/spring-security/issues/4467
        .anyRequest().hasRole("ACTUATOR")
      .and()
        .httpBasic()
      .and()
        .requestCache().disable()
        .csrf().disable();
      
      
    }  

  }
	
	@GetMapping("/foo")
	public String foo() {
	  return "foo";
	}
}
