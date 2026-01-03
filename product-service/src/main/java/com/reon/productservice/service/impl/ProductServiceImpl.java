package com.reon.productservice.service.impl;

import com.reon.productservice.dto.ProductRequest;
import com.reon.productservice.model.Product;
import com.reon.productservice.repository.ProductRepository;
import com.reon.productservice.response.ProductResponse;
import com.reon.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createNewProduct(ProductRequest productRequest) {
        if (productRepository.existsByName(productRequest.getName())) {
            throw new IllegalArgumentException("Product with name: " + " already exist");
        }
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        Product save = productRepository.save(product);
        return ProductResponse.builder()
                .id(save.getId())
                .name(save.getName())
                .description(save.getDescription())
                .price(save.getPrice())
                .createdAt(save.getCreatedAt())
                .build();
    }

    @Override
    public Page<ProductResponse> fetchAllProducts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .build());
    }
}
