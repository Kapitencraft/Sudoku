package net.kapitencraft.sudoku.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Reader {
    public static byte[][] readArray(FileReader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        reader.close();
        return read(bufferedReader.lines().toList(), (byte) -1);
    }

    public static byte[][] read(List<String> list, byte size) {
        List<List<Byte>> bytesArray = new ArrayList<>();
        byte numSize = size == -1 ? -1 : (byte) Math.ceil(Math.log10(size));
        list.stream().map(string -> readLine(numSize, string)).forEach(bytesArray::add);
        List<byte[]> array = new ArrayList<>();
        bytesArray.forEach(bytes -> array.add(Utils.toPrimitive(bytes.toArray(Byte[]::new))));
        return array.toArray(byte[][]::new);
    }

    private static List<Byte> readLine(byte numSize, String string) {
        List<Byte> bytes = new ArrayList<>();
        if (numSize == -1) {
            StringBuilder builder = new StringBuilder();
            boolean adding = false;
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                if (c == '[') {
                    if (adding) throw new IllegalStateException("detected missing closing ]");
                    adding = true;
                } else if (c == ']') {
                    if (adding) {
                        bytes.add(Utils.readByte(builder.toString()));
                        builder = new StringBuilder();
                        adding = false;
                    }
                } else if (adding) {
                    builder.append(string.charAt(i));
                }
            }
        } else {
            for (int i = 0; i < string.length(); i+=numSize) {
                bytes.add(Utils.readByte(string.substring(i, i + numSize)));
            }
        }
        return bytes;
    }
}
