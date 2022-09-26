package com.babakov;

import com.spire.pdf.PdfDocument;

import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        URL url = Main.class.getClassLoader().getResource("sample.pdf");
        File file = new File(url.toURI());

        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(file.toPath()));

        StringBuilder stringBuilder = new StringBuilder();
        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);

        List<String> list = new ArrayList<>();
        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);

            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            String text = table.getText(i, j);
                            if (text.isEmpty())
                                continue;
                            stringBuilder.append(text.replace("\u00a0", " ")).append("|");
                        }
                        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        stringBuilder.append("\r\n");
                    }
                    list.add(stringBuilder.toString());
                    stringBuilder.setLength(0);
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replace(",", "."));
            list.set(i, list.get(i).replace("|", ","));
            FileUtils.writeStringToFile(new File("src/main/resources/sample" + i + ".csv"), list.get(i), StandardCharsets.UTF_8);
        }
    }
}