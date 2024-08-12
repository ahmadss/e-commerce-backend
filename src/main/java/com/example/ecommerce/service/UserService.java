package com.example.ecommerce.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.SignupRequestDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.dto.UserDetailResponseDTO;

@Service
public interface UserService extends UserDetailsService{
	
	
	public UserDetailResponseDTO findUserDetail();
	
	public UserDTO saveUserWithRole(SignupRequestDTO request);
    public Optional<UserDTO> findUserByUsername(String username);
    public Optional<UserDTO> findUserByEmail(String email);
}
