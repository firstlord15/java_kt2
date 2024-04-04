package org.example.Views;

import org.example.Models.Document;
import org.example.Models.DocumentType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private ArrayList<ArrayList<Document>> listDocuments;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.listDocuments = new ArrayList<ArrayList<Document>>();
    }

    public void scannerClose(){
        scanner.close();
    }

    public void displayAllDocuments(ArrayList<Document> documents) {
        System.out.println("\n=====================================");
        for (Document doc : documents) {
            System.out.println(doc.displayInfo());
        }
        System.out.println("=====================================\n");
    }

    private <T> void printOptions(T[] options) {
        for (int i = 0; i < options.length; i++) {
            System.out.println("[" + i + "] " + options[i]);
        }
    }

    private int chooseDocument(String action) {
        if (listDocuments.isEmpty()) {
            System.out.println("Нет циклов документов для " + action.toLowerCase() + ".");
            return -1;
        }

        System.out.println("[" + action + " цикла документов]");
        for (int i = 0; i < listDocuments.size(); i++) {
            ArrayList<Document> listDocument = listDocuments.get(i);
            System.out.println("[" + i + "]  Doc number: " + listDocument.get(0).getNumber());
        }

        System.out.print("\nВыберите нужную вам цепочку документов: ");
        int indexDocs = scanner.nextInt();
        scanner.nextLine();

        if (indexDocs < 0 || indexDocs >= listDocuments.size()) {
            System.out.println("\nНекорректный номер цепочки документов.");
            return -1;
        }

        return indexDocs;
    }

    public void createDocs() {
        CreateDocuments createDocuments = new CreateDocuments();
        listDocuments.add(createDocuments.doDocs());
    }

    public void viewDocs() {
        int indexDocs = chooseDocument("Просмотр");
        if (indexDocs >= 0) {
            displayAllDocuments(listDocuments.get(indexDocs));
        }
    }

    public void editDocs() {
        int indexDocs = chooseDocument("Редактирование");
        if (indexDocs >= 0) {
            DocumentType[] documentTypes = DocumentType.values();
            printOptions(documentTypes);

            System.out.print("Введите индекс типа документа: ");
            int indexDocType = scanner.nextInt();
            scanner.nextLine();

            Document document = listDocuments.get(indexDocs).get(indexDocType);
            Field[] fields = document.getClass().getDeclaredFields();
            printOptions(fields);

            System.out.print("Введите индекс поля: ");
            int indexField = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Введите данные, на которые вы хотите заменить: ");
            String answer = scanner.next();
            scanner.nextLine();

            try {
                fields[indexField].setAccessible(true);
                fields[indexField].set(document, answer);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void deleteDocs() {
        int indexDocs = chooseDocument("Удаление");
        if (indexDocs >= 0) {
            listDocuments.remove(indexDocs);
        }
    }

    public int mainMenu() {
        System.out.println("\n[Основное меню]");
        System.out.println("[1] Создание цикла документов");
        System.out.println("[2] Просмотр цикла документов");
        System.out.println("[3] Редактирование цикла документов");
        System.out.println("[4] Удаление цикла документов");
        System.out.println("[5] Для выхода\n");

        System.out.print("Введите свой ответ: ");
        int answer = scanner.nextInt();
        scanner.nextLine();

        return answer;
    }

    public void menu() {
        while (true) {
            int number = mainMenu();

            switch (number) {
                case 1: createDocs();
                    break;
                case 2: viewDocs();
                    break;
                case 3: editDocs();
                    break;
                case 4: deleteDocs();
                    break;
                case 5: System.exit(0);
                default: System.out.println("Некорректный ввод. Пожалуйста, повторите попытку.");
            }
        }
    }



    public void method(){

    }

    public List<ArrayList<Document>> getListDocuments() {
        return listDocuments;
    }

    public void setListDocuments(ArrayList<ArrayList<Document>> listDocuments) {
        this.listDocuments = listDocuments;
    }
}



