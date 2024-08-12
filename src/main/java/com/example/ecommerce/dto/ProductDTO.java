package com.example.ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 2521589686728431694L;
	private Long id;
    private String title;
    private String description;
    private String image; // base64-encoded image string
    private List<String> categories;
    private BigDecimal price;
    private int stock;
}
