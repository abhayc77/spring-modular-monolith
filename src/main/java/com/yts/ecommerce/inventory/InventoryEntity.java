package com.yts.ecommerce.inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "inventory", schema = "inventory")
class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_generator")
    @SequenceGenerator(name = "inventory_id_generator", sequenceName = "inventory_id_seq", schema = "catalog")
    private Long id;

    @Column(name = "product_code", nullable = false, unique = true)
    @NotEmpty(message = "Product code is required")
    private String productCode;

    @Column(nullable = false)
    private Long quantity;

    public InventoryEntity() {}

    public InventoryEntity(Long id, String productCode, Long quantity) {
        this.id = id;
        this.productCode = productCode;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
