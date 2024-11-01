package com.jeff.library.model;

public enum Language {
    EN("en"),
    ES("es"),
    FR("fr"),
    DE("de"),
    IT("it"),
    PT("pt"),
    RU("ru");

    private String languageFromAPI;

    Language(String languageFromAPI){
        this.languageFromAPI = languageFromAPI;
    }

    public static Language fromString(String text){
        for(Language languages: Language.values()){
            if(languages.languageFromAPI.equalsIgnoreCase(text)){
                return languages;
            }
        }

        throw new IllegalArgumentException("Ningun lenguaje encontrado: " + text);
    }
}
