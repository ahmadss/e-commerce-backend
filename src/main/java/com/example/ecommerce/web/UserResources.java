package com.example.ecommerce.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.UserDetailResponseDTO;
import com.example.ecommerce.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserResources {
	
	private final UserService appUserService;
	
	@GetMapping("/v1/user")
	public ResponseEntity<UserDetailResponseDTO> findUserDetail(){
		return ResponseEntity.ok(appUserService.findUserDetail());
	}

}
