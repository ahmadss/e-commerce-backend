package com.example.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.SignupRequestDTO;
import com.example.ecommerce.dto.UserDTO;
import com.example.ecommerce.exception.UserCreationException;
import com.example.ecommerce.service.UserService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
public class SignupController {
	
	UserService userService;
	
	@PostMapping("/v1/signup")
    public ResponseEntity<UserDTO> createUserWithRole(@RequestBody SignupRequestDTO request) {
        try {
        	UserDTO userDTO = userService.saveUserWithRole(request);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
		} catch (UserCreationException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userService.findUserByUsername(username)
            .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userService.findUserByEmail(email)
            .map(userDTO -> new ResponseEntity<>(userDTO, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
