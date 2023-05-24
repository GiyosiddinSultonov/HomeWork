package service.impl;

import dao.Product;
import lombok.Data;
import mapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import service.ProductService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public List<Product> getProductsByCategory(String categoryName){
        String query = "select * from product where category=?";
        List<Product> result = jdbcTemplate.query(query, new ProductRowMapper(), categoryName);
        return result;
    }

    public List<Product> getAllProductsOrderByPriceAcs(){
        String query = "select * from product order by price";
        List<Product> result = jdbcTemplate.query(query, new ProductRowMapper());
        return result;
    }

    public List<Product> getProductsByOrderDate( Date orderDate ){
        String query =
                "select p.id,p.category,p.name,p.price from order_product_relationship  rel join\n" +
                        "(select id,order_date from product_order where order_date = ?) as orderId\n" +
                        "    on rel.order_id = orderId.id\n" +
                        "join product p on rel.product_id = p.id;";
        List<Product> result = jdbcTemplate.query(query, new ProductRowMapper(),orderDate);
        return result;
    }

    public Double getSumOfALlOrderPrice(){
        String query ="select sum(p.price) " +
                "from " +
                "product p " +
                "join " +
                "order_product_relationship opr " +
                "on p.id = opr.product_id";
        Double result = jdbcTemplate.queryForObject(query, Double.class);
        return result;
    }

    public Double getAvgPriceByOrderDate(Date orderDate){
        String query = "select avg(p.price)\n" +
                "from order_product_relationship  rel\n" +
                "    join\n" +
                "    (select id,order_date from product_order where order_date = ?) as orderId\n" +
                "        on rel.order_id = orderId.id\n" +
                "    join product p on rel.product_id = p.id;";
        Double result = jdbcTemplate.queryForObject(query, Double.class, orderDate);
        return result;
    }

    @Override
    public Product getMaxPriceByCategory(String category) {
        return jdbcTemplate.queryForObject("SELECT * FROM PRODUCT WHERE PRICE = (SELECT MAX(PRICE) FROM PRODUCT\n" +
                "WHERE CATEGORY = ?)",new ProductRowMapper(),category);
    }

    @Override
    public List<Product> getProductsAll(Integer limit, Integer offset, String columnName) {
        List<Product> products = new ArrayList<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("columnName",columnName);
        mapSqlParameterSource.addValue("limit",limit);
        mapSqlParameterSource.addValue("offset",offset);

        String sql = "SELECT * FROM product ORDER BY :columnName OFFSET :offset ROWS FETCH NEXT :limit ROWS ONLY";
        namedParameterJdbcTemplate.query(
                sql,
                mapSqlParameterSource,
                rs -> {
                    products.add(
                            new Product(
                                    rs.getInt("id"),
                                    rs.getString("category"),
                                    rs.getDouble("price"),
                                    rs.getString("name")
                            )
                    );
                });

        return products;
    }

    @Override
    public List<Product> getByStatusCustomerId(String status, Integer customerId) {
        return jdbcTemplate.query("SELECT P.ID,P.CATEGORY,P.NAME,P.PRICE FROM PRODUCT P\n" +
                "JOIN ORDER_PRODUCT_RELATIONSHIP OPR on P.ID = OPR.PRODUCT_ID\n" +
                "WHERE OPR.ORDER_ID IN\n" +
                "(SELECT ID FROM PRODUCT_ORDER WHERE CUSTOMER_ID = ? AND STATUS = ?)", new ProductRowMapper(),customerId,status);
    }


}
