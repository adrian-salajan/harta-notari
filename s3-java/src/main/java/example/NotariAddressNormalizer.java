package example;

import example.api.HandlerForAddressNormalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NotariAddressNormalizer {
    private static final Logger logger = LoggerFactory.getLogger(HandlerForAddressNormalization.class);
    static final List<Function<String, String>> normalizationRules = new ArrayList<>();
    static {
        normalizationRules.add(n -> n.replace("b-dul", "bulevardul"));
        normalizationRules.add(n -> n.replace("bdul", "bulevardul"));
    };

    public List<NotarCsvNormalized> normalized(Stream<NotarCsvRaw> notari) {
        var normalized =
                notari
                        .parallel()
                        .filter(p -> p.getAddress() != null && !p.getAddress().trim().isEmpty())
                        .filter(p -> p.getCity() != null && !p.getCity().trim().isEmpty())
                        .map(this::normalizeNotar)
                        .collect(Collectors.toList());

        var size = normalized.size();
        logger.info("Normalized notari count:" + size);
        return normalized;
    }

    private NotarCsvNormalized normalizeNotar(NotarCsvRaw n) {
        var normalizedAddress = normalizeAddress(n.getAddress().toLowerCase());
        var normalizedWithoutCity = normalizedAddress.replace(n.getCity().toLowerCase(), "");
        return new NotarCsvNormalized(n.getName(), normalizedWithoutCity, n.getAddress(), n.getCity());
    }


    private String normalizeAddress(String address) {
       return normalizationRules.stream()
               .reduce(Function.identity(), Function::andThen)
               .apply(address);
    }

}
