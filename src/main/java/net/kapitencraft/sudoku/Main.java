package net.kapitencraft.sudoku;

import net.kapitencraft.sudoku.util.IOHelper;
import net.kapitencraft.sudoku.util.Reader;
import net.kapitencraft.sudoku.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static final String version = "v0.9";
    public static int yield = 10000;

    public static void main(String[] args) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        print("Sudoku " + version + " initiated");
        print("how can I help?");
        String line;
        try {
            do {
                line = bufferedReader.readLine();
                if (line.startsWith("!fill ")) {
                    fill(line.substring(6));
                } else if ("!test".equals(line)) {
                    fill("test/sudoku.txt");
                } else if (line.startsWith("!yield ")) {
                    yield = Integer.parseInt(line.substring(7));
                    print("Set yield to " + yield);
                } else if (line.startsWith("!solve ")) {
                    solve(Utils.readByte(line.substring(7)), bufferedReader);
                } else if (!"!exit".equals(line)){
                    print("I don't understand that command");
                }
            }
            while (!Objects.equals(line, "!exit"));
        } catch (IOException e) {
            print("error reading line: " + e.getMessage());
        }
    }

    private static void fill(String fileName) {
        long time = System.currentTimeMillis();
        try {
            File file = new File(".", fileName);
            IOHelper.print(IOHelper.generate(file), time);
        } catch (Exception e) {
            print("can not fill: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }

    private static void solve(byte size, BufferedReader reader) {
        print("awaiting input...");
        List<String> in = new ArrayList<>();
        while (in.size() < size) {
            try {
                String val = reader.readLine();
                if (val != null && !val.isEmpty()) {
                    in.add(val);
                }
            } catch (IOException e) {
                print("error reading line: " + e.getMessage());
            }
        }
        long time = System.currentTimeMillis();
        IOHelper.print(IOHelper.generate(Reader.read(in, size)), time);
    }

    public static void print(String toPrint) {
        System.out.println("[Sudoku]: " + toPrint);
    }
}