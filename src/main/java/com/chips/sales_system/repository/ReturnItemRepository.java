package com.chips.sales_system.repository;

import com.chips.sales_system.entity.ReturnItem;
import com.chips.sales_system.entity.Returns;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReturnItemRepository extends JpaRepository<ReturnItem, Long> {
    List<ReturnItem> findByReturns(Returns returns);

    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(i.itemName, i.invoiceItem.itemList.item.itemName), SUM(i.returnedQuantity) FROM ReturnItem i WHERE i.returns.shop.id = :shopId GROUP BY COALESCE(i.itemName, i.invoiceItem.itemList.item.itemName) ORDER BY SUM(i.returnedQuantity) DESC")
    List<Object[]> findMostReturnedItemsByShopId(@org.springframework.data.repository.query.Param("shopId") Long shopId);
}
