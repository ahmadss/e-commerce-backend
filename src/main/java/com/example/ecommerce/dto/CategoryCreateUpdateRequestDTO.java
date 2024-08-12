package com.example.ecommerce.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CategoryCreateUpdateRequestDTO implements Serializable{
	
	private static final long serialVersionUID = 7385860183990030103L;
	@NotBlank
	private String code;
	@NotBlank
	private String name;
	private String description;

}
