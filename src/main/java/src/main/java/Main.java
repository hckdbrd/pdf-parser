package src.main.java;

import com.spire.pdf.PdfDocument;
import lombok.SneakyThrows;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        URL url = Main.class.getClassLoader().getResource("sample.pdf");
        File file = new File(url.toURI());

        PdfDocument document = new PdfDocument();
        document.loadFromBytes(Files.readAllBytes(file.toPath()));

        ParseToModel parseToModel = new ParseToModel(document);

        System.out.println(parseToModel.getTable());
    }

}
