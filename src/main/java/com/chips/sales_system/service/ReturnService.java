package com.chips.sales_system.service;

import com.chips.sales_system.dto.ReturnDto;
import com.chips.sales_system.dto.ReturnItemDto;
import com.chips.sales_system.entity.*;
import com.chips.sales_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReturnService {

    @Autowired
    private ReturnsRepository returnsRepository;

    @Autowired
    private ReturnItemRepository returnItemRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ItemListRepository itemListRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    public List<ReturnDto> getAllReturns() {
        return returnsRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public ReturnDto createReturn(ReturnDto returnDto, String username) {
        Invoice invoice = invoiceRepository.findById(returnDto.getInvoiceId())
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        User staff = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Returns returnEntity = new Returns();
        returnEntity.setReturnNo(idGeneratorService.generateReturnNo());
        returnEntity.setInvoice(invoice);
        returnEntity.setShop(invoice.getShop());
        returnEntity.setStaff(staff);
        returnEntity.setReturnDate(returnDto.getReturnDate() != null ? returnDto.getReturnDate() : LocalDateTime.now());
        
        BigDecimal totalReturnAmount = BigDecimal.ZERO;

        Returns savedReturn = returnsRepository.save(returnEntity);

        for (ReturnItemDto itemDto : returnDto.getItems()) {
            InvoiceItem invoiceItem = invoiceItemRepository.findById(itemDto.getInvoiceItemId())
                    .orElseThrow(() -> new RuntimeException("Invoice item not found"));

            if (itemDto.getReturnedQuantity() > invoiceItem.getQuantity()) {
                throw new RuntimeException("Return quantity cannot be greater than billed quantity for item: " + invoiceItem.getItemName());
            }

            ReturnItem returnItem = new ReturnItem();
            returnItem.setReturns(savedReturn);
            returnItem.setInvoiceItem(invoiceItem);
            returnItem.setItemName(invoiceItem.getItemName());
            returnItem.setReturnedQuantity(itemDto.getReturnedQuantity());
            returnItem.setUnit(invoiceItem.getUnit());
            returnItem.setPrice(invoiceItem.getPrice());
            returnItem.setAmount(itemDto.getAmount());
            returnItem.setCostPrice(invoiceItem.getCostPrice());
            returnItem.setReason(itemDto.getReason());

            returnItemRepository.save(returnItem);
            
            totalReturnAmount = totalReturnAmount.add(itemDto.getAmount());

            // Increase stock
            ItemList itemList = invoiceItem.getItemList();
            if (itemList != null) {
                itemList.setQuantity(itemList.getQuantity() + itemDto.getReturnedQuantity());
                itemListRepository.save(itemList);
            }
        }
        
        savedReturn.setTotalReturnAmount(totalReturnAmount);
        returnsRepository.save(savedReturn);

        return mapToDto(savedReturn);
    }

    private ReturnDto mapToDto(Returns returnEntity) {
        ReturnDto dto = new ReturnDto();
        dto.setId(returnEntity.getId());
        dto.setReturnNo(returnEntity.getReturnNo());
        if (returnEntity.getInvoice() != null) {
            dto.setInvoiceId(returnEntity.getInvoice().getId());
            dto.setInvoiceNo(returnEntity.getInvoice().getInvoiceNo());
        }
        if (returnEntity.getShop() != null) {
            dto.setShopId(returnEntity.getShop().getId());
            dto.setShopName(returnEntity.getShop().getName());
        }
        if (returnEntity.getStaff() != null) {
            dto.setStaffId(returnEntity.getStaff().getId());
            dto.setStaffName(returnEntity.getStaff().getName());
        }
        dto.setReturnDate(returnEntity.getReturnDate());
        dto.setTotalReturnAmount(returnEntity.getTotalReturnAmount());

        List<ReturnItemDto> items = returnItemRepository.findByReturns(returnEntity).stream().map(item -> {
            ReturnItemDto itemDto = new ReturnItemDto();
            itemDto.setId(item.getId());
            if (item.getInvoiceItem() != null) {
                itemDto.setInvoiceItemId(item.getInvoiceItem().getId());
            }
            itemDto.setItemName(item.getItemName());
            itemDto.setReturnedQuantity(item.getReturnedQuantity());
            itemDto.setUnit(item.getUnit());
            itemDto.setPrice(item.getPrice());
            itemDto.setAmount(item.getAmount());
            itemDto.setReason(item.getReason());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }
}
