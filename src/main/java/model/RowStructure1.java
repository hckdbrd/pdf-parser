package model;

import lombok.Data;

@Data
public class RowStructure1 implements src.main.java.model.RowStructure {

    private String category;

    private String budget;

    private String actual;

    public RowStructure1(String category, String budget, String actual) {
        this.category = category;
        this.budget = budget;
        this.actual = actual;
    }


}
