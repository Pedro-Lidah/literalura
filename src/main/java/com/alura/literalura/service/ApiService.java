package com.alura.literalura.service;

import com.alura.literalura.model.BookList;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
@Service
public class ApiService {
    public BookList fetchBooks(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        Scanner scanner = new Scanner(url.openStream());
        StringBuilder jsonResponse = new StringBuilder();
        while (scanner.hasNext()) {
            jsonResponse.append(scanner.nextLine());
        }
        scanner.close();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonResponse.toString(), BookList.class);
    }
}