package com.example.ecommerce.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignupRequestDTO implements Serializable {


	private static final long serialVersionUID = -6818407145078759395L;

	private String username;
	
	private String email;
	
	private String password;
	
	private List<String> roleNames;

}
