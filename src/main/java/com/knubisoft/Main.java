package com.knubisoft;

import com.knubisoft.parser.PDFParser;
import com.spire.pdf.PdfDocument;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException{
        PdfDocument document = new PdfDocument();
        PDFParser.getFile(document);
        List<List<?>> result = PDFParser.convertToList(document);
    }
}