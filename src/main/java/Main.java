import com.spire.pdf.PdfDocument;
import com.spire.pdf.utilities.PdfTable;
import com.spire.pdf.utilities.PdfTableExtractor;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Files;

public class Main {


    // ввести номер страници с которой хочешь стянуть талицу
    public static final int PAGE = 1;
    //путь к файлу
    public static final String NAME = "sample.pdf";
    // выходящий файл
    public static final String OUTPUT = "output.txt";


    @SneakyThrows
    public static void main(String[] args) {
        URL url = Main.class.getClassLoader().getResource(NAME);
        File file = new File(url.toURI());

        PdfDocument pdf = new PdfDocument();
        pdf.loadFromBytes(Files.readAllBytes(file.toPath()));

        PdfTableExtractor extractor = new PdfTableExtractor(pdf);


        PdfTable[] pdfTables = extractor.extractTable(PAGE);
        PdfTable table = pdfTables[0];

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                String text = table.getText(i, j);
                builder.append(text.replace("\u00a0","") + " ");
            }
            builder.append("\r\n");
        }
        //тут можно сразу конертить в объект, но временно в файл
        FileWriter fw = new FileWriter(OUTPUT);
        fw.write(builder.toString());
        fw.flush();
        fw.close();
    }

}

