package example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import example.NotarCsvNormalized;
import example.NotarCsvRaw;
import example.api.HandlerForAddressNormalization;

public class NotariAddressNormalizer {
    public static final Locale LOCALE = Locale.forLanguageTag("ro-RO");

    private static final Logger logger = LoggerFactory.getLogger(HandlerForAddressNormalization.class);
    static final List<Function<String, String>> normalizationRules = new ArrayList<>();
    static {
        normalizationRules.add(n -> n.replaceFirst("bd\\.", "bulevardul "));
        normalizationRules.add(n -> n.replaceFirst("bdl\\.", "bulevardul "));
        normalizationRules.add(n -> n.replaceFirst("bld\\.?", "bulevardul "));
        normalizationRules.add(n -> n.replaceFirst("b-dul", "bulevardul "));
        normalizationRules.add(n -> n.replaceFirst("bdul\\.?", "bulevardul "));

        normalizationRules.add(n -> n.replaceFirst("str\\.?", "strada "));
        normalizationRules.add(n -> n.replaceFirst("sos\\.", "soseaua "));
        normalizationRules.add(n -> n.replaceFirst("\\u015Fos\\.", "soseaua "));
        normalizationRules.add(n -> n.replaceFirst("sttr\\.?", "strada "));
        normalizationRules.add(n -> n.replaceFirst("cap\\.?", "caporal "));
        normalizationRules.add(n -> n.replaceFirst("g-ral\\.?", "general "));
        normalizationRules.add(n -> n.replaceFirst("gen\\.?", "general "));
        normalizationRules.add(n -> n.replaceFirst("cpt\\.?", "capitan "));
        normalizationRules.add(n -> n.replaceFirst("^\\, ", ""));
        normalizationRules.add(n -> n.replaceAll(" +", " "));
    };

    public Stream<NotarCsvNormalized> normalized(Stream<NotarCsvRaw> notari) {
        var normalized =
                notari
                        .filter(p -> p.getAddress() != null && !p.getAddress().trim().isEmpty())
                        .filter(p -> p.getCity() != null && !p.getCity().trim().isEmpty())
                        .map(this::normalizeNotar);

        return normalized;
    }

    public NotarCsvNormalized normalizeNotar(NotarCsvRaw n) {
        var addressWithoutCity = n.getAddress().toLowerCase().replace(n.getCity().toLowerCase(LOCALE), "");
        var normalizedAddress = normalizeAddress(addressWithoutCity);
        return new NotarCsvNormalized(n.getName(), normalizedAddress, n.getAddress(), n.getCity());
    }


    public String normalizeAddress(String address) {
       return normalizationRules.stream()
               .reduce(Function.identity(), Function::andThen)
               .apply(address.toLowerCase(LOCALE));
    }

}
