package example;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import example.model.NotariAddressNormalizer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NotariAddressNormalizerTest {

    NotariAddressNormalizer normalizer = new NotariAddressNormalizer();

    @Test
    void toLowercase() {
        assertEquals("\u00E2\u00E2", "\u00C2\u00C2".toLowerCase(NotariAddressNormalizer.LOCALE));
    }

    @Disabled
    @Test
    void normalizedAddressSpecialChars() {
        assertEquals("șșșș țțțăăăă ââââîî îî", normalizer.normalizeAddress("șșșș țțțăăăă ââââîî îî"));

    }

    @Test
    void normalized() {
        assertEquals("soseaua mihai bravu", normalizer.normalizeAddress("ŞOS. MIHAI BRAVU"));

    }

    @Test
    void removeFirstComma() {
        assertEquals("sector 1, ", normalizer.normalizeAddress(", sector 1, "));

    }

    @Test
    void normalizedNotary() {
        assertEquals(
                new NotarCsvNormalized("adrian", "bulevardul x", "b-dul x", "city"),
                normalizer.normalizeNotar(new NotarCsvRaw("adrian", "b-dul x", "city")));
    }

    @Disabled
    @Test
    void normalizedNotarySpecialChars() {
        assertEquals(
                new NotarCsvNormalized("șșșș", "țțțăăăă,",  "țțțăăăă", "ââââîîîî"),
                normalizer.normalizeNotar(new NotarCsvRaw("șșșș", "țțțăăăă", "ââââîîîî")));
    }

    @Test
    void normalizedNotaryStream() {
        List<NotarCsvRaw> notari = new ArrayList<NotarCsvRaw>();
        notari.add(new NotarCsvRaw("a", "Bdul x", "oras1"));
        notari.add(new NotarCsvRaw("b", "B-dul y", "oras1"));
        notari.add(new NotarCsvRaw("c", "adresa x,oras1", "oras1"));
        notari.add(new NotarCsvRaw("\u00C2\u00C2", "\u00C2\u00C2", "city"));

        var normalized = normalizer.normalized(notari.stream());


        var expectedNotari = new ArrayList<NotarCsvNormalized>();
        expectedNotari.add(new NotarCsvNormalized("a", "bulevardul x", "Bdul x", "oras1"));
        expectedNotari.add(new NotarCsvNormalized("b", "bulevardul y","B-dul y", "oras1"));
        expectedNotari.add(new NotarCsvNormalized("c", "adresa x,",  "adresa x,oras1", "oras1"));
        expectedNotari.add(new NotarCsvNormalized("\u00C2\u00C2", "\u00E2\u00E2",  "\u00C2\u00C2", "city"));

        assertEquals(expectedNotari, normalized);
    }
}