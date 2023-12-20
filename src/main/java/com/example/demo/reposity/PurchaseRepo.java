package com.example.demo.reposity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Customer;
import com.example.demo.model.Order;
import com.example.demo.model.Purchase;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Long> {
    List<Purchase> findByCustomer(Customer customer);
    List<Purchase> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

}