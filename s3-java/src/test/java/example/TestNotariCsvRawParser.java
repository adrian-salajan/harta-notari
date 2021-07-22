package example;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class TestNotariCsvRawParser {

    @Test
    void process() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("notari-raw.csv");
        long size = NotariCsvRawParser.parse(is).count();

        assertTrue(size > 0);
        System.out.println(size);
    }
}