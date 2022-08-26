package com.PDFParser;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetTablesFromPdf {
    private static final String DUPLICATE = "\\b(\\w+)\\1\\b";
    private static final String NO_BREAK_SPACE = "\u00a0";

    public List<List<String>> getTablesFromPdf(PdfDocument document, PdfTableExtractor pdfTableExtractor) {
        BuildTable buildTable = new BuildTable();
        List<List<String>> tables = new ArrayList<>();

        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);
            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    tables.add(Arrays.asList(buildTable.buildTable(table).toString()
                            .replaceAll(DUPLICATE, "$1")
                            .replaceAll(NO_BREAK_SPACE, "")
                            .replaceAll("Diﬀerence", "Difference")
                            .split("\r\n")));

                }
            }

        }

        return tables;
    }
}
