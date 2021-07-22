package example;

import java.io.InputStream;

public class InputData {
    final InputStream is;
    final long size;

    public InputData(InputStream is, long size) {
        this.is = is;
        this.size = size;
    }
}
