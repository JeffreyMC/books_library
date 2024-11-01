package com.jeff.library.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
 public class Book {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String title;
     private Double download_count;
     private List<String> language;

     @ManyToOne
     private Author author;

     public Book(){}

     public Book(Double downloads, String title, List<String> language, Author author){
         this.language = language;
         this.title = title;
         this.download_count = downloads;
         this.author = author;
     }

     public Book(BookData data, Author author){
         this.author = author;
         this.download_count = data.downloadCount();
         this.title = data.title();
         this.language = data.language();
     }


    public Long getId() {
         return id;
     }

     public void setId(Long id) {
         this.id = id;
     }

     public String getTitle() {
         return title;
     }

     public void setTitle(String title) {
         this.title = title;
     }

     public Double getDownload_count() {
         return download_count;
     }

     public void setDownload_count(Double download_count) {
         this.download_count = download_count;
     }

     public List<String> getLanguage() {
         return language;
     }

     public void setLanguage(List<String> language) {
         this.language = language;
     }

     public Author getAuthor() {
         return author;
     }

     public void setAuthor(Author author) {
         this.author = author;
     }

    @Override
    public String toString() {
        return "\n---------------BOOK----------------------\n" +
                "Titulo: '" + title + '\'' +
                "\nTotal de descargas: " + download_count +
                "\nlenguajes: " + language +
                "\nautor: " + author +
                "\n---------------------------------------\n";
    }
}
