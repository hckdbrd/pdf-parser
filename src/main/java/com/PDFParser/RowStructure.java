package com.PDFParser;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RowStructure {

    @Lookup(category = "\\d+\\S+")
    private String category;

    @Lookup(category = "\\D+\\s+")
    private String budget;

//    @Lookup("")
//    private String actual;


    @Override
    public String toString() {
        return "RowStructure{" +
                "category='" + category + '\'' +
                ", budget='" + budget + '\'' +
                '}';
    }
}
