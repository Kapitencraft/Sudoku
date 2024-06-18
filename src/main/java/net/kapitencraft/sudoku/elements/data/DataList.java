package net.kapitencraft.sudoku.elements.data;

import net.kapitencraft.sudoku.SudokuField;
import net.kapitencraft.sudoku.elements.Cell;

import java.util.ArrayList;
import java.util.List;

public abstract class DataList extends DataContainer {
    public final List<Cell> cells = new ArrayList<>();

    public DataList(SudokuField field) {
        super(field);
    }

    public void accordChange(byte in) {
        this.addValue(in);
        this.cells.forEach(cell -> cell.addValue(in));
    }

    protected List<Cell> possible(byte num) {
        List<Cell> cells = new ArrayList<>();
        for (Cell cell : this.cells) {
            if (cell.isNum(num)) return List.of();
            else if (!cell.filled() && !cell.hasValue(num)) cells.add(cell);
        }
        return cells;
    }

    /**
     * used to check whether there's only one remaining spot for the numbers to go, inserting into the target cell
     */
    public void checkRemaining() {
        for (byte b = 1; b < this.field.size(); b++) {
            this.check(b);
        }
    }

    protected void check(byte num) {
        List<Cell> possibles = possible(num);
        if (possibles.size() == 1) {
            possibles.get(0).insert(num);
        }
    }

    public void accordChangeWithout(byte num, List<Cell> cells) {
        this.cells.removeAll(cells);
        accordChange(num);
        this.cells.addAll(cells);
    }
}
