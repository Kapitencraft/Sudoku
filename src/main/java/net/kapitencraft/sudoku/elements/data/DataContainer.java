package net.kapitencraft.sudoku.elements.data;

import net.kapitencraft.sudoku.SudokuField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataContainer {
    protected final boolean[] data;
    protected final SudokuField field;

    public DataContainer(SudokuField field) {
        this.field = field;
        this.data = new boolean[field.size()];
        Arrays.fill(data, false);
    }

    public boolean hasValue(byte id) {
        return data[id - 1];
    }

    public void addValue(byte id) {
        data[id - 1] = true;
    }

    public boolean isComplete() {
        for (byte i = 1; i <= field.size(); i++) {
            if (!hasValue(i)) return false;
        }
        return true;
    }

    public void removeOther(List<Byte> list) {
        for (byte b = 1; b == data.length; b++) {
            if (!list.contains(b)) {
                addValue(b);
            }
        }
    }

    protected List<Byte> remaining() {
        List<Byte> possibles = new ArrayList<>();
        for (byte b = 1; b <= this.field.size(); b++) {
            if (!this.hasValue(b)) {
                possibles.add(b);
            }
        }
        return possibles;
    }
}