package controller;

import dao.Customer;
import dao.ProductOrder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.CustomerService;

import java.util.List;

@Data
@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/all")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }
    @GetMapping("/{customerId}/orders")
    public List<ProductOrder> getAllProductOrderByCustomerId(@PathVariable Integer customerId){
        return customerService.getAllProductOrderByCustomerId(customerId);
    }
    @GetMapping("/{name}")
    public List<Customer> getAllCustomerByLikeName(@PathVariable String name){
        return customerService.getAllCustomerByLikeName(name);
    }

    @GetMapping("/orders/max-price")
    public List<Customer> getCustomerWithMaxOrder(){
        return customerService.getCustomerWithMaxOrder();
    }
}

