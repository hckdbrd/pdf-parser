package com.knubisoft.parser;

import com.knubisoft.parser.builder.ClassBuilder;
import com.knubisoft.parser.builder.ListBuilder;
import com.knubisoft.parser.builder.TableBuilder;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PDFParser {

    private static final String TITLE_WITH_WHITESPACE = "(?<=[a-zA-Z])\s+(?=[a-zA-Z])";
    private static final String DUPLICATE = "\\b(\\w+)\\1\\b";
    private static final String NO_BREAK_SPACE = "\u00a0";

    public static List<List<?>> fromPDFToList(PdfDocument document) {
        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);
        Locale newLocale = Locale.ROOT;
        Locale.setDefault(newLocale);
        List<List<?>> dynamicModelList = new ArrayList<>();

        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);
            if (tableLists != null && tableLists.length > 0) {

                for (PdfTable table : tableLists) {
                    List<String> tbl = formatTable(table);
                    Class<?> clazz = ClassBuilder.buildClass(tbl.get(0).toLowerCase().split(";"));
                    dynamicModelList.add(new ListBuilder().transform(tbl, clazz));
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
}
