package service;

import dao.Product;

import java.util.Date;
import java.util.List;

public interface ProductService {
    List<Product> getProductsByCategory(String categoryName);
    List<Product> getAllProductsOrderByPriceAcs();
    List<Product> getProductsByOrderDate( Date orderDate);
    Double getSumOfALlOrderPrice();
    Double getAvgPriceByOrderDate(Date orderDate);
    Product getMaxPriceByCategory(String category);

    List<Product> getProductsAll(Integer limit, Integer offset, String columnName);

    List<Product> getByStatusCustomerId(String status, Integer customerId);
}


