package controller;

import dao.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.ProductOrderService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/product-order")
public class ProductOrderController {
    @Autowired
    ProductOrderService productOrderService;

    @GetMapping("/status/{statusName}")
    public List<ProductOrder> getProductOrderByStatus(@PathVariable String statusName){
        return productOrderService.getProductOrderByStatus(statusName);
    }

    @GetMapping("/all-sum/{date}")
    public Double getSumOrdersByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate date){
        return productOrderService.getSumOrdersByDate(date);
    }

    @GetMapping("/orders/most-recent/{n}")
    public List<ProductOrder> getMostRecentN(@PathVariable Integer n){
        return productOrderService.getMostRecentN(n);
    }

    @GetMapping("/orders/day/{n}")
    public List<ProductOrder> getLastNDayOrders(@PathVariable Integer n){
        return productOrderService.getOrdersLastNDay(n);
    }

    @GetMapping("/orders/{status}/all-price")
    public Double getAllPriceByStatus(@PathVariable String status){
        return productOrderService.getAllPriceByStatus(status);
    }
}

