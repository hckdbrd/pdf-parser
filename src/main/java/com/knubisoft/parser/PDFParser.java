package com.knubisoft.parser;

import com.knubisoft.Main;
import com.knubisoft.parser.builder.ClassBuilder;
import com.knubisoft.parser.builder.ListBuilder;
import com.knubisoft.parser.builder.TableBuilder;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public final class PDFParser {

    private static final String TITLE_WITH_WHITESPACE = "(?<=[a-zA-Z])\s+(?=[a-zA-Z])";
    private static final String DUPLICATE = "\\b(\\w+)\\1\\b";
    private static final String NO_BREAK_SPACE = "\u00a0";


    public static List<List<?>> fromPDFToList(PdfDocument document) {
        Locale newLocale = Locale.ROOT;
        Locale.setDefault(newLocale);


        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);
        List<List<?>> dynamicModelList = new ArrayList<>();

        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);
            if (tableLists != null && tableLists.length > 0) {

                for (PdfTable table : tableLists) {
                    List<String> tbl = formatTable(table);
                    Class<?> clazz = ClassBuilder.buildClass(tbl.get(0).toLowerCase().split(";"));
                    dynamicModelList.add(ListBuilder.transform(tbl, clazz));
                }
            }
        }
        return dynamicModelList;
    }

    private static List<String> formatTable(PdfTable table) {
        return Arrays.asList(TableBuilder.buildTable(table)
                .toString()
                .replaceAll(TITLE_WITH_WHITESPACE, "_")
                .replaceAll(DUPLICATE, "$1")
                .replaceAll(NO_BREAK_SPACE, " ")
                .split("\r\n"));
    }

    @SneakyThrows
    public static void getFile(PdfDocument document){
        URL url = Main.class.getClassLoader().getResource("sample.pdf");
        if (url == null) throw new IllegalArgumentException();
        document.loadFromBytes(Files.readAllBytes(Path.of(url.toURI())));
        new File(url.toURI());
    }
}
