package com.example.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CategoryListResponseDTO;
import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.ProductListResponseDTO;
import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductUpdateRequestDTO;
import com.example.ecommerce.dto.ResultPageResponseDTO;
import com.example.ecommerce.entities.Product;

@Service
public interface ProductService {
	public ResultPageResponseDTO<ProductListResponseDTO> findProductList(Integer pages, Integer limit, String sortBy,
			String direction, String productName);
	List<Product> findAll();
    ProductDTO findById(Long id);
    ProductDTO findBySecureId(String id);
    void save(ProductRequestDTO product);
    void updateBySecureId(String id, ProductUpdateRequestDTO product);
    void deleteById(Long id);
    void deleteBySecureId(String id);
}
