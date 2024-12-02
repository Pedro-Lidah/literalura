package com.alura.literalura.principal;

import com.google.gson.*;

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
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                JsonArray results = jsonObject.getAsJsonArray("results");

                for (JsonElement element : results) {
                    JsonObject book = element.getAsJsonObject();
                    String title = book.get("title").getAsString();

                    System.out.println("Título: " + title);

                    if (book.has("authors")) {
                        JsonArray authors = book.getAsJsonArray("authors");
                        for (JsonElement authorElement : authors) {
                            JsonObject author = authorElement.getAsJsonObject();
                            String authorName = author.get("name").getAsString();
                            System.out.println("   Autor: " + authorName);
                        }
                    }
                }
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
