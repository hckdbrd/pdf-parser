package com.pdfParser;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import java.io.IOException;

public class SimpleTextExtractor {
    public static void main(String[] args) throws IOException {
        PdfReader pdfReader = new PdfReader("sample.pdf");

//SimpleTextExtractionStrategy - парсимо весь файл з першої пдф сторінки до останньої
        for (int i = 1; i < pdfReader.getNumberOfPages(); i++) {
            TextExtractionStrategy textExtractionStrategy = new SimpleTextExtractionStrategy();
            String text = PdfTextExtractor.getTextFromPage(pdfReader, i, textExtractionStrategy);
            System.out.println(text);

        }
        pdfReader.close();


    }
}
