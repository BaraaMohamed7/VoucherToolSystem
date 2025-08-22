package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Request.CategoryRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.CategoryResponseDTO;
import com.biro.vouchertoolsystem.model.Category;
import com.biro.vouchertoolsystem.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        List<Category> categories =  categoryRepository.findAllByDeletedAtIsNullAndIsActiveIsTrue();
        return categories.stream().map(category -> modelMapper.map(category, CategoryResponseDTO.class)).toList();
    }
    public CategoryResponseDTO findById(Long id) {
        Category category = categoryRepository.findByDeletedAtIsNullAndIsActiveIsTrueAndIdIs(id);
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) {
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryResponseDTO.class);
    }

    public String deleteById(Long id) {
        Category category = categoryRepository.findByDeletedAtIsNullAndIsActiveIsTrueAndIdIs(id);
        category.setDeletedAt(new Date());
        categoryRepository.save(category);
        return "Category has been deleted";
    }

    public CategoryResponseDTO updateCategory(Long id,  CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findByDeletedAtIsNullAndIsActiveIsTrueAndIdIs(id);
        if (categoryRequestDTO.getNameArabic() != null) {
            category.setNameArabic(categoryRequestDTO.getNameArabic());
        }
        if (categoryRequestDTO.getNameEnglish() != null) {
            category.setNameEnglish(categoryRequestDTO.getNameEnglish());
        }
        if (categoryRequestDTO.getImageUrl() != null) {
            category.setImageUrl(categoryRequestDTO.getImageUrl());
        }
        if (categoryRequestDTO.getIsActive() != null) {
            category.setIsActive(categoryRequestDTO.getIsActive());
        }
        return modelMapper.map(categoryRepository.save(category), CategoryResponseDTO.class);
    }
}
