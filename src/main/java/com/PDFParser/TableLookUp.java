package com.PDFParser;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableLookUp {
    String DUPLICATE = "\\b(\\w+)\\1\\b";
    String NO_BREAK_SPACE = "\u00a0";

    @SneakyThrows
    public List<List<String>> searchTables(File file) {
        BuildTable buildTable = new BuildTable();
        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(file.toPath()));
        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);
        List<List<String>> tables = new ArrayList<>();
        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);
            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    tables.add(Arrays.asList(buildTable.buildTable(table).toString()
                            .replaceAll("(?<=[a-zA-Z])\s+(?=[a-zA-Z])", "_")
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
