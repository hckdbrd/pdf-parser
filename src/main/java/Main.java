package src.main.java;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;
import model.*;
import src.main.java.model.RowStructure;

import java.io.File;
import java.net.URL;
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

        List<src.main.java.model.RowStructure> rowStructures = new ArrayList<>();
        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);

            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            String text = table.getText(i, j);
                            if (text.length() == 0) {
                                continue;
                            }
                            stringBuilder.append(text + ",");

                        }
                        if (stringBuilder.length() != 0) {
                            rowStructures.add(getColumn(stringBuilder, pageIndex));
                        }
                        stringBuilder.delete(0, stringBuilder.length());
                    }
                }
            }
        }
        for (RowStructure rowStructure:rowStructures){
            System.out.println(rowStructure);
        }
    }

    public static RowStructure getColumn(StringBuilder str, int page) {
        String[] s = String.valueOf(str).split(",");
        if (page == 0) {
            return new RowStructure1(s[0], s[1], s[2]);
        } else if (page == 1) {
            return new RowStructure2(s[0], s[1]);
        } else if (page == 2) {
            return new RowStructure3(s[0], s[1], s[2]);
        } else {
            return new RowStructure4(s[0]);
        }
    }
}
