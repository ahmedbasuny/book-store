package com.book.store.system.catalog.domain;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Page<Product> getProducts(int pageNumber, int pageSize);
}
