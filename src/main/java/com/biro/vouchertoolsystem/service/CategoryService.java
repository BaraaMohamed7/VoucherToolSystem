package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Request.CategoryRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.CategoryResponseDTO;
import com.biro.vouchertoolsystem.model.Category;
import com.biro.vouchertoolsystem.repository.CategoryRepository;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public CategoryService(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    public List<CategoryResponseDTO> findAll() {
        List<Category> categories =  categoryRepository.findAll();
        return categories.stream().map(category -> modelMapper.map(category, CategoryResponseDTO.class)).toList();
    }
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findById(id).get();
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryResponseDTO.class);
    }

}
