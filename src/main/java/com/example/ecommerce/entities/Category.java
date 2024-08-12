package com.example.ecommerce.entities;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "category", indexes = {
		@Index(name="category_secure_id", columnList = "secure_id")
})
@SQLDelete(sql = "UPDATE category SET deleted= true WHERE id = ?")
@Where(clause = "deleted=false")
@Data
public class Category extends AbstractBaseEntity{

	private static final long serialVersionUID = -8901634365654171485L;
	
	@Id
	@Column(name = "code", nullable = false)
	private String code;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "description", nullable = true)
	private String description;
	
	@ManyToMany(mappedBy = "categories")
	private List<Product> products;

}
