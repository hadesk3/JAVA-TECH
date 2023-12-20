package com.example.demo.reposity;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.GetInvoice;

public interface GetInvoiceRepo extends JpaRepository<GetInvoice, Long> {
	GetInvoice getGetInvoiceByPath(String path);
}
