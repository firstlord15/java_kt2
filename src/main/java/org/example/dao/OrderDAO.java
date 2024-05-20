package org.example.dao;

import org.example.Models.Invoice;
import org.example.Models.Order;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
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
    public List<String> getFields() {
        List<String> result = new ArrayList<>();
        Field[] fields = Order.class.getDeclaredFields();

        result.add(Order.class.getSimpleName());
        for (Field f : fields) {
            result.add(f.getName());
        }

        return result;
    }
}