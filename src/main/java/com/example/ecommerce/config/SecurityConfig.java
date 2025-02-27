package com.example.ecommerce.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.List;

import com.example.ecommerce.security.filter.JwtAuthProcessingFilter;
import com.example.ecommerce.security.filter.UsernamePasswordAuthProcessingFilter;
import com.example.ecommerce.security.handler.UsernamePasswordAuthFailureHandler;
import com.example.ecommerce.security.handler.UsernamePasswordAuthSucessHandler;
import com.example.ecommerce.security.provider.JwtAuthenticationProvider;
import com.example.ecommerce.security.provider.UsernamePasswordAuthProvider;
import com.example.ecommerce.security.utils.JWTTokenFactory;
import com.example.ecommerce.security.utils.SkipPathRequestMatcher;
import com.example.ecommerce.security.utils.TokenExtractor;
import com.fasterxml.jackson.databind.ObjectMapper;


@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

	private final static String AUTH_URL = "/v1/login";
	private final static String SIGNUP_URL = "/v1/signup";
	private final static String UPLOAD_URL = "/uploads/**";
	private final static String ROLES_URL = "/v1/roles";
	private final static String V1_URL = "/v1/**";
	private final static String V2_URL = "/v2/**";
	
	private final static List<String> PERMIT_ENDPOINT_LIST = Arrays.asList(AUTH_URL
			, ROLES_URL
			, SIGNUP_URL
			, UPLOAD_URL
			, "/swagger-ui.html"
            , "/swagger-ui/index.html"
            , "/swagger-ui/index.css"
            , "/favicon.ico"
            , "/swagger-ui/swagger-ui.css"
            , "/swagger-ui/swagger-ui.css.map"
            , "/swagger-ui/swagger-ui-standalone-preset.js"
            , "/swagger-ui/swagger-ui-standalone-preset.js.map"
            , "/swagger-ui/swagger-ui-bundle.js"
            , "/swagger-ui/swagger-ui-bundle.js.map"
            , "/swagger-ui/favicon-32x32.png"
            , "/swagger-ui/favicon-16x16.png"
            , "/swagger-ui/swagger-initializer.js"
            , "/v3/api-docs/swagger-config"
            , "/v3/api-docs");
	private final static List<String> AUTHENTICATED_ENDPOINT_LIST = Arrays.asList(V1_URL, V2_URL);

			

	@Autowired
	private UsernamePasswordAuthProvider usernamePasswordAuthProvider;
	
	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Bean
	public AuthenticationSuccessHandler usernamePasswordAuthSuccessHandler(ObjectMapper objectMapper, JWTTokenFactory jwtTokenFactory) {
		return new UsernamePasswordAuthSucessHandler(objectMapper, jwtTokenFactory);
	}

	@Bean
	public AuthenticationFailureHandler usernamePasswordAuthFailureHandler(ObjectMapper objectMapper) {
		return new UsernamePasswordAuthFailureHandler(objectMapper);
	}

	@Autowired
	void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(usernamePasswordAuthProvider)
		.authenticationProvider(jwtAuthenticationProvider);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter(ObjectMapper objectMapper,
			AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler,
			AuthenticationManager manager) {
		UsernamePasswordAuthProcessingFilter filter = new UsernamePasswordAuthProcessingFilter(AUTH_URL, objectMapper,
				successHandler, failureHandler);
		filter.setAuthenticationManager(manager);
		return filter;
	}
	
	
	@Bean
	public JwtAuthProcessingFilter jwtAuthProcessingFilter(TokenExtractor tokenExtractor, AuthenticationFailureHandler failureHandler,  AuthenticationManager authManager ) {
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(PERMIT_ENDPOINT_LIST, AUTHENTICATED_ENDPOINT_LIST);
		JwtAuthProcessingFilter filter = new JwtAuthProcessingFilter(matcher, tokenExtractor, failureHandler);
		filter.setAuthenticationManager(authManager);
		return filter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http,
			UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter,
			JwtAuthProcessingFilter jwtAuthProcessingFilter) throws Exception {
		http.authorizeHttpRequests(auth -> auth.requestMatchers(PERMIT_ENDPOINT_LIST.toArray(new String[0])).permitAll().requestMatchers(V1_URL, V2_URL).authenticated()).csrf(csrf -> csrf.disable())
				.sessionManagement(
						(sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(usernamePasswordAuthProcessingFilter, UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(jwtAuthProcessingFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();

	}

}
