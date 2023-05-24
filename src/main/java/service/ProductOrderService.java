package service;

import dao.ProductOrder;

import java.time.LocalDate;
import java.util.List;

public interface ProductOrderService {


    List<ProductOrder> getProductOrderByStatus(String statusName);

    Double getSumOrdersByDate(LocalDate date);

    List<ProductOrder> getMostRecentN(Integer n);

    List<ProductOrder> getOrdersLastNDay(Integer n);

    Double getAllPriceByStatus(String status);
}
