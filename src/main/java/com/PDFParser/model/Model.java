package com.PDFParser.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Model {
    private String category;
    private String budget;
    private String actual;
    private String difference;
    private String date;
    private String description;
    private String amount;

}
