package com.example.ecommerce.entities;


import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Index;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@MappedSuperclass
public class AbstractBaseEntity implements Serializable {
	
	private static final long serialVersionUID = 3112257040761253180L;

	@Column(name = "secure_id", nullable = false, unique = true)
	private String secureId=UUID.randomUUID().toString();
	
	@Column(name="deleted", columnDefinition = "boolean default false")
	private boolean deleted;

}
