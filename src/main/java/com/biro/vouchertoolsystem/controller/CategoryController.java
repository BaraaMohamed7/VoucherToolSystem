package com.biro.vouchertoolsystem.controller;

import com.biro.vouchertoolsystem.Dtos.Request.CategoryRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.CategoryResponseDTO;
import com.biro.vouchertoolsystem.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryId}")
    public CategoryResponseDTO findCategoryById(@PathVariable("categoryId") Long categoryId) {
        return categoryService.findById(categoryId);
    }

    @GetMapping
    public List<CategoryResponseDTO> findAllCategory() {
        return categoryService.findAll();
    }

    @PostMapping("/new")
    public CategoryResponseDTO create(@RequestBody CategoryRequestDTO  categoryRequestDTO) {
        return categoryService.create(categoryRequestDTO);
    }
}
