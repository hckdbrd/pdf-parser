package src.main.java;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;

import lombok.SneakyThrows;
import model.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ParseToModel {
    private final PdfDocument document;
    StringBuilder stringBuilder = new StringBuilder();


    public ParseToModel(PdfDocument document) {
        this.document = document;
    }

    public List<RowStructure> getTable() {
        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);
        List<RowStructure> rowStructures = new ArrayList<>();
        RowStructure r;
        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);

            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    r = getModel(table);
                    for (int i = 0; i < table.getRowCount(); i++) {
                        for (int j = 0; j < table.getColumnCount(); j++) {
                            stringBuilder = getString(table.getText(i, j));
                        }
                        addDataToList(rowStructures, r);
                    }
                }
            }
        }
        return rowStructures;
    }

    private void addDataToList(List<RowStructure> rowStructures, RowStructure r) {
        if (stringBuilder.length() != 0) {
            rowStructures.add(getColumn(stringBuilder, r));
        }
        stringBuilder.delete(0, stringBuilder.length());
    }

    private StringBuilder getString(String text) {
        if (text.length() != 0) {
            return stringBuilder.append(text.replaceAll(",", ".") + ",");
        } else {
            return stringBuilder;
        }

    }


    private static RowStructure getModel(PdfTable text) {
        if (text.getText(0, 0).equals("CategoryCategory")) {
            return new RowStructure1();
        } else if
        (text.getText(0, 0).equals("Category")) {
            return new RowStructure2();
        } else if
        (text.getText(0, 0).equals("Date")) {
            return new RowStructure3();
        } else if
        (text.getText(0, 0).equals("Amount")) {
            return new RowStructure4();
        }

        return new RowStructure1();
    }


    @SneakyThrows
    public static RowStructure getColumn(StringBuilder str, RowStructure rowStructure) {
        String[] s = String.valueOf(str).split(",");
        Field[] fields = rowStructure.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            fields[i].set(rowStructure, s[i]);
        }
        return (RowStructure) rowStructure.getClass().getDeclaredConstructors()[0].newInstance(rowStructure);
    }
}
