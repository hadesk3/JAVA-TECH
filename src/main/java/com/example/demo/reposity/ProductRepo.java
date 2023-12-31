package com.example.demo.reposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Product;



public interface ProductRepo extends JpaRepository<Product, Long> {
	Product findByProductName(String productName); 
}
