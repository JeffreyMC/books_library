package com.jeff.library.principal;

import com.jeff.library.model.*;
import com.jeff.library.repository.AuthorRepository;
import com.jeff.library.repository.BookRepository;
import com.jeff.library.service.ConsumeAPI;
import com.jeff.library.service.ConvertData;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private ConsumeAPI consumeAPI = new ConsumeAPI();
    private ConvertData converter = new ConvertData();
    private final String BASE_URL = "https://gutendex.com/books/?search=";

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public Menu(BookRepository bookRepository, AuthorRepository authorRepository){
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showMenu(){
        var menu = """
                ________________________________________
                Bienvenido a la librería virtual.
                
                Seleccione la opción que desea ejecutar:
                1. Buscar libro por título.
                2. Listar libros registrados.
                3. Listar autores registrados.
                4. Listar autores vivos en determinado año.
                5. Listar libros por idioma.
                0. Salir.
                """;
        String option = "";

        while(option != "0"){
            System.out.println(menu);
            option = scanner.nextLine();


            switch (option){
                case "1" -> getBookByTitle();
                case "2" -> getAllTitles();
                case "3" -> getAllAuthors();
                case "4" -> getAliveAuthorsByYear();
                case "5" -> getBooksByLanguage();
                case "0" -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción no válida. Intenta de nuevo");
            }
        }
    }

    public BookData getBookData(){
        System.out.println("Escribe el nombre del libro que deseas buscar: ");
        var bookName = scanner.nextLine();

        var json = consumeAPI.getData(BASE_URL+bookName.replace(" ", "+"));
        System.out.println(json);
        var data = converter.getData(json, CompleteBookData.class);


        Optional<BookData> foundBook = data.books().stream()
                .filter(b -> b.title().toUpperCase().contains(bookName.toUpperCase()))
                .findFirst();

        if(foundBook.isPresent()){
            BookData newBook = foundBook.get();
            return newBook;
        }else{
            System.out.println("Libro no encontrado");
        }

        return null;
    }

    public void getBookByTitle(){
        BookData data = getBookData();

        if(data != null){
            Optional<Author> authorFound = data.author().stream().findFirst();

            //verifico si el autor existe en la base de datos
            Optional<Author> authorID = authorRepository.findIdByName(authorFound.get().getName());
            //verifica si el titulo existe
            Optional<Book> bookTitle = bookRepository.findBookByTitleContainsIgnoreCase(data.title());

            if(authorID.isPresent() && !bookTitle.isPresent()){
                Author newAuthor = authorID.get();
                //guarda el libro.
                Book newBook = new Book(data, newAuthor);
                bookRepository.save(newBook);
            }else if(!authorID.isPresent() && !bookTitle.isPresent()){
                Author newAuthor = authorFound.get();
                //guarda el autor
                authorRepository.save(newAuthor);

                //guarda el libro.
                Book newBook = new Book(data, newAuthor);
                bookRepository.save(newBook);
            }

            //muestra el libro
            System.out.println(data);
        }

    }

    public void getAllTitles() {
        System.out.println(bookRepository.findAll());
    }

    public void getAllAuthors(){
        List<Author> authors = authorRepository.findAll();

        authors.stream()
                .forEach(a -> System.out.println(a.fullData()));

    }

    public void getAliveAuthorsByYear(){
        try{
            System.out.print("Ingrese el año: ");
            Integer year = scanner.nextInt();

            //toma el anio actual
            LocalDate actualDate = LocalDate.now();

            if(year > actualDate.getYear()){
                System.out.println("¡¡Diablos, doc!! No estamos en el futuro aún...");
            }else{
                List<Author> authorsFound = authorRepository.findALiveAuthorsByYear(String.valueOf(year));

                if(authorsFound != null){
                    System.out.println(authorsFound);
                }else{
                    System.out.println("No se encontraron autores vivos en ese año.");
                }
            }
        }catch(Exception e){
            System.out.println("Año inválido, intente de nuevo. \n" + e);
        }
    }

    public void getBooksByLanguage(){
        String menu = """
                    Ingrese el idioma en que desea buscar libros: 
                    
                    1. Español
                    2. Inglés
                    3. Francés
                    4. Alemán
                    5. Italiano
                    6. Portugués
                    7. Ruso
                    0. Volver al menú principal
                """;
        String opcion = "-1";

        while(opcion != "0"){
            System.out.println(menu);
            opcion = scanner.nextLine();
            List<String> lang;
            switch (opcion){
                case "1" -> System.out.println(bookRepository.findByLanguage(Arrays.asList("es")));
                case "2" -> System.out.println(bookRepository.findByLanguage(Arrays.asList("en")));
                case "3" -> System.out.println(bookRepository.findByLanguage(Arrays.asList("fr")));
                case "4" -> System.out.println(bookRepository.findByLanguage(Arrays.asList("de")));
                case "5" -> System.out.println(bookRepository.findByLanguage(Arrays.asList("it")));
                case "6" -> System.out.println(bookRepository.findByLanguage(Arrays.asList("pt")));
                case "7" -> System.out.println(bookRepository.findByLanguage(Arrays.asList("ru")));
                default -> System.out.println("Opcion incorrecta. Intente de nuevo");
            }
        }

    }
}
