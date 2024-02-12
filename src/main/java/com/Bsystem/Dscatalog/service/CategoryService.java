package com.Bsystem.Dscatalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bsystem.Dscatalog.dto.CategoryDTO;
import com.Bsystem.Dscatalog.entities.CategoryEntity;
import com.Bsystem.Dscatalog.exceptions.NotFoundException;
import com.Bsystem.Dscatalog.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryDTO> findAll(){		
		List<CategoryEntity> list = categoryRepository.findAll();
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	}

	public CategoryDTO findById(Long id) {
		CategoryEntity entity = categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Categoria não encotrada!"));
		return new CategoryDTO(entity);
	}

}
