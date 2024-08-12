package com.example.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CategoryCreateUpdateRequestDTO;
import com.example.ecommerce.dto.CategoryDetailResponseDTO;
import com.example.ecommerce.dto.CategoryListResponseDTO;
import com.example.ecommerce.dto.ResultPageResponseDTO;
import com.example.ecommerce.entities.Category;

@Service
public interface CategoryService {
	
	public void createAndUpdateCategory(CategoryCreateUpdateRequestDTO dto);

	public CategoryDetailResponseDTO findCategoryBySecureId(String code);

	public ResultPageResponseDTO<CategoryListResponseDTO> findCategoryList(Integer pages, Integer limit, String sortBy,
			String direction, String categoryName);

	public List<Category> findCategories(List<String> categoryCodeList);

	public List<CategoryListResponseDTO> constructDTO(List<Category> categories);

	public void deleteCategory(String code);
	
	

}
