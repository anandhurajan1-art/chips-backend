package com.chips.sales_system.repository;

import com.chips.sales_system.entity.SalesOrderItem;
import com.chips.sales_system.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
    List<SalesOrderItem> findByOrder(SalesOrder order);

    @org.springframework.data.jpa.repository.Query("SELECT i.itemName, SUM(i.quantity) FROM SalesOrderItem i WHERE i.order.shop.id = :shopId GROUP BY i.itemName ORDER BY SUM(i.quantity) DESC")
    List<Object[]> findMostOrderedItemsByShopId(@org.springframework.data.repository.query.Param("shopId") Long shopId);
}
