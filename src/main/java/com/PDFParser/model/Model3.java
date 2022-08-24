package com.PDFParser.model;

import java.time.LocalDate;

public class Model3 {
    private String Date;
    private String Description;
    private String Category;

    public Model3(String date, String description, String category) {
        Date = date;
        Description = description;
        Category = category;
    }

    public Model3() {
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

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    @Override
    public String toString() {
        return "Model3{" +
                "Date=" + Date +
                ", Description='" + Description + '\'' +
                ", Category='" + Category + '\'' +
                '}';
    }
}
