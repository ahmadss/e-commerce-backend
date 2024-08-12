package com.example.ecommerce.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.CategoryCreateUpdateRequestDTO;
import com.example.ecommerce.dto.CategoryDetailResponseDTO;
import com.example.ecommerce.dto.CategoryListResponseDTO;
import com.example.ecommerce.dto.ResultPageResponseDTO;
import com.example.ecommerce.entities.Category;
import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.service.CategoryService;
import com.example.ecommerce.utils.PaginationUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	@Override
	public void createAndUpdateCategory(CategoryCreateUpdateRequestDTO dto) {
		Category category =  categoryRepository.findByCode(dto.getCode().toLowerCase()).orElse(new Category());
		if(category.getCode() == null) {
			category.setCode(dto.getCode());
		}
		category.setName(dto.getName());
		category.setDescription(dto.getDescription());
		
		categoryRepository.save(category);
		
	}

	@Override
	public ResultPageResponseDTO<CategoryListResponseDTO> findCategoryList(Integer pages, Integer limit, String sortBy,
			String direction, String categoryName) {
		categoryName  = StringUtils.isEmpty(categoryName) ?"%":categoryName+"%";
		Sort sort = Sort.by(PaginationUtil.getSortBy(direction), sortBy);
		
		Pageable pageable = PageRequest.of(pages, limit, sort);
		Page<Category> pageResult = categoryRepository.findByNameLikeIgnoreCase(categoryName, pageable);
		List<CategoryListResponseDTO> dtos =  pageResult.stream().map((c)->{
			CategoryListResponseDTO dto = new CategoryListResponseDTO();
			dto.setCode(c.getCode());
			dto.setName(c.getName());
			dto.setDescription(c.getDescription());
			return dto;
		}).collect(Collectors.toList());
		return PaginationUtil.createResultPageDTO(dtos, pageResult.getTotalElements(), pageResult.getTotalPages());
	}

	@Override
	public List<Category> findCategories(List<String> categoryCodeList) {
		List<Category> categories = categoryRepository.findByCodeIn(categoryCodeList);
		if(categories.isEmpty()) throw new BadRequestException("author can empty");
		return categories;
	}

	@Override
	public List<CategoryListResponseDTO> constructDTO(List<Category> categories) {
		return categories.stream().map((c)->{
			CategoryListResponseDTO dto = new CategoryListResponseDTO();
			dto.setCode(c.getCode());
			dto.setName(c.getName());
			dto.setDescription(c.getDescription());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public void deleteCategory(String code) {
		Category category =  categoryRepository.findByCode(code)
				.orElseThrow(()->new BadRequestException("invalid category id"));
				category.setDeleted(Boolean.TRUE);
				categoryRepository.save(category);
		
	}

	@Override
	public CategoryDetailResponseDTO findCategoryBySecureId(String code) {
		Category category =  categoryRepository.findBySecureId(code)
				.orElseThrow(()->new BadRequestException("invalid category id"));
				
				CategoryDetailResponseDTO dto = new CategoryDetailResponseDTO();
				dto.setName(category.getName());
				dto.setDescription(category.getDescription());
				dto.setCode(category.getCode());
				return dto;
	}

}
