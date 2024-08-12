package com.example.ecommerce.security.handler;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.ecommerce.entities.User;
import com.example.ecommerce.security.model.AccessJWTToken;
import com.example.ecommerce.security.utils.JWTTokenFactory;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UsernamePasswordAuthSucessHandler implements AuthenticationSuccessHandler {
	
	private final ObjectMapper objectMapper;
	
	private JWTTokenFactory jwtTokenFactory;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException, StreamWriteException, DatabindException, java.io.IOException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		AccessJWTToken token= jwtTokenFactory.createAccessJWTToken(userDetails.getUsername(), userDetails.getAuthorities());
		User customUserDetails = (User) userDetails;
		List<String> roles = customUserDetails.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("token", token.getRawToken());
		resultMap.put("username", userDetails.getUsername());
		resultMap.put("email", customUserDetails.getEmail());
        resultMap.put("id", customUserDetails.getId().toString());
        resultMap.put("roles", roles);
		response.setStatus(HttpStatus.OK.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		objectMapper.writeValue(response.getWriter(), resultMap);
		
		
	}

}
