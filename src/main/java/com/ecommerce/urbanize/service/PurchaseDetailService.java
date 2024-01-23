package com.ecommerce.urbanize.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.urbanize.entity.PurchaseDetailEntity;
import com.ecommerce.urbanize.exception.ResourceNotFoundException;
import com.ecommerce.urbanize.repository.PurchaseDetailRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PurchaseDetailService {

    @Autowired
    PurchaseDetailRepository oPurchaseDetailRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    //@Autowired
    //ProductService oProductService;

    //@Autowired  
    //PurchaseService oPurchaseService;

    // Get purchase detail by ID
    public PurchaseDetailEntity get(Long id) {
        return oPurchaseDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase detail not found"));
    }

    // Get a page of purchase details
    public Page<PurchaseDetailEntity> getPage(Pageable oPageable) {
        return oPurchaseDetailRepository.findAll(oPageable);
    }

    // Create a new purchase detail
    public Long create(PurchaseDetailEntity oPurchaseDetailEntity) {
        oPurchaseDetailEntity.setId(null);
        return oPurchaseDetailRepository.save(oPurchaseDetailEntity).getId();
    }

    // Update an existing purchase detail
    public PurchaseDetailEntity update(PurchaseDetailEntity oPurchaseDetailEntity) {
        return oPurchaseDetailRepository.save(oPurchaseDetailEntity);
    }

    // Delete a purchase detail by ID
    public Long delete(Long id) {
        oPurchaseDetailRepository.deleteById(id);
        return id;
    }

    // Get a random purchase detail
    public PurchaseDetailEntity getOneRandom() {
        Pageable oPageable = PageRequest.of((int) (Math.random() * oPurchaseDetailRepository.count()), 1);
        return oPurchaseDetailRepository.findAll(oPageable).getContent().get(0);
    }

    // Find purchase details by order ID
    public Page<PurchaseDetailEntity> findByIdOrder(Long order_id, Pageable pageable) {
        return oPurchaseDetailRepository.findByPurchaseId(order_id, pageable);
    }

    // Find purchase details by product ID
    public Page<PurchaseDetailEntity> findByIdProduct(Long product_id, Pageable pageable) {
        return oPurchaseDetailRepository.findByProductId(product_id, pageable);
    }

    // Find purchase details by both order and product ID
    public Page<PurchaseDetailEntity> findByIdOrderAndIdProduct(Long order_id, Long product_id, Pageable pageable) {
        return oPurchaseDetailRepository.findByPurchaseIdAndProductId(order_id, product_id, pageable);
    }

    // Order purchase details by price in descending order
    public Page<PurchaseDetailEntity> findAllByPriceDesc(Pageable pageable) {
        return oPurchaseDetailRepository.findAllByPriceDesc(pageable);
    }

    // Order purchase details by price in ascending order
    public Page<PurchaseDetailEntity> findAllByPriceAsc(Pageable pageable) {
        return oPurchaseDetailRepository.findAllByPriceAsc(pageable);
    }

    /* 
        // Populate the purchase detail table
    public Long populate(Integer amount) {
        for (int i = 0; i < amount; i++) {
            // Generate random purchase detail data
            int amountValue = PurchaseDetailDataGenerationHelper.getRandomAmount();
            int priceValue = PurchaseDetailDataGenerationHelper.getRandomPrice();
            // For simplicity, assuming you have methods to get random ProductEntity and
            // PurchaseEntity
            ProductEntity product = oProductService.getOneRandom();
            PurchaseEntity purchase = oPurchaseService.getOneRandom();

            // Save the purchase detail to the repository
            oPurchaseDetailRepository.save(new PurchaseDetailEntity(amountValue, priceValue, product, purchase));
        }
        return oPurchaseDetailRepository.count();
    }
    */

    // Empty the purchase detail table
    public Long empty() {
        oPurchaseDetailRepository.deleteAll();
        oPurchaseDetailRepository.resetAutoIncrement();
        oPurchaseDetailRepository.flush();
        return oPurchaseDetailRepository.count();
    }

}