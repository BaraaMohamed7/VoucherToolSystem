package com.biro.vouchertoolsystem.service;

import com.biro.vouchertoolsystem.Dtos.Response.ProductResponseDTO;
import com.biro.vouchertoolsystem.model.Product;
import com.biro.vouchertoolsystem.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductResponseDTO  getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        return modelMapper.map(product, ProductResponseDTO.class);
    }

    public List<ProductResponseDTO> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> modelMapper.map(product,ProductResponseDTO.class)).toList();
    }
}
