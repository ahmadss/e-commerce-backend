package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.entities.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	public Optional<Product> findBySecureId(String secureId);
	public Page<Product> findByNameLikeIgnoreCase(String productTitle, Pageable pageable);
}
