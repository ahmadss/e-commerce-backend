package com.example.ecommerce.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.CategoryCreateUpdateRequestDTO;
import com.example.ecommerce.dto.CategoryDetailResponseDTO;
import com.example.ecommerce.dto.CategoryListResponseDTO;
import com.example.ecommerce.dto.ResultPageResponseDTO;
import com.example.ecommerce.service.CategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/category")
@AllArgsConstructor
public class CategoryController {
	
	@Autowired
	private final CategoryService categoryService;
	
	@GetMapping("/{code}")
	public ResponseEntity<CategoryDetailResponseDTO> findBookDetail(@PathVariable("code") String id){
		CategoryDetailResponseDTO  result = categoryService.findCategoryBySecureId(id);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping
	public ResponseEntity<Void> createAndUpdateCategory(@RequestBody CategoryCreateUpdateRequestDTO dto){
		categoryService.createAndUpdateCategory(dto);
		
		return ResponseEntity.created(URI.create("/v1/category")).build();
	}
	
	@GetMapping
	public ResponseEntity<ResultPageResponseDTO<CategoryListResponseDTO>> findCategoryList(
			@RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages, 
			@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit, 
			@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy, 
			@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction, 
			@RequestParam(name = "categoryName", required = false) String categoryName
			){
		return ResponseEntity.ok().body(categoryService.findCategoryList(pages, limit, sortBy, direction, categoryName));
	}
	
	@DeleteMapping("/{code}")
	public ResponseEntity<Void> deleteCategory(@PathVariable String code){
		System.out.println(code);
		categoryService.deleteCategory(code);
		return ResponseEntity.ok().build();
	}
}
