package com.reon.productservice.controller;

import com.reon.productservice.dto.ProductRequest;
import com.reon.productservice.response.ProductResponse;
import com.reon.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/new")
    public ResponseEntity<ProductResponse> newProduct(@Valid @RequestBody ProductRequest product) {
        ProductResponse newProduct = productService.createNewProduct(product);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(newProduct);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize){
        Page<ProductResponse> productList = productService.fetchAllProducts(pageNo, pageSize);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productList);
    }
}
