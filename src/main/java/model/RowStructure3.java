package model;

import lombok.Data;

@Data
public class RowStructure3 implements RowStructure {
    private String date;
    private String description;
    private String category;

    public RowStructure3(RowStructure3 rowStructure) {
        this.category = rowStructure.getCategory();
        this.description = rowStructure.getDescription();
        this.date = rowStructure.getDate();
    }

    public RowStructure3() {
    }
}
