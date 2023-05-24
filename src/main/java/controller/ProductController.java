package controller;

import dao.Product;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ProductService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productServiceImpl;

    @GetMapping("/category/{categoryName}")
    public List<Product> getProductsByCategory(@PathVariable String categoryName){
        return productServiceImpl.getProductsByCategory(categoryName);
    }

    @GetMapping("/all")
    public List<Product> getAllProductsOrderByPriceAsc(){
        return productServiceImpl.getAllProductsOrderByPriceAcs();
    }

    @GetMapping("/order-date/{orderDate}")
    public List<Product> getProductsBuOrderDate(@PathVariable("orderDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return productServiceImpl.getProductsByOrderDate(date);
    }
    @GetMapping("/orders/all-sum")
    public double getSumOfAllOrderPrice(){
        return productServiceImpl.getSumOfALlOrderPrice();
    }

    @GetMapping("/order/avg/{date}")
    public double getSumOfAllOrderPrice(@PathVariable("date")@DateTimeFormat(pattern = "yyyy-MM-dd") Data date){
        return productServiceImpl.getAvgPriceByOrderDate((Date) date);
    }

    @GetMapping("/{category}/max")
    public Product getMaxPriceByCategory(@PathVariable String category){
        return productServiceImpl.getMaxPriceByCategory(category);
    }

    @GetMapping("/all/limit/{limit}/offset/{offset}/sortBy/{columnName}")
    public List<Product> getProductsAll(@PathVariable Integer limit,
                                        @PathVariable Integer offset,
                                        @PathVariable String columnName){
        System.out.println(columnName);
        return productServiceImpl.getProductsAll(limit,offset,columnName);
    }

    @GetMapping("/orders/{status}/customer/{customerId}")
    public List<Product> getByStatusCustomerId(@PathVariable String status,
                                               @PathVariable Integer customerId){
        return productServiceImpl.getByStatusCustomerId(status,customerId);
    }
}
