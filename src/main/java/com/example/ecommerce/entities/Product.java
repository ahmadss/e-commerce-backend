package com.example.ecommerce.entities;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product", indexes = {
		@Index(name="product_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE product SET deleted= true WHERE id = ?")
@Where(clause = "deleted=false")
public class Product extends AbstractBaseEntity{
	

	private static final long serialVersionUID = -1990632873122805468L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@Column(name = "image", columnDefinition = "TEXT", nullable = true)
	private String image;
	
	@Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int stock;
	
	
	@ManyToMany
	@JoinTable(name = "product_category", joinColumns = {
			@JoinColumn(name = "product_id", referencedColumnName = "id")
	}, inverseJoinColumns = {
			@JoinColumn(name = "category_code", referencedColumnName = "code")
	})
	private List<Category> categories;

}
