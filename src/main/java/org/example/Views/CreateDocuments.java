package org.example.Views;

import org.example.Models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CreateDocuments {
    private final Scanner scanner;

    public CreateDocuments() {
        this.scanner = new Scanner(System.in);
    }

    public List<String> createDoc(String name) {
        List<String> result = new ArrayList<>();
        String id = enterValue("Введите id: ");
        String nameValue = enterValue("Введите " + name + ": ");

        if (id.trim().equalsIgnoreCase("exit()")){
            System.out.println("Создание документа прервано пользователем.");
            return null;
        }

        result.add(id);
        result.add(nameValue);
        return result;
    }

    public List<String> createDoc(String name, int id) {
        List<String> result = new ArrayList<>();
        result.add(String.valueOf(id));
        
        String answerName = enterValue("Введите " + name + ": ");
        if (answerName.trim().equalsIgnoreCase("exit()")) return null;

        result.add(answerName);
        return result;
    }

    private String enterValue(String prompt) {
        System.out.print(prompt + " ");
        String string = scanner.nextLine();

        return string.trim().toLowerCase();
    }

    public PaymentInvoice createPaymentInvoice(int number, int id) {
        List<String> documentData = createDoc("customerName", id);
        if (documentData == null) return null;

        String comments = enterValue("Введите comments: ");
        return new PaymentInvoice(
                Integer.parseInt(documentData.get(0)), number,
                LocalDateTime.now(),
                documentData.get(1),
                comments
        );
    }

    public Payment createPayment(int number, int id){
        List<String> documentData = createDoc("nameSupplier", id);
        if (documentData == null){
            System.out.println("Создание документа прервано пользователем.");
            return null;
        }

        return new Payment(
                Integer.parseInt(documentData.get(0)), number,
                LocalDateTime.now(), documentData.get(1)
        );
    }

    public Invoice createInvoice(int number, int id){
        List<String> documentData = createDoc("clientName", id);
        if (documentData == null){
            System.out.println("Создание документа прервано пользователем.");
            return null;
        }

        String address = enterValue("Введите address: ");
        return new Invoice(
                Integer.parseInt(documentData.get(0)), number,
                LocalDateTime.now(),
                documentData.get(1), address
        );
    }

    public Order createOrder(int number){
        ArrayList<String> products = new ArrayList<>();
        List<String> documentData = createDoc("buyerName");
        if (documentData == null) {
            System.out.println("Создание документа прервано пользователем.");
            return null;
        }

        System.out.println("\nВ формате [<Название продукта> <Кол-во> <Цена>] или 'exit' для завершения");
        while (true) {
            System.out.print("Введите данные продукта: ");
            String nameAndPrice = scanner.nextLine();

            if (nameAndPrice.equalsIgnoreCase("exit") & !products.isEmpty())
                break;

            if (nameAndPrice.equalsIgnoreCase("exit") && products.isEmpty()) {
                System.out.println("Надо ввести хотя бы один продукт");
                continue;
            }

            String[] parts = nameAndPrice.split(" ");
            if (parts.length != 3) {
                System.out.println("Неверный формат ввода. Попробуйте снова.");
                continue;
            }

            String productNameStr = parts[0].trim();
            int amount;
            double price;

            try {
                amount = Integer.parseInt(parts[1].trim());
                price = Double.parseDouble(parts[2].trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка при чтении количества или цены продукта. Попробуйте снова.");
                continue;
            }

            products.add(productNameStr + " " + amount + " " + price);
        }

        System.out.println();
        return new Order(
                Integer.parseInt(documentData.get(0)), number,
                LocalDateTime.now(), documentData.get(1), products
        );
    }

    public ArrayList<Document> doDocs(){
        ArrayList<Document> result = new ArrayList<>();

        int number = Integer.parseInt(enterValue("\nВведите number:"));
        Order order = createOrder(number);
        if (order == null) {
            return null;
        }

        PaymentInvoice paymentInvoice = createPaymentInvoice(number, order.getId()+1);
        if (paymentInvoice == null) {
            return null;
        }

        Payment payment = createPayment(number, order.getId()+2);
        if (payment == null) {
            return null;
        }

        Invoice invoice = createInvoice(number, order.getId()+3);
        if (invoice == null) {
            return null;
        }

        result.add(order);
        result.add(paymentInvoice);
        result.add(payment);
        result.add(invoice);

        return result;
    }
}
