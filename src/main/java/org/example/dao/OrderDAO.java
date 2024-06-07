package org.example.dao;

import org.example.Models.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
        setEntities(getJdbcTemplate().query("SELECT * FROM \"order\"", new BeanPropertyRowMapper<>(Order.class)));
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, Order order) {
        String sqlMain = "INSERT INTO \"order\" (number, date, buyername) VALUES (?, ?, ?)";
        String sqlProducts = "INSERT INTO products (name, price) VALUES (?, ?)";

        jdbcTemplate.update(sqlMain, order.getNumber(), order.getOrderDate() != null ? order.getOrderDate() : LocalDateTime.now(), order.getBuyerName());

        if (order.getProducts() != null) {
            for (String product : order.getProducts()) {
                String[] productsList = product.split(" ");
                if (productsList.length == 2) {
                    try {
                        String productName = productsList[0];
                        int productPrice = Integer.parseInt(productsList[1]);
                        jdbcTemplate.update(sqlProducts, productName, productPrice);
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());;
                    }
                }
            }
        }
    }

    @Override
    public Order findById(int locator) {
        return getJdbcTemplate().query("SELECT * FROM \"order\" WHERE id=?", new Object[]{locator}, new BeanPropertyRowMapper<>(Order.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public Order findByNumber(int number) {
        return getJdbcTemplate().query("SELECT * FROM \"order\" WHERE number=?", new Object[]{number}, new BeanPropertyRowMapper<>(Order.class))
                .stream().findAny().orElse(null);
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