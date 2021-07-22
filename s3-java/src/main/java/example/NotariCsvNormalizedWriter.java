package example;

import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.*;
import java.util.stream.Stream;

public class NotariCsvNormalizedWriter {

    public byte[] write(Stream<NotarCsvNormalized> notari) throws IOException {
        var os = new ByteArrayOutputStream();
        try {
            var writer = new BufferedWriter(new OutputStreamWriter(os));
            var beanToCsv = new StatefulBeanToCsvBuilder<NotarCsvNormalized>(writer).build();
            beanToCsv.write(notari);
            writer.close();
        } catch (Exception e) {
            throw new IOException("Could not write to csv", e);
        }
        return os.toByteArray();
    }

}
