package net.kapitencraft.sudoku.util;

import java.util.function.Function;
import java.util.function.Supplier;

public class Utils {

    public static <T> void fillArray(T[] array, Supplier<T> constructor) {
        fillArray(array, integer -> constructor.get());
    }

    public static <T> void fillArray(T[] array, Function<Integer, T> mapper) {
        for (int i = 0; i < array.length; i++) {
            array[i] = mapper.apply(i);
        }
    }

    public static byte[] toPrimitive(Byte[] bytes) {
        byte[] bytes1 = new byte[bytes.length];
        for (int i = 0; i< bytes1.length; i++) {
            bytes1[i] = bytes[i];
        }
        return bytes1;
    }

    public static byte readByte(String in) {
        if (in == null || in.isEmpty()) return -1;
        else return Byte.parseByte(in);
    }
}
