package com.example.snakesandladders;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        try {
            GameApplication.main(args);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
