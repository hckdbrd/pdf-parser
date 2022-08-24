package com.PDFParser;

import com.PDFParser.model.Model;
import com.CSVOrm.CSVOrm;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        URL url = Main.class.getClassLoader().getResource("sample.pdf");
        File file = new File(url.toURI());

        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(file.toPath()));

        StringBuilder stringBuilder = new StringBuilder();
        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);


        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);

            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            String text = table.getText(i, j);
                            stringBuilder.append(text + " | ");
                        }
                        stringBuilder.append("\r\n");
                    }
                }
            }
        }

        List<String> stringList = new ArrayList<>();
        stringList.add(0, "Category Budget Actual");

        String[] string = stringBuilder.toString().split("Category | Budget | Actual");
        stringList.addAll(List.of(string[3].trim().replaceAll("\\s\\|", "").trim().split("\r\n")));
        //stringList.addAll(List.of(string[3].trim().split("\r\n")));

        //List<String> trimed = stringList.stream().map(str -> str.trim()).collect(Collectors.toList());

        stringList.remove(1);
        stringList.set(6, "PersonalItems 300,00 UAH 80,00 UAH");

        List<Model> personList = CSVOrm.transform(stringList, Model.class);

        for (Model model : personList) {
            System.out.println(model);
        }
    }
}
