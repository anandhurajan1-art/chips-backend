package com.chips.sales_system.service;

import com.chips.sales_system.dto.OrderDto;
import com.chips.sales_system.dto.OrderItemDto;
import com.chips.sales_system.entity.SalesOrder;
import com.chips.sales_system.entity.SalesOrderItem;
import com.chips.sales_system.entity.Shop;
import com.chips.sales_system.entity.User;
import com.chips.sales_system.entity.ItemList;
import com.chips.sales_system.entity.enums.OrderStatus;
import com.chips.sales_system.repository.SalesOrderItemRepository;
import com.chips.sales_system.repository.SalesOrderRepository;
import com.chips.sales_system.repository.ShopRepository;
import com.chips.sales_system.repository.UserRepository;
import com.chips.sales_system.repository.ItemListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private SalesOrderRepository orderRepository;

    @Autowired
    private SalesOrderItemRepository orderItemRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemListRepository itemListRepository;

    @Autowired
    private IdGeneratorService idGeneratorService;

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public OrderDto getOrderById(Long id) {
        SalesOrder order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        return mapToDto(order);
    }

    @Transactional
    public OrderDto createOrder(OrderDto orderDto, String username) {
        SalesOrder order = new SalesOrder();
        order.setOrderNo(idGeneratorService.generateOrderNo());
        
        Shop shop = shopRepository.findById(orderDto.getShopId()).orElseThrow(() -> new RuntimeException("Shop not found"));
        order.setShop(shop);

        User staff = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        order.setStaff(staff);

        order.setOrderDate(orderDto.getOrderDate());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setStatus(OrderStatus.PENDING);

        SalesOrder savedOrder = orderRepository.save(order);

        for (OrderItemDto itemDto : orderDto.getItems()) {
            SalesOrderItem item = new SalesOrderItem();
            item.setOrder(savedOrder);
            ItemList itemList = itemListRepository.findById(itemDto.getItemListId()).orElseThrow(() -> new RuntimeException("Item not found"));
            item.setItemList(itemList);
            item.setItemName(itemDto.getItemName());
            item.setQuantity(itemDto.getQuantity());
            item.setUnit(itemDto.getUnit());
            item.setPrice(itemDto.getPrice());
            item.setAmount(itemDto.getAmount());
            orderItemRepository.save(item);
        }

        return mapToDto(savedOrder);
    }

    @Transactional
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        SalesOrder order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be edited.");
        }

        Shop shop = shopRepository.findById(orderDto.getShopId()).orElseThrow(() -> new RuntimeException("Shop not found"));
        order.setShop(shop);
        order.setOrderDate(orderDto.getOrderDate());
        order.setTotalAmount(orderDto.getTotalAmount());

        SalesOrder savedOrder = orderRepository.save(order);

        // Delete old items and insert new ones
        List<SalesOrderItem> oldItems = orderItemRepository.findByOrder(savedOrder);
        orderItemRepository.deleteAll(oldItems);

        for (OrderItemDto itemDto : orderDto.getItems()) {
            SalesOrderItem item = new SalesOrderItem();
            item.setOrder(savedOrder);
            ItemList itemList = itemListRepository.findById(itemDto.getItemListId()).orElseThrow(() -> new RuntimeException("Item not found"));
            item.setItemList(itemList);
            item.setItemName(itemDto.getItemName());
            item.setQuantity(itemDto.getQuantity());
            item.setUnit(itemDto.getUnit());
            item.setPrice(itemDto.getPrice());
            item.setAmount(itemDto.getAmount());
            orderItemRepository.save(item);
        }

        return mapToDto(savedOrder);
    }

    @Transactional
    public void cancelOrder(Long id) {
        SalesOrder order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be cancelled.");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private OrderDto mapToDto(SalesOrder order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderNo(order.getOrderNo());
        if (order.getShop() != null) {
            dto.setShopId(order.getShop().getId());
            dto.setShopName(order.getShop().getName());
        }
        if (order.getStaff() != null) {
            dto.setStaffId(order.getStaff().getId());
            dto.setStaffName(order.getStaff().getName());
        }
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus().name());

        List<OrderItemDto> itemDtos = orderItemRepository.findByOrder(order).stream().map(item -> {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setId(item.getId());
            if (item.getItemList() != null) {
                itemDto.setItemListId(item.getItemList().getId());
            }
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
