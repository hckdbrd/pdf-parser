package com.PDFParser;

import com.PDFParser.model.Model;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class WriteToConsole {
    @SneakyThrows
    public void writeValuesFromModel(Model person, String[] str) {
        Class<?> cls = person.getClass();

        StringBuilder values = new StringBuilder();
        for (int i = 0; i <= str.length - 1; i++) {
            Field field = cls.getDeclaredField(str[i]);
            values.append(field.getName()).append(" ");
            field.setAccessible(true);
            values.append(field.get(person)).append(" ");
        }

        System.out.println(values);
    }
}
