package model;

import lombok.Data;

@Data
public class RowStructure2 implements RowStructure {
    private String category;

    private String difference;

    public RowStructure2(RowStructure2 rowStructure) {
        this.category = rowStructure.getCategory();
        this.difference = rowStructure.getDifference();
    }

    public RowStructure2() {
    }
}
