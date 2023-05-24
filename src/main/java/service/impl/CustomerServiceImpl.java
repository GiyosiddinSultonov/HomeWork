package service.impl;

import dao.Customer;
import dao.ProductOrder;
import lombok.Data;
import mapper.CustomerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import service.CustomerService;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Customer> getAllCustomers(){
        String query = "select * from customer";
        List<Customer> result = jdbcTemplate.query(query, new CustomerRowMapper());
        return result;
    }
    @Override
    public List<ProductOrder> getAllProductOrderByCustomerId(Integer customerId) {
        List<ProductOrder> query = jdbcTemplate.query("SELECT * FROM PRODUCT_ORDER WHERE customer_id = ?", resultSet -> {
            List<ProductOrder> productOrderArrayList = new ArrayList<>();
            while (resultSet.next()){
                ProductOrder productOrder = new ProductOrder();
                productOrder.setId(resultSet.getInt("id"));
                productOrder.setOrderDate(resultSet.getDate("order_date").toLocalDate());
                productOrder.setDeliveryDate(resultSet.getDate("delivery_date").toLocalDate());
                productOrder.setStatus(resultSet.getString("status"));
                productOrder.setCustomerId(resultSet.getInt("customer_id"));

                productOrderArrayList.add(productOrder);
            }
            return productOrderArrayList;
        }, customerId);
        return query;

    }


    @Override
    public List<Customer> getAllCustomerByLikeName(String name) {
        name = name.replace("'", "''")
                .replace("\"", "");

        return jdbcTemplate.query("SELECT * FROM customer WHERE name LIKE '%"+name+"%'",new CustomerRowMapper());
    }

    @Override
    public List<Customer> getCustomerWithMaxOrder() {
        return  jdbcTemplate.query("SELECT DISTINCT(C2.ID), C2.NAME, C2.TIER FROM PRODUCT_ORDER\n" +
                "JOIN ORDER_PRODUCT_RELATIONSHIP OPR on PRODUCT_ORDER.ID = OPR.ORDER_ID\n" +
                "JOIN CUSTOMER C2 on C2.ID = PRODUCT_ORDER.CUSTOMER_ID\n" +
                "WHERE OPR.PRODUCT_ID = (SELECT ID FROM PRODUCT WHERE PRICE = (SELECT MAX(PRICE) FROM PRODUCT))",new CustomerRowMapper());
    }
}
