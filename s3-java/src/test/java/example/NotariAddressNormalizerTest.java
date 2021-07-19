package example;

import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class NotariAddressNormalizerTest {

    @Test
    void process() {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("notari-raw.csv");
        int size = new NotariAddressNormalizer().process(is);

        assertTrue(size > 0);
        System.out.println(size);
    }
}