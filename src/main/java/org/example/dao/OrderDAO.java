package org.example.dao;

import org.example.Models.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDAO extends BaseDAO<Order> {
    public OrderDAO(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
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
        set(getJdbcTemplate().query("SELECT * FROM \"order\"", new BeanPropertyRowMapper<>(Order.class)));
    }

    @Override
    public void save(JdbcTemplate jdbcTemplate, Order order) {
        String sqlMain = "INSERT INTO \"order\" (number, date, buyername) VALUES (?, ?, ?)";
        String sqlProducts = "INSERT INTO products (name, price) VALUES (?, ?)";
        jdbcTemplate.update(sqlMain, order.getNumber(), LocalDateTime.now(), order.getBuyerName());

        if (order.getProducts() != null) {
            for (String product : order.getProducts()) {
                String[] productsList = product.split(" ");
                jdbcTemplate.update(sqlProducts, productsList[0], productsList[1]);
            }
        }
    }

    @Override
    public List<String> getFields() {
        List<String> result = new ArrayList<>();
        Field[] fields = Order.class.getDeclaredFields();

        result.add(Order.class.getSimpleName());
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            if (i != 2) result.add(f.getName());
        }

        return result;
    }
}