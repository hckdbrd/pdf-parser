package com.knubisoft.parser.builder;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;

import javax.tools.*;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public final class ClassBuilder {

    private static final String CLASS_DECLARATION = "(class)\\s(\\w+)\\s";
    private static final String CODE_PLACEHOLDER = "\\{(\\s*.*\\s*/{2}\\s*.*\\s*)}";

    @SneakyThrows
    public static Class<?> buildClass(String[] fieldNames) {
        File sourceFile = File.createTempFile("Model", ".java");
        sourceFile.deleteOnExit();

        String className = sourceFile.getName().split("\\.")[0];
        codeWrite(fieldNames, className, sourceFile);

        URLClassLoader classLoader = URLClassLoader.
                newInstance(new URL[]{compileSourceFile(sourceFile).toURI().toURL()});

        return classLoader.loadClass(className);
    }

    @SneakyThrows
    private static void codeWrite(String[] fieldNames, String className, File sourceFile){
        String sourceCode = Files.readString(Path.of("src/main/java/com/knubisoft/model/Model.txt"))
                .replaceFirst(CLASS_DECLARATION, String.format("$1 " + className))
                .replaceFirst(CODE_PLACEHOLDER, fieldsOf(fieldNames));

        FileUtils.writeStringToFile(sourceFile, sourceCode, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    private static File compileSourceFile(File sourceFile){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        File parentDirectory = sourceFile.getParentFile();

        StandardJavaFileManager fileManager = compiler
                .getStandardFileManager(null, null, null);
        fileManager
                .setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(parentDirectory));
        Iterable<? extends JavaFileObject> compilationUnits = fileManager
                .getJavaFileObjectsFromFiles(List.of(sourceFile));
        compiler
                .getTask(null,
                        fileManager,
                        null,
                        null,
                        null,
                        compilationUnits).call();
        fileManager
                .close();

        return parentDirectory;
    }

    @SneakyThrows
    private static String fieldsOf(String[] fieldNames) {
        if (fieldNames.length != 0) {
            StringBuilder fields = new StringBuilder();
            String modifier = "public";
            String type = "String";
            String name;

            for (String fieldName : fieldNames) {
                name = fieldName.trim();
                fields
                        .append("\r\n\t")
                        .append(modifier)
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

