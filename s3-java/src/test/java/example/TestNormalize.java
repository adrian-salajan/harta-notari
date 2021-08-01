package example;

import org.junit.jupiter.api.Test;

import example.model.NotariProcessor;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestNormalize {

    // @Disabled
    @Test
    void process() throws IOException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("notari-raw.csv");
        
        System.out.println("parsing");
        var notari = NotariCsvRawParser.parse(is);
        System.out.println("normalising");

        var normalized = new NotariProcessor().process(notari);
        var notariData = new NotariCsvNormalizedWriter().write(normalized);
        var normalizedFile = new File("notari-normalized.csv");

        if (normalizedFile.exists()) {
            normalizedFile.delete();
        }

        assertTrue(normalizedFile.createNewFile(), "file was not created");
        System.out.println("writing to file");
        var fus = new FileOutputStream(normalizedFile);
        fus.write(notariData);
        fus.close();
        System.out.println("done");

        assertTrue(true);
    }
}