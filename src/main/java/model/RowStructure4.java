package model;

import lombok.Data;

@Data
public class RowStructure4 implements src.main.java.model.RowStructure {
    private String Amount;

    public RowStructure4(String amount) {
        Amount = amount;
    }
}
