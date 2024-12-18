package com.alura.literalura.service;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.model.BookList;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServices {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ApiService apiService;

    public BookServices(BookRepository bookRepository, AuthorRepository authorRepoitory, ApiService apiService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepoitory;
        this.apiService = apiService;
    }

    public List<Book> listarLivrosRegistrados() {
        return bookRepository.findAll();
    }

    public List<Book> buscarELivrarLivroPorTitulo(String titulo){
        try {
            String encodedTitle = URLEncoder.encode(titulo, StandardCharsets.UTF_8);
            String apiUrl = "https://gutendex.com/books?search=" + encodedTitle;

            BookList bookList = apiService.fetchBooks(apiUrl);

            if (bookList.getResults().isEmpty()) {
                System.out.println("Nenhum livro encontrado para o título: " + titulo);
            } else {
                System.out.println("\nLivros encontrados:");
                for (Book book : bookList.getResults()) {
                    System.out.println("- Título: " + book.getTitle());
                    System.out.println("  Autores: " + (book.getAuthors().isEmpty() ? "Nenhum autor encontrado" : book.getAuthors()));
                    System.out.println("  Idioma: " + book.getLanguage());
                    System.out.println("  Downloads: " + book.getDownloads());
                    System.out.println();

                    bookRepository.save(book);

                    for (Author author : book.getAuthors()) {
                        author.addBook(book);
                        book.addAuthor(author);
                        authorRepository.save(author);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar livro: " + e.getMessage());
        }
        return bookRepository.findAll();

    }

    public List<Author> listarAutoresRegistrados(){
        return authorRepository.findAll();
    }
    public List<Author> listarAutoresVivosPorAno(int ano){
        return authorRepository.findAll().stream()
                .filter(author -> author.isAlive() && author.getBirthYear() <= ano)
                .collect(Collectors.toList());
    }
    public List<Book> listarLivrosPorIdioma(String idioma){
        return bookRepository.findAll().stream()
                .filter(book -> book.getLanguage().equalsIgnoreCase(idioma))
                .collect(Collectors.toList());
    }
}
