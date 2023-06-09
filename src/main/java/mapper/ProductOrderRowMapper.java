package mapper;

import dao.Product;
import dao.ProductOrder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductOrderRowMapper implements RowMapper<ProductOrder> {
    @Override
    public ProductOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setId(rs.getInt("id"));
        productOrder.setOrderDate(rs.getDate("order_date").toLocalDate());
        productOrder.setDeliveryDate(rs.getDate("delivery_date").toLocalDate());
        productOrder.setStatus(rs.getString("status"));
        productOrder.setCustomerId(rs.getInt("customer_id"));
        return productOrder;
    }
}
