 package com.example.demo.reposity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Order;
import com.example.demo.model.Profit;

public interface ProfitRepo extends JpaRepository<Profit, Long> {
    List<Profit> findByPurchaseDateBetween(LocalDate startDate, LocalDate endDate);

}
