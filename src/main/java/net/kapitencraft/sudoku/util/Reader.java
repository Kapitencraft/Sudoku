package net.kapitencraft.sudoku.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    public static byte[][] readArray(FileReader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        List<String> list = bufferedReader.lines().toList();
        reader.close();
        return read(list, (byte) -1);
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
            collectBracketContent(string, '[', ']').stream().map(Utils::readByte).forEach(bytes::add);
        } else {
            for (int i = 0; i < string.length(); i+=numSize) {
                bytes.add(Utils.readByte(string.substring(i, i + numSize)));
            }
        }
        return bytes;
    }

    public static List<String> collectBracketContent(String in, char openBracket, char closeBracket) {
        List<String> strings = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        boolean adding = false;
        for (int i = 0; i < in.length(); i++) {
            char c = in.charAt(i);
            if (c == openBracket) {
                if (adding) throw new IllegalStateException("detected missing closing ]");
                adding = true;
            } else if (c == closeBracket) {
                if (adding) {
                    strings.add(builder.toString());
                    builder = new StringBuilder();
                    adding = false;
                }
            } else if (adding) {
                builder.append(in.charAt(i));
            }
        }
        return strings;
    }
}