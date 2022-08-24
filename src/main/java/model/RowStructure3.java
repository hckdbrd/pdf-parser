package model;

import lombok.Data;

@Data
public class RowStructure3 implements src.main.java.model.RowStructure {
    private String date;
    private String description;
    private String category;

    public RowStructure3(String date, String description, String category) {
        this.date = date;
        this.description = description;
        this.category = category;
    }
}
