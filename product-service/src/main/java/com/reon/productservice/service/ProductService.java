package com.reon.productservice.service;

import com.reon.productservice.dto.ProductRequest;
import com.reon.productservice.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    ProductResponse createNewProduct(ProductRequest productRequest);
    Page<ProductResponse> fetchAllProducts(int pageNo, int pageSize);
}
