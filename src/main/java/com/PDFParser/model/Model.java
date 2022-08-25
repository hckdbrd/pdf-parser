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
    private String Category;
    private String Budget;
    private String Actual;
    private String Difference;
    private String Date;
    private String Description;
    private String Amount;

}
