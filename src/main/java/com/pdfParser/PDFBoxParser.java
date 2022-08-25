package com.pdfParser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

import static org.apache.pdfbox.pdfparser.PDFParser.load;

public class PDFBoxParser {

    public static void main(String[] args) throws IOException {

        //Loading an existing document
        File file = new File("/Users/oleksii/Documents/GitHub/pdf-parser/src/main/resources/sample.pdf");
        PDDocument document = load(file);

        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        System.out.println(text);

        //Closing the document
        document.close();

    }
}

