package com.ziz.springsecuredappwith0auth2.config;


import com.ziz.springsecuredappwith0auth2.service.CustomOAuth2UserService;
import com.ziz.springsecuredappwith0auth2.service.MyUserDetailsSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	private MyUserDetailsSevice userDetailsSevice;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/", "/register", "/login", "/css/**").permitAll()
						.anyRequest().authenticated()
				)
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login")
						.defaultSuccessUrl("/", true)
						.permitAll()
				)
				.oauth2Login(oauth -> oauth
						.loginPage("/login") // Optional: reuse same login page
						.defaultSuccessUrl("/", true)
						.userInfoEndpoint(userInfo -> userInfo
								.userService(customOAuth2UserService)
				))
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
				);

		return http.build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsSevice);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}
}
