package com.Bsystem.Dscatalog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Bsystem.Dscatalog.dto.CategoryDTO;
import com.Bsystem.Dscatalog.entities.CategoryEntity;
import com.Bsystem.Dscatalog.exceptions.DataBaseException;
import com.Bsystem.Dscatalog.exceptions.ResourceEntityNotFoundException;
import com.Bsystem.Dscatalog.repository.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Page<CategoryDTO> findAllPage(PageRequest pageRequest){		
		Page<CategoryEntity> list = categoryRepository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
	}

	public CategoryDTO findById(Long id) {
		CategoryEntity entity = categoryRepository.findById(id)
				.orElseThrow(() -> new ResourceEntityNotFoundException("Categoria não encotrada!"));
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
				.orElseThrow(() -> new ResourceEntityNotFoundException("Categoria não encotrada!"));
		entity.setName(dto.getName());	
		return new CategoryDTO(categoryRepository.save(entity));
	}

	public void deleteCategory(Long id) {
		try {
			categoryRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceEntityNotFoundException("Categoria não encotrada!");
		} catch (DataIntegrityViolationException ex) {
			throw new DataBaseException("Integridade do Banco Violada");
		}
	}
}
