package com.Bsystem.Dscatalog.dto;

import java.io.Serializable;

import com.Bsystem.Dscatalog.entities.CategoryEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String name;

	public CategoryDTO(CategoryEntity entity) {
		this.name = entity.getName();
	}		
}
