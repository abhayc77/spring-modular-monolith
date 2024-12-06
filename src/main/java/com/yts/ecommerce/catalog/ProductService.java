package com.yts.ecommerce.catalog;

import com.yts.ecommerce.common.models.PagedResult;
import java.util.Optional;

public interface ProductService {
    PagedResult<Product> getProducts(int pageNo);

    Optional<Product> getByCode(String code);
}
