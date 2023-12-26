package com.example.demo.reposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

}
