package com.example.ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.example.ecommerce.entities.Category;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductListResponseDTO implements Serializable{
	
	private static final long serialVersionUID = -4863741783059919295L;
	
	private Long id;
	private String secureId;
    private String title;
    private String description;
    private String image; // base64-encoded image string
    private List<Category> categories;
    private BigDecimal price;
    private int stock;
}
