package com.book.store.system.catalog.web.controllers;

import com.book.store.system.catalog.domain.Product;
import com.book.store.system.catalog.domain.ProductNotFoundException;
import com.book.store.system.catalog.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
class ProductController {

    private final ProductService productService;

    @GetMapping
    ResponseEntity<Page<Product>> getProducts(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return ResponseEntity.ok(productService.getProducts(pageNumber, pageSize));
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> getProductByCode(@PathVariable String code) {
        return productService
                .getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ProductNotFoundException.forCode(code));
    }
}
