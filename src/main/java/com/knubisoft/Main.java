package com.knubisoft;

import com.knubisoft.parser.PDFParser;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.knubisoft.parser.PDFParser.fromPDFToList;

public class Main {


    public static void main(String[] args) throws IOException{
        PdfDocument document = new PdfDocument();
        PDFParser.getFile(document);
        List<List<?>> result = fromPDFToList(document);
    }
}