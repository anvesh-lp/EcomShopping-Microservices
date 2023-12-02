package com.shopping.productservice.services;


import com.shopping.productservice.dto.ProductRequest;
import com.shopping.productservice.dto.ProductResponse;
import com.shopping.productservice.model.Product;
import com.shopping.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    public void saveProduct(ProductRequest productRequest){
        Product product=Product.builder()
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .name(productRequest.getName()).build();
        productRepository.save(product);
        log.info("Product {} saved successfully",product.getId());
    }

    public List<ProductResponse> getProducts(){

        List<ProductResponse> productResponses=productRepository.findAll().stream().map(product -> {
            return ProductResponse.builder().id(product.getId())
                     .description(product.getDescription())
                     .name(product.getName())
                     .price(product.getPrice()).build();
        }).toList();
        log.info("Fetched {} products from DB",productResponses.size());
        return  productResponses;
    }
}
