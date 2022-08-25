package com.PDFParser;

import com.PDFParser.model.Model;
import com.CSVOrm.CSVOrm;
import com.PDFParser.model.Model2;
import com.PDFParser.model.Model3;
import com.PDFParser.model.Model4;
import com.fasterxml.jackson.core.type.TypeReference;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        //URL url = Main.class.getClassLoader().getResource("sample.pdf");
        File file = new File("D:\\Projects\\pdf\\src\\main\\resources\\sample.pdf");
        //File file = new File(url.toURI());
        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(file.toPath()));
        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);
        List<List<String>> tables = new ArrayList<>();
        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);
            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {

                    tables.add(Arrays.asList(buildTable(table).toString()
                            .replaceAll("(?<=[a-zA-Z])\s+(?=[a-zA-Z])","_")
                            .replaceAll("CategoryCategory", "Category") // TODO change this row
                                    .replaceAll("Diﬀerence" , "Difference") //TODO change encoding
                            .split("\r\n")));

                }
            }
        }

        for (int i = 0; i < tables.size(); i++) {
            List<?> personList = CSVOrm.transform(tables.get(i), Model.class);

            for (Object person : personList) {
                System.out.println(person);
            }

            System.out.println("\r\n");
        }

    }

    private static StringBuilder buildTable(PdfTable table) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < table.getRowCount(); i++) {
            buildRow(stringBuilder, table, i);
            stringBuilder.append("\r\n");
        }
        return stringBuilder;
    }

    private static void buildRow(StringBuilder stringBuilder, PdfTable table, int i) {
        List<String> row = new ArrayList<>();
        for (int j = 0; j < table.getColumnCount(); j++) {
            String text = table.getText(i, j);
            if (text != null && text.trim().length() > 1) {
                row.add(text);
            }
        }
        stringBuilder.append(String.join(";", row));
    }
}
