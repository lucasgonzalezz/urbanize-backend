package com.ecommerce.urbanize.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.urbanize.entity.ProductEntity;

// Interface definition extending JpaRepository for ProductEntity with Long as ID type
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // Method to find products by category ID
    Page<ProductEntity> findByCategoryId(Long id, Pageable pageable);

    // Method to find products by size
    @Query(value = "SELECT * FROM product WHERE size = ?1", nativeQuery = true)
    Page<ProductEntity> findBySize(String size, Pageable pageable);

    // Method to find products by stock descending
    @Query(value = "SELECT * FROM product ORDER BY stock DESC", nativeQuery = true)
    Page<ProductEntity> findByStockDesc(Pageable pageable);

    // Method to find products by price descending
    @Query(value = "SELECT * FROM product ORDER BY price DESC", nativeQuery = true)
    Page<ProductEntity> findByPriceDesc(Pageable pageable);

    // Method to find products by price and category descending
    @Query(value = "SELECT * FROM product WHERE category_id = ?1 ORDER BY price DESC", nativeQuery = true)
    Page<ProductEntity> findByPriceDescAndIdCategory(Long category_id, Pageable pageable);

    // Method to find products by search text ignoring case
    Page<ProductEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = "SELECT * FROM product p WHERE p.id IN (SELECT pd.product_id FROM purchase_detail pd WHERE pd.purchase_id IN (SELECT p.id FROM purchase p WHERE p.user_id = ?1))", nativeQuery = true)
    Page<ProductEntity> findProductsPurchaseByUser(Long user_id, Pageable pageable);

    // Method to find products most sold
    @Query(value = "SELECT p.* FROM product p INNER JOIN purchase_detail pd ON p.id = pd.product_id INNER JOIN purchase pu ON pd.purchase_id = pu.id GROUP BY p.id ORDER BY SUM(pd.amount) DESC", nativeQuery = true)
    Page<ProductEntity> findProductsMostSold(Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE length(?1) >= 3 AND (name LIKE %?1% )", nativeQuery = true)
    Page<ProductEntity> findByName(String name, Pageable oPageable);

    // Method to reset the auto-increment counter for the "user" table
    @Modifying
    @Query(value = "ALTER TABLE user AUTO_INCREMENT = 1", nativeQuery = true)
    void resetAutoIncrement();
}