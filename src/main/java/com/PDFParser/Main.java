package com.PDFParser;

import com.CSVOrm.CSVOrm;
import com.PDFParser.model.Model;
import lombok.SneakyThrows;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TableLookUp tableLookUp = new TableLookUp();
        WriteToConsole write = new WriteToConsole();
        File file = new File("D:\\Projects\\pdf\\src\\main\\resources\\sample.pdf");

        List<List<String>> allTables = tableLookUp.searchTables(file);
        List<Model> personList;

        for (List<String> allTable : allTables) {
            personList = CSVOrm.transform(allTable, Model.class);
            for (Model person : personList) {
                String[] str = allTable.get(0).split(";");
                write.writeValuesFromModel(person, str);
            }
            System.out.println("\r\n");
        }
    }

}
