package com.example.ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.ProductListResponseDTO;
import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductUpdateRequestDTO;
import com.example.ecommerce.dto.ResultPageResponseDTO;
import com.example.ecommerce.entities.Category;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.utils.PaginationUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService{

	private final ProductRepository productRepository;
	
	private final CategoryService categoryService;
	
	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductDTO findById(Long id) {
		return productRepository.findById(id)
	            .map(this::convertToDTO)
	            .orElse(null);
	}
	
	@Override
	public ResultPageResponseDTO<ProductListResponseDTO> findProductList(Integer pages, Integer limit, String sortBy,
			String direction, String productTitle) {
		productTitle  = StringUtils.isEmpty(productTitle) ?"%":productTitle+"%";
		Sort sort = Sort.by(PaginationUtil.getSortBy(direction), sortBy);
//		
		Pageable pageable = PageRequest.of(pages, limit, sort);
		Page<Product> pageResult = productRepository.findByNameLikeIgnoreCase(productTitle, pageable);
		
		List<ProductListResponseDTO> dtos =  pageResult.stream().map((c)->{
//			List<Category> categories = categoryService.findCategories(dto.getCategories());
			ProductListResponseDTO dto = new ProductListResponseDTO();
			dto.setTitle(c.getName());
			dto.setDescription(c.getDescription());
			dto.setImage(c.getImage());
			dto.setId(c.getId());
			dto.setPrice(c.getPrice());
			dto.setStock(c.getStock());
			dto.setSecureId(c.getSecureId());
//			dto.setCategories(c.getCategories());
			return dto;
		}).collect(Collectors.toList());
		
		return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
	}

	@Override
	public ProductDTO findBySecureId(String id) {
		return productRepository.findBySecureId(id)
	            .map(this::convertToDTO)
	            .orElse(null);
	}

	@Override
	public void save(ProductRequestDTO dto) {
		List<Category> categories = categoryService.findCategories(dto.getCategories());
		Product product = new Product();
		product.setCategories(categories);
		product.setImage(dto.getImage());
		product.setDescription(dto.getDescription());
		product.setName(dto.getTitle());
		product.setPrice(dto.getPrice());
		product.setStock(dto.getStock());
		productRepository.save(product);
	}

	@Override
	public void updateBySecureId(String id, ProductUpdateRequestDTO dto) {
		List<Category> categories = categoryService.findCategories(dto.getCategories());
		Product product = productRepository.findBySecureId(id)
				.orElseThrow(()->new BadRequestException("product_id.invalid"));
		product.setName(dto.getTitle());
		if(dto.getDescription()!=null) {
			product.setDescription(dto.getDescription());
		} 
		if(!categories.isEmpty()) product.setCategories(categories);
		if(dto.getImage()!=null) product.setImage(dto.getImage());
		if(dto.getStock() > 0) product.setStock(dto.getStock());
		if(dto.getPrice()!=null) product.setPrice(dto.getPrice());
		productRepository.save(product);
	}
	


	@Override
	public void deleteById(Long id) {
		Product product =  productRepository.findById(id)
				.orElseThrow(()->new BadRequestException("invalid product id"));
				product.setDeleted(Boolean.TRUE);
				productRepository.save(product);
		
	}

	@Override
	public void deleteBySecureId(String id) {
		Product product =  productRepository.findBySecureId(id)
				.orElseThrow(()->new BadRequestException("invalid product id"));
				product.setDeleted(Boolean.TRUE);
				productRepository.save(product);
		
	}
	
	private ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setTitle(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
        dto.setCategories(product.getCategories().stream().map(c->c.getCode()).collect(Collectors.toList()));
        return dto;
    }


}
