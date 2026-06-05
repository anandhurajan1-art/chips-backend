package com.chips.sales_system.service;

import com.chips.sales_system.repository.InvoiceRepository;
import com.chips.sales_system.repository.ReturnsRepository;
import com.chips.sales_system.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class IdGeneratorService {

    @Autowired
    private SalesOrderRepository orderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ReturnsRepository returnsRepository;

    private String getDatePrefix() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public String generateOrderNo() {
        String prefix = "ORD-" + getDatePrefix() + "-";
        int seq = 1;
        String newNo = prefix + String.format("%03d", seq);
        while (orderRepository.existsByOrderNo(newNo)) {
            seq++;
            newNo = prefix + String.format("%03d", seq);
        }
        return newNo;
    }

    public String generateInvoiceNo() {
        String prefix = "INV-" + getDatePrefix() + "-";
        int seq = 1;
        String newNo = prefix + String.format("%03d", seq);
        while (invoiceRepository.existsByInvoiceNo(newNo)) {
            seq++;
            newNo = prefix + String.format("%03d", seq);
        }
        return newNo;
    }

    public String generateReturnNo() {
        String prefix = "RET-" + getDatePrefix() + "-";
        int seq = 1;
        String newNo = prefix + String.format("%03d", seq);
        while (returnsRepository.existsByReturnNo(newNo)) {
            seq++;
            newNo = prefix + String.format("%03d", seq);
        }
        return newNo;
    }
}
