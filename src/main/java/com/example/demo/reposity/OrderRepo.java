package com.example.demo.reposity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Product;

@Repository
public interface OrderRepo  extends JpaRepository<Order, Long>{
	  List<Order> findByCustomer(Customer customer);
	    List<Order> findByProductsContaining(Product product);
	    List<Order> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

}
