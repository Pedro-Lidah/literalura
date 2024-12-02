package com.alura.literalura.principal;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        String url = "https://gutendex.com/books/";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();        try {

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            if (response.statusCode() == 200) {
                String responseBody = response.body();
                Gson gson = new Gson();
                
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                System.out.println("Resposta da API: " + jsonObject);
                
             BookList books = gson.fromJson(responseBody, BookList.class);
             books.results.forEach(book -> System.out.println("Título: " + book.title));
            } else {
                System.out.println("Erro: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class BookList {
        Book[] results;

        static class Book {
            String title;
            String[] authors;
        }
    }
}
