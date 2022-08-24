package com.PDFParser.model;

public class Model {
    private String Category;
    private String Budget;
    private String Actual;

    public Model(String category, String budget, String actual) {
        Category = category;
        Budget = budget;
        Actual = actual;
    }

    public Model() {
    }

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

    @Override
    public String toString() {
        return "Model{" +
                "Category='" + Category + '\'' +
                ", Budget='" + Budget + '\'' +
                ", Actual='" + Actual + '\'' +
                "}";
    }
}
