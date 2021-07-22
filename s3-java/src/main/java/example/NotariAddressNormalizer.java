package example;

import example.api.HandlerForAddressNormalization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NotariAddressNormalizer {
    public static final Locale LOCALE = Locale.forLanguageTag("ro-RO");

    private static final Logger logger = LoggerFactory.getLogger(HandlerForAddressNormalization.class);
    static final List<Function<String, String>> normalizationRules = new ArrayList<>();
    static {
        normalizationRules.add(n -> n.replaceFirst("bd\b.?", "bulevardul "));
        normalizationRules.add(n -> n.replaceFirst("bdl\\.?", "bulevardul "));
        normalizationRules.add(n -> n.replaceFirst("b-dul", "bulevardul "));
        normalizationRules.add(n -> n.replaceFirst("bdul\\.?", "bulevardul "));

        normalizationRules.add(n -> n.replaceFirst("str\\.?", "strada "));
        normalizationRules.add(n -> n.replaceFirst("\\u015Fos\\.?", "soseaua "));
        normalizationRules.add(n -> n.replaceFirst("sos\\.?", "soseaua "));
        normalizationRules.add(n -> n.replaceFirst("sttr\\.?", "strada "));
        normalizationRules.add(n -> n.replaceFirst("cap\\.?", "caporal "));
        normalizationRules.add(n -> n.replaceAll(" +", " "));
    };

    public List<NotarCsvNormalized> normalized(Stream<NotarCsvRaw> notari) {
        var normalized =
                notari
//                        .parallel()
                        .filter(p -> p.getAddress() != null && !p.getAddress().trim().isEmpty())
                        .filter(p -> p.getCity() != null && !p.getCity().trim().isEmpty())
                        .map(this::normalizeNotar)
                        .collect(Collectors.toList());

        var size = normalized.size();
        logger.info("Normalized notari count:" + size);
        return normalized;
    }

    public NotarCsvNormalized normalizeNotar(NotarCsvRaw n) {
        var normalizedAddress = normalizeAddress(n.getAddress());
        var normalizedWithoutCity = normalizedAddress.replace(n.getCity().toLowerCase(LOCALE), "");
        return new NotarCsvNormalized(n.getName(), normalizedWithoutCity, n.getAddress(), n.getCity());
    }


    public String normalizeAddress(String address) {
       return normalizationRules.stream()
               .reduce(Function.identity(), Function::andThen)
               .apply(address.toLowerCase(LOCALE));
    }

}
