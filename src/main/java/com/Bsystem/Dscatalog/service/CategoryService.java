package com.Bsystem.Dscatalog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bsystem.Dscatalog.dto.CategoryDTO;
import com.Bsystem.Dscatalog.entities.CategoryEntity;
import com.Bsystem.Dscatalog.exceptions.EntityNotFoundException;
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
				.orElseThrow(() -> new EntityNotFoundException("Categoria não encotrada!"));
		return new CategoryDTO(entity);
	}

	public CategoryDTO insert(CategoryDTO dto) {
		CategoryEntity entity = categoryRepository.save(this.prepareEntity(dto));
		return new CategoryDTO(entity);
	}

	private CategoryEntity prepareEntity(CategoryDTO dto) {
        return CategoryEntity
                .builder()
                .name(dto.getName())
                .build();
    }

	public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
		CategoryEntity entity = categoryRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Categoria não encotrada!"));
		entity.setName(dto.getName());	
		return new CategoryDTO(categoryRepository.save(entity));
	}
}
