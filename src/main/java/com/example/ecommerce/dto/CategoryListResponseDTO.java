package com.example.ecommerce.dto;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class CategoryListResponseDTO implements Serializable{
	
	private static final long serialVersionUID = -5257382907112297274L;
	
	private String code;
	private String name;
	private String description;

}
