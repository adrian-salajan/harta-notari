package example;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NotariAddressNormalizer {

    private static final Logger logger = LoggerFactory.getLogger(HandlerForAddressNormalization.class);

    public int process(InputStream csvInputStream) {

        Stream<NotarRecord> notari =
                new CsvToBeanBuilder(new InputStreamReader(csvInputStream))
                .withType(NotarRecord.class)
                .build().stream();

        List<NotarRecord> withAddress =
                notari
                        .filter(p -> p.getAddress() != null && !p.getAddress().trim().isEmpty())
                        .collect(Collectors.toList());

        int size = withAddress.size();
        logger.info("notari size >>>>>" + size);
        return size;
    }
}
