package com.example.ecommerce.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.SignupRequestDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.dto.UserDetailResponseDTO;
import com.example.ecommerce.entities.Role;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.UserCreationException;
import com.example.ecommerce.repository.RoleRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setEmail(user.getEmail());
		userDTO.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
		return userDTO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username).orElseThrow(() -> new BadRequestException("invalid username"));
	}

	@Override
	public UserDetailResponseDTO findUserDetail() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		UserDetailResponseDTO dto = new UserDetailResponseDTO();
		String username = ctx.getAuthentication().getName();
		dto.setUsername(username);
		return dto;
	}

	@Override
	public Optional<UserDTO> findUserByUsername(String username) {
		return userRepository.findByUsername(username).map(this::convertToDTO);
	}

	@Override
	public Optional<UserDTO> findUserByEmail(String email) {
		return userRepository.findByEmail(email).map(this::convertToDTO);
	}

	@Override
	public UserDTO saveUserWithRole(SignupRequestDTO request) {

		// Check if user with the same username or email already exists
		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new UserCreationException("Username already taken.");
		}

		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new UserCreationException("Email already in use.");
		}

		User user = new User();
		user.setUsername(request.getUsername());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setDeleted(false);
		System.out.println("test1");

		List<Role> roles = request.getRoleNames().stream().map(roleName -> {
			Optional<Role> optionalRole = roleRepository.findByName(roleName);
			if (optionalRole.isPresent()) {
				return optionalRole.get();
			} else {
				Role role = new Role();
				role.setName(roleName);
				return roleRepository.save(role);
			}
		}).collect(Collectors.toList());
		System.out.println("test4");
		user.setRoles(roles);
		System.out.println("test2");
		User savedUser = userRepository.save(user);
		System.out.println("test3");
		return convertToDTO(savedUser);
	}

}
