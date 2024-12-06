package com.yts.ecommerce.catalog.domain;

import com.yts.ecommerce.catalog.Product;
import com.yts.ecommerce.catalog.ProductService;
import com.yts.ecommerce.common.models.PagedResult;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService
{
    private static final int PRODUCT_PAGE_SIZE = 10;
    private final ProductRepository repo;

    ProductServiceImpl(ProductRepository repo) {
        this.repo = repo;
    }

    public PagedResult<Product> getProducts(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        int page = pageNo <= 1 ? 0 : pageNo - 1;
        Pageable pageable = PageRequest.of(page, PRODUCT_PAGE_SIZE, sort);
        Page<Product> productsPage = repo.findAllBy(pageable);
        return new PagedResult<>(productsPage);
    }

    public Optional<Product> getByCode(String code) {
        return repo.findByCode(code);
    }
}
