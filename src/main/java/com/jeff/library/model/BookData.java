package com.jeff.library.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData (
    @JsonAlias("title") String title,
    @JsonAlias("authors") List<Author> author,
    @JsonAlias("languages") List<String> language,
    @JsonAlias("download_count") Double downloadCount
)
{}
