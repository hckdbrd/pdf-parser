package com.PDFParser;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    @SneakyThrows
    public static void main(String[] args) throws IOException, URISyntaxException {
        URL url = Main.class.getClassLoader().getResource("sample.pdf");
        File file = new File(url.toURI());

        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(file.toPath()));

        StringBuilder stringBuilder = new StringBuilder();
        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);


        List<StringBuilder> builderList = new ArrayList<>();
        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);

            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            String text = table.getText(i, j);
                            if (j != table.getColumnCount() - 1)
                                stringBuilder.append(text + " ");
                            else stringBuilder.append(text);
                        }
                        stringBuilder.append("\r\n");
                    }
                    builderList.add(stringBuilder);
                    stringBuilder = new StringBuilder();
                }
            }
        }

        System.out.println(builderList.get(0));

        Class<RowStructure> item = RowStructure.class;
        Field[] declaredFields = item.getDeclaredFields();
        String category = item.getDeclaredField(declaredFields[0].getName()).getAnnotation(Lookup.class).category();
        String budget = item.getDeclaredField(declaredFields[0].getName()).getAnnotation(Lookup.class).category();
//        String category = item.getDeclaredField(declaredFields[0].getName()).getAnnotation(Lookup.class).category();


        List<String> stringList = new ArrayList<>();
//        stringList.add(0, "Category Budget Actual");
//
        stringBuilder = builderList.get(0);

        String[] split = stringBuilder.toString().split("\\D+\\s+");
        RowStructure rowStructure = new RowStructure("Category", split[1] + " UAH");
        System.out.println(rowStructure.toString());
//        stringList.addAll(List.of(string[3].trim().replaceAll("\\s\\|", "").trim().split("\r\n")));
        //stringList.addAll(List.of(string[3].trim().split("\r\n")));

        //List<String> trimed = stringList.stream().map(str -> str.trim()).collect(Collectors.toList());

//        stringList.remove(1);
//        stringList.set(6, "PersonalItems 300,00 UAH 80,00 UAH");

//        List<Model> personList = CSVOrm.transform(stringList, Model.class);
//
//        for (Model model : personList) {
//            System.out.println(model);
//        }
    }
}
