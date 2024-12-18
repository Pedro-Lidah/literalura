package com.alura.literalura.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private int birthYear;
    private boolean isAlive;
    @ManyToMany
    private List<Book> books;

    public Author() {
    }

    public Author(String name, int birthYear, boolean isAlive) {
        this.name = name;
        this.birthYear = birthYear;
        this.isAlive = isAlive;
    }
    public void addBook(Book book){
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
    }
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public String toString() {
        return name;
    }
}