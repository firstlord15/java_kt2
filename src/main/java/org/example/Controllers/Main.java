package org.example.Controllers;

import org.example.Views.Menu;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.menu();
        menu.scannerClose();
    }
}
