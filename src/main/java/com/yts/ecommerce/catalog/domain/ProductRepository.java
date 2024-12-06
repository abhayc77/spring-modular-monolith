package com.yts.ecommerce.catalog.domain;

import com.yts.ecommerce.catalog.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query(
            """
        select new com.yts.ecommerce.catalog.Product(
                p.code, p.name, p.description, p.imageUrl, p.price)
        from ProductEntity p
        """)
    Page<Product> findAllBy(Pageable pageable);

    @Query(
            """
        select new com.yts.ecommerce.catalog.Product(
                p.code, p.name, p.description, p.imageUrl, p.price)
        from ProductEntity p
        where p.code = :code
        """)
    Optional<Product> findByCode(String code);
}
