package service.impl;

import dao.ProductOrder;
import lombok.RequiredArgsConstructor;
import mapper.ProductOrderRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import service.ProductOrderService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductOrderServiceServiceImpl implements ProductOrderService {
    private final JdbcTemplate jdbcTemplate;



    @Override
    public List<ProductOrder> getProductOrderByStatus(String statusName) {
        return jdbcTemplate.query("select * from product_order where status = ?", new ProductOrderRowMapper(), statusName.toUpperCase());
    }

    @Override
    public Double getSumOrdersByDate(LocalDate date) {
        return jdbcTemplate.queryForObject("SELECT SUM(P.PRICE) FROM PRODUCT_ORDER PO\n" +
                "JOIN ORDER_PRODUCT_RELATIONSHIP OPR on PO.ID = OPR.ORDER_ID\n" +
                "JOIN PRODUCT P on P.ID = OPR.PRODUCT_ID\n" +
                "WHERE PO.ORDER_DATE = ?", Double.class, Date.valueOf(date));
    }

    @Override
    public List<ProductOrder> getMostRecentN(Integer n) {
        return jdbcTemplate.query("SELECT * FROM PRODUCT_ORDER ORDER BY ORDER_DATE DESC FETCH NEXT ? ROWS ONLY",new ProductOrderRowMapper(),n);
    }

    @Override
    public List<ProductOrder> getOrdersLastNDay(Integer n) {
        Date maxDate = jdbcTemplate.queryForObject(
                "select max(order_date) from product_order",
                Date.class
        );
        return jdbcTemplate.query(
                "select * from product_order " +
                        "where order_date between ? - ? " +
                        "and ? " +
                        "order by order_date desc",
                new ProductOrderRowMapper(),
                maxDate, n, maxDate
        );
    }

    @Override
    public Double getAllPriceByStatus(String status) {

        return jdbcTemplate.queryForObject("SELECT SUM(P.PRICE) FROM PRODUCT P\n" +
                "JOIN ORDER_PRODUCT_RELATIONSHIP OPR on P.ID = OPR.PRODUCT_ID\n" +
                "WHERE OPR.ORDER_ID IN\n" +
                "    (SELECT PRODUCT_ORDER.ID FROM PRODUCT_ORDER\n" +
                "WHERE STATUS = ?)",Double.class,status);
    }

}
