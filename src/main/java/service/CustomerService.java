package service;

import dao.Customer;
import dao.ProductOrder;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    List<ProductOrder> getAllProductOrderByCustomerId(Integer customerId);

    List<Customer> getAllCustomerByLikeName(String name);
    List<Customer> getCustomerWithMaxOrder();
}