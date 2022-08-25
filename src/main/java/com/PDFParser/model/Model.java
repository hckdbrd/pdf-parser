package com.PDFParser.model;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Model {
    private String Category;
    private String Budget;
    private String Actual;
    private String Difference;
    private String Date;
    private String Description;
    private String Amount;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getBudget() {
        return Budget;
    }

    public void setBudget(String budget) {
        Budget = budget;
    }

    public String getActual() {
        return Actual;
    }

    public void setActual(String actual) {
        Actual = actual;
    }

    public String getDifference() {
        return Difference;
    }

    public void setDifference(String difference) {
        Difference = difference;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    @Override
    public String toString() {
        return "Model{" +
                "Category='" + Category + '\'' +
                ", Budget='" + Budget + '\'' +
                ", Actual='" + Actual + '\'' +
                ", Difference='" + Difference + '\'' +
                ", Date='" + Date + '\'' +
                ", Description='" + Description + '\'' +
                ", Amount='" + Amount + '\'' +
                '}';
    }
}
