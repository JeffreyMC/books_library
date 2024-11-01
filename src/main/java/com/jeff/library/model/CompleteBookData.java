package com.jeff.library.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompleteBookData(
        @JsonAlias("results")List<BookData> books
        ) {
}
