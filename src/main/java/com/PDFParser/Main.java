package com.PDFParser;

import com.CSVOrm.CSVOrm;
import com.PDFParser.model.Model;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        TableLookUp tableLookUp = new TableLookUp();
        WriteToConsole write = new WriteToConsole();
        URL url = Main.class.getClassLoader().getResource("sample.pdf");
        File file = new File(url.toURI());
        List<List<String>> allTables = tableLookUp.searchTables(file);
        List<List<Model>> modelList = new ArrayList<>();
        List<Model> personList;
        writeToModelList(write, allTables, modelList);
        System.out.print("");
    }

    private static void writeToModelList(WriteToConsole write, List<List<String>> allTables, List<List<Model>> modelList) {
        List<Model> personList;
        for (List<String> allTable : allTables) {
            personList = CSVOrm.transform(allTable, Model.class);
            modelList.add(personList);
            printToConsole(write, personList, allTable);
        }
    }

    private static void printToConsole(WriteToConsole write, List<Model> personList, List<String> allTable) {
        for (Model person : personList) {
            String[] str = allTable.get(0).split(";");
            write.writeValuesFromModel(person, str);
        }
        System.out.println("\r\n");
    }

}
