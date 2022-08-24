package model;

import lombok.Data;

@Data
public class RowStructure4 implements RowStructure {
    private String amount;

    public RowStructure4(RowStructure4 rowStructure) {
        this.amount = rowStructure.getAmount();
    }

    public RowStructure4() {
    }
}
