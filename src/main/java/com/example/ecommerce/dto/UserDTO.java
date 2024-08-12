package com.example.ecommerce.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.ecommerce.entities.Role;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.persistence.Column;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class UserDTO implements Serializable {

	private static final long serialVersionUID = -5523354627349882668L;

	private Long id;

	private String username;

	private String email;

	private List<String> roles;

//	private byte[] img;
//	
//	private MultipartFile _img;

}
