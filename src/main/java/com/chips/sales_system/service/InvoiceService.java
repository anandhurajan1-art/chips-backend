package com.chips.sales_system.service;

import com.chips.sales_system.dto.InvoiceDto;
import com.chips.sales_system.dto.InvoiceItemDto;
import com.chips.sales_system.entity.*;
import com.chips.sales_system.entity.enums.OrderStatus;
import com.chips.sales_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private SalesOrderRepository orderRepository;
    
    @Autowired
    private SalesOrderItemRepository orderItemRepository;

    @Autowired
    private ItemListRepository itemListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    @Autowired
    private jakarta.persistence.EntityManager entityManager;

    public List<InvoiceDto> getAllInvoices() {
        return invoiceRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public List<InvoiceDto> getAllInvoices(Long shopId, String date) {
        StringBuilder jpql = new StringBuilder("SELECT i FROM Invoice i WHERE 1=1");
        if (shopId != null) jpql.append(" AND i.shop.id = :shopId");
        if (date != null && !date.isEmpty()) jpql.append(" AND CAST(i.invoiceDate AS date) = CAST(:date AS date)");

        jakarta.persistence.TypedQuery<Invoice> query = entityManager.createQuery(jpql.toString(), Invoice.class);
        if (shopId != null) query.setParameter("shopId", shopId);
        if (date != null && !date.isEmpty()) query.setParameter("date", java.time.LocalDate.parse(date));

        return query.getResultList().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
        return mapToDto(invoice);
    }

    @Transactional
    public InvoiceDto generateInvoice(Long orderId, String username) {
        SalesOrder order = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Invoice can only be generated for pending orders");
        }

        User staff = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Invoice invoice = new Invoice();
        invoice.setInvoiceNo(idGeneratorService.generateInvoiceNo());
        invoice.setOrder(order);
        invoice.setShop(order.getShop());
        invoice.setStaff(staff);
        invoice.setInvoiceDate(java.time.LocalDateTime.now());
        invoice.setTotalAmount(order.getTotalAmount());
        invoice.setGrandTotal(order.getTotalAmount()); // Add taxes here if any
        
        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<SalesOrderItem> orderItems = orderItemRepository.findByOrder(order);
        for (SalesOrderItem orderItem : orderItems) {
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setInvoice(savedInvoice);
            invoiceItem.setItemList(orderItem.getItemList());
            invoiceItem.setItemName(orderItem.getItemName());
            invoiceItem.setQuantity(orderItem.getQuantity());
            invoiceItem.setUnit(orderItem.getUnit());
            invoiceItem.setPrice(orderItem.getPrice());
            invoiceItem.setCostPrice(orderItem.getCostPrice());
            invoiceItem.setAmount(orderItem.getAmount());
            invoiceItemRepository.save(invoiceItem);

        }

        order.setStatus(OrderStatus.BILLED);
        orderRepository.save(order);
        
        invoiceItemRepository.flush();

        return mapToDto(savedInvoice);
    }

    private InvoiceDto mapToDto(Invoice invoice) {
        InvoiceDto dto = new InvoiceDto();
        dto.setId(invoice.getId());
        dto.setInvoiceNo(invoice.getInvoiceNo());
        if (invoice.getOrder() != null) {
            dto.setOrderId(invoice.getOrder().getId());
            dto.setOrderNo(invoice.getOrder().getOrderNo());
        }
        if (invoice.getShop() != null) {
            dto.setShopId(invoice.getShop().getId());
            dto.setShopName(invoice.getShop().getName());
            dto.setShopPlace(invoice.getShop().getPlace());
            dto.setShopPhoneNumber(invoice.getShop().getPhoneNumber());
        }
        if (invoice.getStaff() != null) {
            dto.setStaffId(invoice.getStaff().getId());
            dto.setStaffName(invoice.getStaff().getName());
        }
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setGrandTotal(invoice.getGrandTotal());

        List<InvoiceItemDto> itemDtos = invoiceItemRepository.findByInvoice(invoice).stream().map(item -> {
            InvoiceItemDto itemDto = new InvoiceItemDto();
            itemDto.setId(item.getId());
            itemDto.setInvoiceItemId(item.getId());
            itemDto.setItemName(item.getItemName());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnit(item.getUnit());
            itemDto.setPrice(item.getPrice());
            itemDto.setAmount(item.getAmount());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);
        return dto;
    }
}
