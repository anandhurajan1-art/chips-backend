package com.chips.sales_system.repository;

import com.chips.sales_system.entity.InvoiceItem;
import com.chips.sales_system.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    List<InvoiceItem> findByInvoice(Invoice invoice);
}
