package com.alura.literalura;

import com.alura.literalura.model.Author;
import com.alura.literalura.service.ApiService;
import com.alura.literalura.service.BookServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	private static ApiService apiService;

	private static BookServices bookServices;

	public LiteraluraApplication(ApiService apiService, BookServices bookServices) {
		this.apiService = apiService;
		this.bookServices = bookServices;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n=== Menu de Opções ===");
			System.out.println("1. Buscar livro pelo título (API)");
			System.out.println("2. Listar livros registrados (Banco de Dados)");
			System.out.println("3. Listar autores registrados");
			System.out.println("4. Listar autores vivos de um determinado ano");
			System.out.println("5. Listar livros de um determinado idioma");
			System.out.println("6. Sair");
			System.out.print("Escolha uma opção: ");
			int opcao = scanner.nextInt();
			scanner.nextLine();

			try {
				switch (opcao) {
					case 1 -> {
						System.out.print("Digite o título do livro: ");
						String titulo = scanner.nextLine();
						bookServices.buscarELivrarLivroPorTitulo(titulo);
						//bookServices.cadastrarAutor(new Author("nome",1890,true));

					}
					case 2 -> bookServices.listarLivrosRegistrados();
					case 3 -> bookServices.listarAutoresRegistrados();
					case 4 -> {
						System.out.print("Digite o ano: ");
						int ano = scanner.nextInt();
						bookServices.listarAutoresVivosPorAno(ano);
					}
					case 5 -> {
						System.out.print("Digite o idioma: ");
						String idioma = scanner.nextLine();
						bookServices.listarLivrosPorIdioma(idioma);
					}
					case 6 -> {
						System.out.println("Saindo...");
						return;
					}
					default -> System.out.println("Opção inválida! Tente novamente.");
				}
			} catch (Exception e) {
				System.err.println("Ocorreu um erro: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
