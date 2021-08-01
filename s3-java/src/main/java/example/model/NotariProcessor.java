package example.model;

import java.util.stream.Stream;

import example.NotarCsvNormalized;
import example.NotarCsvRaw;

public class NotariProcessor {
    private final NotariFilter filter = new NotariFilter();
    private final NotariAddressNormalizer normalizer = new NotariAddressNormalizer();

    public Stream<NotarCsvNormalized> process(Stream<NotarCsvRaw> notaries) {
        var filtered = filter.filter(notaries);
        var normalized = normalizer.normalized(filtered);
        return normalized;
    }
}
