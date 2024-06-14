package org.example.Models;

import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;

public interface Document {
    int getId();
    int getNumber();
    void setId(int id);
    void setNumber(int number);
    DocumentType getType();
    List<String> displayInfo();
}
