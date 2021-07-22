package example;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class NotariCsvRawParser {

    public static Stream<NotarCsvRaw> parse(InputStream csvInputStream) {

        return new CsvToBeanBuilder(new InputStreamReader(csvInputStream))
                .withType(NotarCsvRaw.class)
                .build().stream();


    }


}
