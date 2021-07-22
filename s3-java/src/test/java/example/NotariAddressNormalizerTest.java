package example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotariAddressNormalizerTest {

    NotariAddressNormalizer normalizer = new NotariAddressNormalizer();

    @Test
    void normalized() {
        List<NotarCsvRaw> notari = new ArrayList<NotarCsvRaw>();
        notari.add(new NotarCsvRaw("a", "Bdul x", "oras1"));
        notari.add(new NotarCsvRaw("b", "B-dul y", "oras1"));
        notari.add(new NotarCsvRaw("c", "adresa x,oras1", "oras1"));

        var normalized = normalizer.normalized(notari.stream());

        var expectedNotari = new ArrayList<NotarCsvNormalized>();
        expectedNotari.add(new NotarCsvNormalized("a", "bulevardul x", "Bdul x", "oras1"));
        expectedNotari.add(new NotarCsvNormalized("b", "bulevardul y","B-dul y", "oras1"));
        expectedNotari.add(new NotarCsvNormalized("c", "adresa x,",  "adresa x,oras1", "oras1"));

        assertEquals(expectedNotari, normalized);
    }
}