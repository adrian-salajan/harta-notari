package example;

import java.util.function.Function;

public interface AddressRules {

    static String rule1(String s) {
        return s.replace("b-dul", "bulevardul");
    }
    static String rule2(String s) {
        return s.replace("bdul", "bulevardul");
    }
}
