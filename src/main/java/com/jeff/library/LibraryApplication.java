package com.jeff.library;

import com.jeff.library.principal.Menu;
import com.jeff.library.repository.AuthorRepository;
import com.jeff.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Principal;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu principal = new Menu(bookRepository, authorRepository);
		principal.showMenu();
	}
}
