package com.chips.sales_system.repository;

import com.chips.sales_system.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    boolean existsByInvoiceNo(String invoiceNo);
}
