package com.book.store.system.catalog.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Page<Product> getProducts(int pageNumber, int pageSize);

    Optional<Product> getProductByCode(String code);
}
