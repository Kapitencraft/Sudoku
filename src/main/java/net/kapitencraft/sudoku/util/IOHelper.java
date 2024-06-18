package net.kapitencraft.sudoku.util;

import net.kapitencraft.sudoku.Main;
import net.kapitencraft.sudoku.SudokuField;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class IOHelper {

    public static SudokuField generate(File file) {
        try {
            byte[][] content = Reader.readArray(new FileReader(file));
            return generate(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SudokuField generate(byte[][] content) {
        if (content.length != content[0].length) throw new IllegalArgumentException("content must be squared");
        SudokuField field = new SudokuField((byte) Math.sqrt(content.length));
        field.applyValues(content);
        field.generate();
        return field;
    }

    public static void print(SudokuField generated, long startTime) {
        Main.print("finished processing (took " + (System.currentTimeMillis() - startTime) + " millis): ");
        byte[][] content = generated.getContent();
        for (byte[] row : content) {
            System.out.print("\t\t");
            for (byte b : row) {
                System.out.print("[" + b + "]");
            }
            System.out.print("\n");
        }
    }
}
