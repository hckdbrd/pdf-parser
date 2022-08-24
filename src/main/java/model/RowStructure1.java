package model;

import lombok.Data;

@Data
public class RowStructure1 implements RowStructure {

    private String category;

    private String budget;

    private String actual;

    public RowStructure1(RowStructure1 rowStructure) {
        this.category = rowStructure.getCategory();
        this.budget = rowStructure.getBudget();
        this.actual = rowStructure.getActual();
    }

    public RowStructure1() {
    }
}
