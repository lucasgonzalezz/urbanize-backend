package com.ecommerce.urbanize.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.urbanize.entity.CartEntity;
import com.ecommerce.urbanize.service.CartService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/cart")
public class CartApi {

    @Autowired
    CartService oCartService;

    // Get cart by ID
    @GetMapping("/{id}")
    public ResponseEntity<CartEntity> get(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oCartService.get(id));
    }

    // Get cart by user ID
    @GetMapping("/byUser/{idUser}")
    public ResponseEntity<List<CartEntity>> getByUser(@PathVariable("idUser") Long idUser) {
        return ResponseEntity.ok(oCartService.getByUser(idUser));
    }

    // Get cart by user ID and product ID
    @GetMapping("/byUserAndProduct/{idUser}/{idProduct}")
    public ResponseEntity<CartEntity> getByUserAndProduct(@PathVariable("idUser") Long idUser,
            @PathVariable("idProduct") Long idProduct) {
        return ResponseEntity.ok(oCartService.getByUserAndProduct(idUser, idProduct));
    }

    // Create a new cart
    @PostMapping("")
    public ResponseEntity<Long> create(@RequestBody CartEntity oCartEntity) {
        return ResponseEntity.ok(oCartService.create(oCartEntity));
    }

    // Update an existing cart
    @PutMapping("")
    public ResponseEntity<CartEntity> update(@RequestBody CartEntity oCartEntity) {
        return ResponseEntity.ok(oCartService.update(oCartEntity));
    }

    // Delete a cart by ID
    @DeleteMapping("{id}")
    public ResponseEntity<Long> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(oCartService.delete(id));
    }

    // Get page of carts
    @GetMapping("")
    public ResponseEntity<Page<CartEntity>> getPage(@PageableDefault(size = 30, sort = {"id"}, direction = Sort.Direction.ASC) Pageable oPageable) {
        return ResponseEntity.ok(oCartService.getPage(oPageable));
    }

    // poulate

    // empty
    @GetMapping("/empty")
    public ResponseEntity<Long> empty() {
        return ResponseEntity.ok(oCartService.empty());
    }

    // delete all carts for a specific user

    // get all carts for a specific user
    @GetMapping("/byUser/{idUser}")
    public ResponseEntity<List<CartEntity>> getAllByUser(@PathVariable("idUser") Long idUser) {
        return ResponseEntity.ok(oCartService.getAllByUser(idUser));
 
    }
}