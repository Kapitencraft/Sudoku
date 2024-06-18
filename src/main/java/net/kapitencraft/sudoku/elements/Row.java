package net.kapitencraft.sudoku.elements;

import net.kapitencraft.sudoku.elements.data.DataLine;
import net.kapitencraft.sudoku.elements.data.DataList;
import net.kapitencraft.sudoku.SudokuField;

public class Row extends DataLine {

    public Row(SudokuField field) {
        super(field);
    }

    @Override
    public byte getIdentifier() {
        return cells.get(0).getY();
    }

    @Override
    public String toString() {
        return "Row: " + getIdentifier();
    }
}
