package org.example.Models;

public interface Document {
    // Метод для получения уникального идентификатора документа
    int getId();
    int getNumber();
    // Метод для установки уникального идентификатора документа
    void setId(int id);
    void setNumber(int number);

    DocumentType getType();

    // Метод для вывода информации о документе
    String displayInfo();
}
