package com.PDFParser.model;

public class Model4 {
    private String Amount;

    public Model4() {
    }

    public Model4(String amount) {
        Amount = amount;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    @Override
    public String toString() {
        return "Model4{" +
                "Amount='" + Amount + '\'' +
                '}';
    }
}
