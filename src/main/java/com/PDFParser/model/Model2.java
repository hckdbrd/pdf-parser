package com.PDFParser.model;

public class Model2 {
    private String Category;
    private String Difference;

    public Model2() {
    }


    public Model2(String category, String difference) {
        Category = category;
        Difference = difference;
    }

    @Override
    public String toString() {
        return "Model2{" +
                "Category='" + Category + '\'' +
                ", Difference='" + Difference + '\'' +
                '}';
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDifference() {
        return Difference;
    }

    public void setDifference(String difference) {
        Difference = difference;
    }
}
