package com.PDFParser;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

public class PDFParser {
    public static final String DELIMITER = "\\|";

    public <T> List<T> readAll(String source, Class<T> cls) {
        Table table = parseToTable(source);
        return convertTableToList(table, cls);
    }

    public Table parseToTable(String source) {
        List<String> lines = Arrays.asList(source.split("\r\n"));
        Map<Integer, String> mapping = buildMapping(lines.get(0));
        Map<Integer, Map<String, String>> result = buildTable(lines.subList(1, lines.size()), mapping);
        return new Table(result);
    }

    private Map<Integer, Map<String, String>> buildTable(List<String> lines, Map<Integer, String> mapping) {
        Map<Integer, Map<String, String>> result = new LinkedHashMap<>();
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            result.put(index, buildRow(mapping, line));
        }
        return result;
    }

    private Map<String, String> buildRow(Map<Integer, String> mapping, String line) {
        Map<String, String> nameToValueMap = new LinkedHashMap<>();
        String[] rowItems = splitLine(line);
        for (int rowIndex = 0; rowIndex < rowItems.length; rowIndex++) {
            String value = rowItems[rowIndex];
            nameToValueMap.put(mapping.get(rowIndex), value);
        }
        return nameToValueMap;
    }

    private Map<Integer, String> buildMapping(String firstLine) {
        Map<Integer, String> map = new LinkedHashMap<>();
        String[] array = splitLine(firstLine);
        for (int index = 0; index < array.length; index++) {
            String value = array[index];
            map.put(index, value.trim());
        }
        return map;
    }

    private static String[] splitLine(String line) {
        return line.split(DELIMITER);
    }

    private <T> List<T> convertTableToList(Table table, Class<T> cls) {
        List<T> resultList = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            Map<String, String> row = table.getTableRowByIndex(i);
            T rowToType = reflectTableRowToClass(row, cls);
            resultList.add(rowToType);
        }
        return resultList;
    }

    @SneakyThrows
    private <T> T reflectTableRowToClass(Map<String, String> row, Class<T> cls) {
        T toType = cls.getDeclaredConstructor().newInstance();
        for (Field field : cls.getDeclaredFields()) {
            field.setAccessible(true);
            String rowValue = row.get(field.getName());
            if (rowValue != null) {
                field.set(toType, transformValueToFieldType(field, rowValue.trim()));
            }
        }
        return toType;
    }

    private Object transformValueToFieldType(Field field, String value) {
        Map<Class<?>, Function<String, Object>> typeToFunction = new LinkedHashMap<>();
        typeToFunction.put(String.class, s -> s);
        typeToFunction.put(int.class, Integer::parseInt);
        typeToFunction.put(Float.class, Float::parseFloat);
        typeToFunction.put(LocalDate.class, s -> LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        typeToFunction.put(Character.class, s -> s.charAt(0));
        typeToFunction.put(Long.class, Long::parseLong);
        return typeToFunction.getOrDefault(field.getType(), type -> {
            throw new UnsupportedOperationException("Type  is not supported" + type);
        }).apply(value);
    }

}
@RequiredArgsConstructor
class Table {
    private final Map<Integer, Map<String, String>> table;

    int size() {
        return table.size();
    }

    Map<String, String> getTableRowByIndex(int row) {
        Map<String, String> tableRow = table.get(row);
        return tableRow == null ? null : new LinkedHashMap<>(tableRow);
    }
}

