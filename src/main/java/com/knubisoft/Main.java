package com.knubisoft;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import static com.knubisoft.parser.PDFParser.fromPDFToList;

public class Main {

    public static void main(String[] args) throws IOException{
        Locale newLocale = Locale.ROOT;
        Locale.setDefault(newLocale);

        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(getFile().toPath()));

        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);

        List<List<?>> result = fromPDFToList(pdfTableExtractor, document);
    }

    @SneakyThrows
    private static File getFile(){
        URL url = Main.class.getClassLoader().getResource("sample.pdf");

        if (url == null) {
            throw new IllegalArgumentException();
        }

        return new File(url.toURI());
    }
}