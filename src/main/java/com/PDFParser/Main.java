package com.PDFParser;

import com.CSVOrm.CSVOrm;
import com.PDFParser.model.DynamicModel;
import com.PDFParser.model.Model;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {

        // For stable run on any JRM on any Locale
        Locale newLocale = Locale.ROOT;
        Locale.setDefault(newLocale);

        URL url = Main.class.getClassLoader().getResource("sample.pdf");
        assert url != null;
        File file = new File(url.toURI());
        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(file.toPath()));

        PdfTableExtractor pdfTableExtractor = new PdfTableExtractor(document);
        List<List<String>> tables = new ArrayList<>(); // need to took from here
//        List<List<Model>> modelList = new ArrayList<>();
        List<String[]> fieldNames = new LinkedList<>();
        List<List<?>> dynamicModelList = new ArrayList<>(); // future list

        // REGEX
        String TITLE_WITH_WHITESPACE = "(?<=[a-zA-Z])\s+(?=[a-zA-Z])";
        String DUPLICATE = "\\b(\\w+)\\1\\b";
        String NO_BREAK_SPACE = "\u00a0";

        for (int pageIndex = 0; pageIndex < document.getPages().getCount(); pageIndex++) {
            PdfTable[] tableLists = pdfTableExtractor.extractTable(pageIndex);
            if (tableLists != null && tableLists.length > 0) {
                for (PdfTable table : tableLists) {
                    tables.add(Arrays.asList(buildTable(table)
                            .toString()
                            .replaceAll(TITLE_WITH_WHITESPACE, "_")
                            .replaceAll(DUPLICATE, "$1")
                            .split("\r\n")));
//                    modelList.add(new PDFParser()
//                            .readAll(buildTable(table)
//                                    .toString()
//                                    .replaceAll(TITLE_WITH_WHITESPACE, "_")
//                                    .replaceAll(DUPLICATE, "$1")
//                                    .replaceAll(NO_BREAK_SPACE, ""), Model.class));
                }
                fieldNames.add(tables.get(pageIndex).get(0).split(" ; "));
                Class<?> cls = buildModel(fieldNames.get(pageIndex), pageIndex + 1);
                dynamicModelList.add(new CSVOrm().transform(tables.get(pageIndex).subList(1,tables.get(pageIndex).size()),cls));

            }

        }

    }

    // -------------------------- TABLE BUILDING -------------------------- //

    private static StringBuilder buildTable(PdfTable table) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < table.getRowCount(); i++) {
            buildRow(stringBuilder, table, i);
            stringBuilder.append("\r\n");
        }
        return stringBuilder;
    }

    private static void buildRow(StringBuilder stringBuilder, PdfTable table, int i) {
        List<String> row = new ArrayList<>();
        for (int j = 0; j < table.getColumnCount(); j++) {
            String text = table.getText(i, j);
            if (text != null && text.trim().length() > 1) {
                row.add(text);
            }
        }
        stringBuilder.append(String.join(" ; ", row));
    }

    // -------------------------- MODEL BUILDING -------------------------- //
    @SneakyThrows
    private static Class<?> buildModel(String[] fieldNames, int version) {

        // Constants:
        Path samplePath = Paths.get("src/main/java/com/PDFParser/model/DynamicModel.java");
        Path generatedModelPath = Paths.get(
                String.format("src/main/java/com/PDFParser/model/DynamicModel_v%d.java", version)
        );
        String CODE = "\\{(\\s*.*\\s*/{2}\\s*.*\\s*)}";
        String CLASS = "(class)\\s(\\w+)\\s(\\{)";

        // Fields building process:
        String modelFields = buildFields(fieldNames);

        // Model building process:
        try {
            if (Files.exists(samplePath)) {

                // Content preparation before insertion:
                String code = Files.readString(samplePath);
                code = code.replaceFirst(CLASS, String.format("$1 $2_v%d extends $2 $3", version));
                code = code.replaceFirst(CODE, modelFields);

                // New Model creation:
                Files.createFile(generatedModelPath);
                Files.writeString(generatedModelPath, code);
                Class<?> clazz = Class.forName(generatedModelPath.getFileName().toString());
                return clazz;
            } else {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String buildFields(String[] fieldNames) {
        if (fieldNames.length != 0) {

            StringBuilder fields = new StringBuilder();
            String modificator = "private";
            String type = "String";
            String name;

            for (String fieldName : fieldNames) {
                name = fieldName;
                fields
                        .append("\r\n\t")
                        .append(modificator)
                        .append(" ")
                        .append(type)
                        .append(" ")
                        .append(name)
                        .append(";");
            }

            fields = new StringBuilder(new StringBuilder("{ " + fields + "\n}"));
            return fields.toString();

        } else {
            throw new IllegalArgumentException("Must be at least 1 field name");
        }
    }
}