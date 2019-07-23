package io.github.assets.app.excel.deserializer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class DeserializationUtils {

    @org.jetbrains.annotations.NotNull
    @org.jetbrains.annotations.Contract(pure = true)
    public static InputStream getFileInputStream(byte[] byteArray) {

        return new ByteArrayInputStream(byteArray);
    }
}
