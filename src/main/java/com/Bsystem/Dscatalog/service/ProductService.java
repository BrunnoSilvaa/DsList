package com.Bsystem.Dscatalog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Bsystem.Dscatalog.dto.CategoryDTO;
import com.Bsystem.Dscatalog.dto.ProductDTO;
import com.Bsystem.Dscatalog.entities.CategoryEntity;
import com.Bsystem.Dscatalog.entities.Product;
import com.Bsystem.Dscatalog.exceptions.DataBaseException;
import com.Bsystem.Dscatalog.exceptions.ResourceEntityNotFoundException;
import com.Bsystem.Dscatalog.repository.CategoryRepository;
import com.Bsystem.Dscatalog.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Page<ProductDTO> findAllPage(PageRequest pageRequest){		
		Page<Product> list = productRepository.findAll(pageRequest);
		return list.map(x -> new ProductDTO(x,x.getCategories()));
	}

	public ProductDTO findById(Long id) {
		Product entity = productRepository.findById(id)
				.orElseThrow(() -> new ResourceEntityNotFoundException("Categoria não encotrada!"));
		return new ProductDTO(entity, entity.getCategories());
	}

	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		this.copyDtoToEntity(dto, entity);
	    productRepository.save(entity);
		return new ProductDTO(entity);
	}

	private void copyDtoToEntity(ProductDTO dto, Product entity ) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setDate(dto.getDate());
		entity.getCategories().clear();
		
		for (CategoryDTO cat : dto.getCategories()) {
			CategoryEntity category = categoryRepository.findById(cat.getId()).orElseThrow();
			entity.getCategories().add(category);
		}
    }

	public ProductDTO updateCategory(Long id, ProductDTO dto) {
		Product entity = productRepository.findById(id)
				.orElseThrow(() -> new ResourceEntityNotFoundException("Produto não encotrado!"));
		this.copyDtoToEntity(dto, entity);	
		return new ProductDTO(productRepository.save(entity));
	}

	public void deleteProduct(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceEntityNotFoundException("Categoria não encotrada!");
		} catch (DataIntegrityViolationException ex) {
			throw new DataBaseException("Integridade do Banco Violada");
		}
	}
}
