package com.example.ecommerce.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.dto.ProductDTO;
import com.example.ecommerce.dto.ProductListResponseDTO;
import com.example.ecommerce.dto.ProductRequestDTO;
import com.example.ecommerce.dto.ProductUpdateRequestDTO;
import com.example.ecommerce.dto.ResultPageResponseDTO;
import com.example.ecommerce.service.ProductService;
import com.example.ecommerce.utils.FileUtil;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/product")
@AllArgsConstructor
public class ProductController {
	
	@Autowired
	private final ProductService productService;
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> findProductDetail(@PathVariable("id") String id){
		ProductDTO  result = productService.findBySecureId(id);
		return ResponseEntity.ok(result);
	}
	
	
	@GetMapping
	public ResponseEntity<ResultPageResponseDTO<ProductListResponseDTO>> findProductList(
			@RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages, 
			@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit, 
			@RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy, 
			@RequestParam(name = "direction", required = true, defaultValue = "asc") String direction, 
			@RequestParam(name = "productTitle", required = false) String productName
			){
		return ResponseEntity.ok().body(productService.findProductList(pages, limit, sortBy, direction, productName));
	}
	
	@PostMapping
	public ResponseEntity<Void> createNewProduct(@RequestBody ProductRequestDTO productCreateDTO){
        try {
        	if(productCreateDTO.getImage()!=null) {
	        	String nameFile = FileUtil.generateUniqueFileName("jpg", productCreateDTO.getTitle());
	            FileUtil.saveBase64ToFile(productCreateDTO.getImage(), nameFile);
	            productCreateDTO.setImage("uploads/"+nameFile);
        	}
            productService.save(productCreateDTO);
    		return ResponseEntity.created(URI.create("/product")).build();
        } catch (IOException e) {
            e.printStackTrace();
         
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateProduct(@PathVariable("id") String id, @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO){
		
		try {
        	if(productUpdateRequestDTO.getImage()!=null) {
	        	String nameFile = FileUtil.generateUniqueFileName("jpg", productUpdateRequestDTO.getTitle());
	            FileUtil.saveBase64ToFile(productUpdateRequestDTO.getImage(), nameFile);
	            productUpdateRequestDTO.setImage("uploads/"+nameFile);
        	} else {
        		productUpdateRequestDTO.setImage(null);
        	}
            productService.updateBySecureId(id, productUpdateRequestDTO);
    		return ResponseEntity.ok().build();	
        } catch (IOException e) {
            e.printStackTrace();
         
        }
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id){
		productService.deleteBySecureId(id);
		return ResponseEntity.ok().build();
	}
}
