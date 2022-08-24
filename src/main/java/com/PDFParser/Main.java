package com.PDFParser;

import com.PDFParser.model.Model;
import com.CSVOrm.CSVOrm;
import com.PDFParser.model.Model2;
import com.PDFParser.model.Model3;
import com.PDFParser.model.Model4;
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
                    stringBuilder.append("\r\n");
                }
            }
        }

        System.out.println("New Model " + "\r\n");
        String[] allTablesFromPdf = stringBuilder.toString().replaceAll("\\s\\|", "").trim().split("\r\n");
        List<String> asd = Arrays.stream(allTablesFromPdf).toList();
        List<String> str = new ArrayList<>();

        int countElement = 0;

        for (int i = 0 ; i < allTablesFromPdf.length; i++) {
            countElement++;
            if (allTablesFromPdf[i].equals("")) {
                break;
            }
            str.add(i, allTablesFromPdf[i]);

        }

        str.set(0, "Category Budget Actual");
        str.set(6, "PersonalItems 300,00 UAH 80,00 UAH");
        List<Model> personList = CSVOrm.transform(str, Model.class);

        for (Model models : personList) {
            System.out.println(models);
        }

        System.out.println("New Model " + "\r\n");

        int count = 0;
        List<String> stf = new ArrayList<>();
        for (int i = countElement; i < allTablesFromPdf.length; i++) {
            countElement++;
            if (allTablesFromPdf[i].equals("")) {
                break;
            }

            stf.add(count, allTablesFromPdf[i]);
            count++;

        }

        stf.set(0, "Category Difference");
        stf.set(6, "PersonalItems 220,00 UAH");
        List<Model2> personList1 = CSVOrm.transform(stf, Model2.class);

        for (Model2 model2 : personList1) {
            System.out.println(model2);
        }


        int count2 = 0;
        List<String> stringList3 = new ArrayList<>();
        for (int i = countElement; i < allTablesFromPdf.length; i++) {
            countElement++;

            if (allTablesFromPdf[i].equals("   ")) {
                break;
            }
            if (allTablesFromPdf[i].equals("")) {
                break;
            }

            stringList3.add(count2, allTablesFromPdf[i]);
            count2++;

        }

        stringList3.set(3, "02/11/20 FlightNo Travel");
        stringList3.set(5, "02/11/20 CinemaTickets Entertainment");
        stringList3.set(6, "02/11/20 DinnerOut Food");
        stringList3.set(8, "21/11/20 Shoes PersonalItems");

        System.out.println("New Model " + "\r\n");

        List<Model3> personList3 = CSVOrm.transform(stringList3, Model3.class);

        for (Model3 model3 : personList3) {
            System.out.println(model3);
        }

        int count4 = 0;
        List<String> stringList4 = new ArrayList<>();
        for (int i = countElement; i < allTablesFromPdf.length; i++) {
            countElement++;

            if (!allTablesFromPdf[i].equals("   ") & !allTablesFromPdf.equals("")) {
                stringList4.add(count4, allTablesFromPdf[i]);
                count4++;
            }

        }

        System.out.println("New Model " + "\r\n");
        stringList4.remove(0);
        List<Model4> personList4 = CSVOrm.transform(stringList4, Model4.class);

        for (Model4 model4 : personList4) {
            System.out.println(model4);
        }

    }
}
