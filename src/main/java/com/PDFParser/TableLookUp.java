package com.PDFParser;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableLookUp {

    @SneakyThrows
    public List<List<String>> searchTables(File file) {
        GetTablesFromPdf getTables = new GetTablesFromPdf();
        PdfDocument document = new PdfDocument();
        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);
        document.loadFromBytes(Files.readAllBytes(file.toPath()));


        return getTables.getTablesFromPdf(document, pdfTableExtractor);
    }

}
