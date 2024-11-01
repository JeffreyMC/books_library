package com.jeff.library.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Stream;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String birthYear;
    private String deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author(){}

    public Author(Long id, String name, String birthYear, String deathYear){
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public Author(String name, String birth_year, String death_year) {
        this.name = name;
        this.birthYear = birth_year;
        this.deathYear = death_year;
    }


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth_year() {
        return birthYear;
    }

    public void setBirth_year(String birth_year) {
        this.birthYear = birth_year;
    }

    public String getDeath_year() {
        return deathYear;
    }

    public void setDeath_year(String death_year) {
        this.deathYear = death_year;
    }

    @Override
    public String toString() {
        return name;
    }

    public String fullData(){
        //cadena para la lista de libros
        StringBuilder listaLibros = new StringBuilder();

        if(books == null){
            listaLibros.append("Sin libros aun");
        }else{
            for (Book lista : books) {
                listaLibros.append("[" + lista.getTitle()).append("] ");
            }
        }
        String datos = String.format("""
                ------------------------------------------------
                Nombre: %s
                Fecha de nacimiento: %s
                Fecha de defuncion: %s
                Libros: %s
                -------------------------------------------------\n
                """, this.name, this.birthYear, this.deathYear, listaLibros);

        return datos;
    }
}
