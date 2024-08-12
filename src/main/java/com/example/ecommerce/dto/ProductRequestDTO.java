package com.example.ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductRequestDTO implements Serializable {
	
	private static final long serialVersionUID = 2352223360694131157L;
	@NotBlank
	private String title;
	private String description;
	private String image; // base64-encoded image string
	@NotEmpty
	private List<String> categories;
	@NotBlank
	private BigDecimal price;
	@NotBlank
    private int stock;
}
