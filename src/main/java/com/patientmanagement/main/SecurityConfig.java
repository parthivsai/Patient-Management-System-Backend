package com.patientmanagement.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.patientmanagement.main.service.MyUserService;

@SuppressWarnings("deprecation")
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getProvider());
	}

	private AuthenticationProvider getProvider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setPasswordEncoder(bCryptPasswordEncoder);
		// to point to DB, go to service first.
		dao.setUserDetailsService(userService);
		
		return dao;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// List of my api's along with User permissions
		// Assigning permissions to my api's according to the requirements
		http.authorizeRequests()
//				.antMatchers(HttpMethod.GET, "/doctor/login").authenticated()
//				.antMatchers(HttpMethod.POST, "/doctor/add/{id}").hasAnyAuthority("DOCTOR")
//				.antMatchers(HttpMethod.PUT, "/doctor/update/{id}").hasAnyAuthority("ADMIN")
//				.antMatchers(HttpMethod.PUT, "/doctor/delete/{id}").hasAnyAuthority("ADMIN")
//				.antMatchers(HttpMethod.GET, "/patient/login").authenticated()
//				.antMatchers(HttpMethod.POST, "/patient/add/{id}").hasAnyAuthority("DOCTOR","ADMIN")
//				.antMatchers(HttpMethod.PUT, "/patient/update/{id}").hasAnyAuthority("DOCTOR","ADMIN")
//				.antMatchers(HttpMethod.PUT, "/patient/delete/{id}").hasAnyAuthority("DOCTOR","ADMIN")
				.anyRequest().permitAll()
				.and()
				.httpBasic()
				.and()
				.csrf().disable()
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	}
}