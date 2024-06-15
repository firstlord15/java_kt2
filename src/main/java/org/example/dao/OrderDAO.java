package org.example.dao;

import org.example.Models.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class OrderDAO extends BaseDAO<Order> {
    private Order order;

    public OrderDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        this.order = new Order();
    }

    @Override
    public int getId(Order order) {
        return order.getId();
    }

    @Override
    public int getNumber(Order order) {
        return order.getNumber();
    }

    @Override
    public void upload() {
        setEntities(getJdbcTemplate().query("SELECT * FROM \"order\" ORDER BY id", new BeanPropertyRowMapper<>(Order.class)));
    }

    private void addProducts(String sql, Order order, JdbcTemplate jdbcTemplate) {
        if (order.getProducts() != null) {
            for (String product : order.getProducts()) {
                String[] productsList = null;
                try { productsList = product.trim().split(" : "); }
                catch (Exception e) { productsList = product.trim().split(":"); }

                if (productsList.length == 2) {
                    try {
                        String productName = productsList[0];
                        BigDecimal productPrice = new BigDecimal(productsList[1]);
                        jdbcTemplate.update(sql, productName, productPrice, order.getId());
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing product price: " + productsList[1]);
                    } catch (DataAccessException e) {
                        System.out.println("Error executing SQL update: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid product format: " + product);
                }
            }
        }
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, Order order) {
        String sqlMain = "INSERT INTO \"order\" (number, date, buyername) VALUES (?, ?, ?)";
        String sqlProducts = "INSERT INTO products (name, price, id_orders) VALUES (?, ?, ?)";
        jdbcTemplate.update(sqlMain, order.getNumber(), order.getOrderDate() != null ? order.getOrderDate() : LocalDateTime.now(), order.getBuyerName());
        addProducts(sqlProducts, order, jdbcTemplate);
    }

    @Override
    public void update(JdbcTemplate jdbcTemplate, Order entity, int id) {
        String sqlMain = "UPDATE \"order\" SET number=?, buyername=?, date=? WHERE id=?";
        jdbcTemplate.update(sqlMain, entity.getNumber(), entity.getBuyerName(), entity.getOrderDate(), id);

        // Удаляем старые продукты перед вставкой новых
        String sqlProducts = "DELETE FROM products WHERE id_orders=?";
        jdbcTemplate.update(sqlProducts, id);

        String insertProductSql = "INSERT INTO products (name, price, id_orders) VALUES (?, ?, ?)";
        addProducts(insertProductSql, entity, jdbcTemplate);
    }

    private List<String> getDBProducts(int id){
        String sql = "SELECT name, price FROM products WHERE id_orders = ?";
        return getJdbcTemplate().query(sql, new Object[]{id}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString("name");
                String price = rs.getBigDecimal("price").toString();
                return name + " : " + price;
            }
        });
    }

    @Override
    public Order findById(int locator) {
        Order order = getJdbcTemplate().query(
                "SELECT * FROM \"order\" WHERE id = ?", new Object[]{locator}, new BeanPropertyRowMapper<>(Order.class)
        ).stream().findAny().orElse(null);
        Objects.requireNonNull(order).setProducts(getDBProducts(locator));

        return order;
    }

    @Override
    public Order findByNumber(int number) {
        return getJdbcTemplate().query(
                "SELECT * FROM \"order\" WHERE number = ?", new Object[]{number}, new BeanPropertyRowMapper<>(Order.class)
        ).stream().findAny().orElse(null);
    }

    public Order findByNumber(int number, boolean isProduct) {
        Order order = getJdbcTemplate().query(
                "SELECT * FROM \"order\" WHERE number = ?", new Object[]{number}, new BeanPropertyRowMapper<>(Order.class)
        ).stream().findAny().orElse(null);
        Objects.requireNonNull(order).setProducts(getDBProducts(order.getId()));

        return order;
    }

    @Override
    public Order getDoc() {
        return order;
    }

    @Override
    public void setDoc(Order entity) {
        order = entity;
    }
}