package com.PDFParser;

import com.CSVOrm.CSVOrm;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        // For stable run on any JRM on any Locale
        Locale newLocale = Locale.ROOT;
        Locale.setDefault(newLocale);

        URL url = Main.class.getClassLoader().getResource("sample.pdf");
        if (url == null) {
            throw new IllegalArgumentException();
        }
        File file = new File(url.toURI());
        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(file.toPath()));

        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);
        //List<String[]> fieldNames = new LinkedList<>();
        List<List<?>> dynamicModelList = new ArrayList<>();

        // REGEX
        String TITLE_WITH_WHITESPACE = "(?<=[a-zA-Z])\s+(?=[a-zA-Z])";
        String DUPLICATE = "\\b(\\w+)\\1\\b";
        String NO_BREAK_SPACE = "\u00a0";

        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);
            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    List<String> tbl = Arrays.asList(TableBuilder.buildTable(table)
                            .toString()
                            .replaceAll(TITLE_WITH_WHITESPACE, "_")
                            .replaceAll(DUPLICATE, "$1")
                            .replaceAll(NO_BREAK_SPACE, " ")
                            .split("\r\n"));
                    //fieldNames.add(tbl.get(0).toLowerCase().split(";"));
                    Class<?> clazz = ClassBuilder.buildClass(tbl.get(0).toLowerCase().split(";"));
                    dynamicModelList.add(new CSVOrm().transform(tbl, clazz));
                }

            }
        }
    }
}