package example.model;

import java.util.Locale;
import java.util.stream.Stream;

import example.NotarCsvRaw;

public class NotariFilter {

    public static final Locale LOCALE = Locale.forLanguageTag("ro-RO");


    public Stream<NotarCsvRaw> filter(Stream<NotarCsvRaw> notari) {
        var filtered =
                notari
                        .filter(p -> p.getAddress() != null && !p.getAddress().trim().isEmpty())
                        .filter(p -> p.getCity() != null && !p.getCity().trim().isEmpty())
                        .filter(p ->! p.getAddress().toLowerCase(LOCALE).contains("f\u0103r\u0103"))
                        .filter(p ->! p.getAddress().contains("F\u0102R\u0102"));

        return filtered;
    }


}
