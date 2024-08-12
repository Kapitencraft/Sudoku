package net.kapitencraft.sudoku.elements;

import net.kapitencraft.sudoku.elements.data.DataList;
import net.kapitencraft.sudoku.SudokuField;
import net.kapitencraft.sudoku.elements.fill_helper.GroupLine;
import net.kapitencraft.sudoku.util.Vec2;

import java.util.ArrayList;
import java.util.List;

public class Group extends DataList {
    private final Vec2 start, end;
    private final List<GroupLine> lines = new ArrayList<>();

    public Group(byte xIndex, byte yIndex, byte size, SudokuField field) {
        super(field);
        this.start = new Vec2((byte) (xIndex * size), (byte) (yIndex * size));
        this.end = new Vec2((byte) ((xIndex + 1) * size - 1), (byte) ((yIndex + 1) * size - 1));
        for (byte x = start.getY(); x <= end.getY(); x++) {
            for (byte y = start.getX(); y <= end.getX(); y++) {
                cells.add(new Cell(new Vec2(x, y), this, field));
            }
        }
    }

    @Override
    public String toString() {
        return "Group{start=" + start + ", end=" + end + "}";
    }

    @Override
    protected void check(byte num) {
        super.check(num);
        List<Cell> possible = possible(num);
        if (possible.size() <= 3 && possible.size() > 1) {
            byte column = possible.get(0).getX();
            byte row = possible.get(0).getY();
            boolean columnGroup = lines.stream().anyMatch(line -> line.matches(column, num));
            boolean rowGroup = lines.stream().anyMatch(line -> line.matches(row, num));
            for (byte b = 1; b < possible.size(); b++) {
                columnGroup &= possible.get(0).getX() == column;
                rowGroup &= possible.get(0).getY() == row;
            }
            if (columnGroup) {//TODO make sure it doesn't stack
                lines.add(new GroupLine(this.field.getColumn(column), num, possible));
            }
            if (rowGroup) {//they shouldn't be happening together but anyway...
                lines.add(new GroupLine(this.field.getRow(row), num, possible));
            }
        }
    }

    public void set(byte[][] array) {
        this.cells.forEach(cell -> cell.set(array));
    }

    public void apply(byte[][] array) {
        this.cells.forEach(cell -> cell.apply(array));
    }
}
