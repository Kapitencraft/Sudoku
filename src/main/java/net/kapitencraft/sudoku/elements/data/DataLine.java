package net.kapitencraft.sudoku.elements.data;

import net.kapitencraft.sudoku.SudokuField;
import net.kapitencraft.sudoku.elements.Cell;
import net.kapitencraft.sudoku.elements.Row;
import org.jetbrains.annotations.Contract;

public abstract class DataLine extends DataList {
    public DataLine(SudokuField field) {
        super(field);
    }

    @Contract("!null -> this")
    public DataLine addElement(Cell cell) {
        this.cells.add(cell);
        return this;
    }

    public abstract byte getIdentifier();
}
