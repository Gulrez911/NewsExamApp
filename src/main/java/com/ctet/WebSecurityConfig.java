package com.ctet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ctet.common.PropertyConfig;
import com.ctet.data.User;
import com.ctet.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	PropertyConfig config;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	UserService userService; 
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//		auth.userDetailsService(userDetailsService);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return super.userDetailsService();
	}

//	@Bean
//	public UserDetailsService userDetailsService() {
//		return new UserDetailsServiceImpl();
//	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
				.antMatchers("/**","/loginAdmin", "/home9", "/login", "/resources/**", "/WEB-INF/views/*", "/oauth/**",
						"./resources/**", "/css/**", "/images/**", "/scripts/**", "/scripts_login/**", "/eAssess/**",
						"/contact/**", "/my_resources/**", "/demo/**", "/user/**"	).permitAll()
//		http.authorizeRequests().antMatchers("/login" , "/oauth/**").permitAll()

				.anyRequest().authenticated()
				.and().
				formLogin().loginPage("/login")
				.defaultSuccessUrl("/add")
				.failureUrl("/login?error")
//		.permitAll()
				.and()
				.oauth2Login()
//				.loginPage("/login")
				.loginPage("/showMyLoginPage")
				.userInfoEndpoint()
				.userService(oauthUserService)
//			.oidcUserService(oidcUserService)

//				.userService(customUserDetailsService)
				.and().successHandler(new AuthenticationSuccessHandler() {

					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						System.out.println("AuthenticationSuccessHandler invoked");
						System.out.println("Authentication name: " + authentication.getName());
						CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
						System.out.println(">>>>>>>  " + oauthUser.getEmail());
						System.out.println(">>>>>>>  " + oauthUser.getName());
						System.out.println(">>>>>>>  " + oauthUser.getImageUrl());

						User usr = new User();
						usr.setCompanyId(config.getCompanyName());
						usr.setCompanyName(config.getCompanyName());
						String userName = "";

						String[] nameArray = oauthUser.getName().split(" ");
						String firstName = "";
						String lastName = "";
						int flag = 0;
						for (String w : nameArray) {
							if (flag == 0) {
								firstName = w;
							} else {
								lastName += w + " ";
							}
							flag = 1;
						}
						System.out.println(userName.trim() + "\n");

						usr.setFirstName(firstName);
						usr.setLastName(lastName);
						usr.setEmail(oauthUser.getEmail());
						usr.setPassword("12345");
						usr.setImageUrl(oauthUser.getImageUrl());
						userService.saveOrUpdate(usr);
						
						request.getSession().setAttribute("usr2", usr);
//						GoogleOAuth2UserInfo googleOAuth2UserInfo  =(GoogleOAuth2UserInfo)authentication.getPrincipal();
//						System.out.println(">>>>>>>  " + googleOAuth2UserInfo.getEmail());
//						System.out.println(">>>>>>>  " + googleOAuth2UserInfo.getName());
//						System.out.println(">>>>>>>  " + googleOAuth2UserInfo.getImageUrl());
//				response.sendRedirect("/ProductManager/login_success");
						response.sendRedirect("/ProductManager/home9");
//						response.sendRedirect("/home9");
					}
				}).and().logout().logoutSuccessUrl("/home9").permitAll();

	}

	@Autowired
	private CustomOAuth2UserService oauthUserService;

}
