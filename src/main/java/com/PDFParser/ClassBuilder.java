package com.PDFParser;

import lombok.SneakyThrows;

import javax.tools.*;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class ClassBuilder {

    @SneakyThrows
    public static Class<?> buildClass(String[] fieldNames) {
        String CLASS_DECLARATION = "(class)\\s(\\w+)\\s";
        String CODE_PLACEHOLDER = "\\{(\\s*.*\\s*/{2}\\s*.*\\s*)}";

        // create an empty source file
        File sourceFile = File.createTempFile("Model", ".java");
        sourceFile.deleteOnExit();

        // generate the source code, using the source filename as the class name
        String classname = sourceFile.getName().split("\\.")[0];
        String sourceCode = Files.readString(Path.of("src/main/java/com/PDFParser/model/Model.txt"))
                .replaceFirst(CLASS_DECLARATION, String.format("$1 " + classname))
                .replaceFirst(CODE_PLACEHOLDER, fieldsOf(fieldNames));

        // write the source code into the source file
        FileWriter writer = new FileWriter(sourceFile);
        writer.write(sourceCode);
        writer.close();

        // compile the source file
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        File parentDirectory = sourceFile.getParentFile();
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(parentDirectory));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(List.of(sourceFile));
        compiler.getTask(null, fileManager, null, null, null, compilationUnits).call();
        fileManager.close();

        // load the compiled class
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{parentDirectory.toURI().toURL()});
        return classLoader.loadClass(classname);
    }

    @SneakyThrows
    private static String fieldsOf(String[] fieldNames) {
        if (fieldNames.length != 0) {
            StringBuilder fields = new StringBuilder();
            String modificator = "public";
            String type = "String";
            String name;
            for (String fieldName : fieldNames) {
                name = fieldName.trim();
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

