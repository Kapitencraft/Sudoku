package net.kapitencraft.sudoku.elements;

import net.kapitencraft.sudoku.elements.data.DataLine;
import net.kapitencraft.sudoku.SudokuField;

public class Column extends DataLine {

    public Column(SudokuField field) {
        super(field);
    }

    @Override
    public byte getIdentifier() {
        return this.cells.get(0).getX();
    }

    @Override
    public String toString() {
        return "Column: " + getIdentifier();
    }
}