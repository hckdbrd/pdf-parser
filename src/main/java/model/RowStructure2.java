package model;

import lombok.Data;

@Data
public class RowStructure2 implements src.main.java.model.RowStructure {
    private String category;

    private String difference;

    public RowStructure2(String category, String difference) {
        this.category = category;
        this.difference = difference;
    }
}
