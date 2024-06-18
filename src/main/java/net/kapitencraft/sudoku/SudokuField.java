package net.kapitencraft.sudoku;

import net.kapitencraft.sudoku.elements.Cell;
import net.kapitencraft.sudoku.elements.Column;
import net.kapitencraft.sudoku.elements.Group;
import net.kapitencraft.sudoku.elements.Row;
import net.kapitencraft.sudoku.elements.data.DataList;
import net.kapitencraft.sudoku.util.Utils;
import net.kapitencraft.sudoku.util.Vec2;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

public class SudokuField {
    private final byte groupSize;
    private final byte fieldSize;
    private final Row[] rows;
    private final Column[] columns;
    private final Group[] groups;


    public SudokuField(@Range(from = 2, to = 8) byte groupSize) {
        this.groupSize = groupSize; //groupSize squared is the size of rows, columns and groups
        this.fieldSize = (byte) Math.pow(groupSize, 2);
        this.rows = new Row[fieldSize];
        Utils.fillArray(rows, ()-> new Row(this));
        this.columns = new Column[fieldSize];
        Utils.fillArray(columns, () -> new Column(this));
        Group[] groups = new Group[fieldSize];
        for (int i = 0; i < fieldSize; i++) {
            byte x = (byte) (i % groupSize);
            byte y = (byte) ((i - x) / groupSize);
            groups[i] = new Group(x, y, groupSize, this); //initialize Groups last
        }
        this.groups = groups;
    }

    public void applyValues(byte[][] array) {
        if (array.length != Math.pow(this.groupSize, 2)) throw new IllegalArgumentException("array size doesn't match Field size");
        for (Group group : this.groups) {
            group.set(array);
        }
    }

    public Row getRow(byte y) {
        return rows[y];
    }

    public Column getColumn(byte x) {
        return columns[x];
    }

    public int size() {
        return this.fieldSize;
    }

    public byte[][] getContent() {
        byte[][] array = new byte[this.fieldSize][this.fieldSize];
        for (Group group : this.groups) {
            group.apply(array);
        }
        return array;
    }

    public boolean isComplete() {
        return all().stream().allMatch(DataList::isComplete);
    }

    private List<DataList> all() {
        List<DataList> list = new ArrayList<>(List.of(this.groups));
        list.addAll(List.of(this.rows));
        list.addAll(List.of(this.columns));
        return list;
    }

    public void generate() {
        for (int i = 0; i < Main.yield && !isComplete(); i++) {
            for (DataList list : all()) {
                list.checkRemaining();
            }
        }
        if (!isComplete()) {
            Main.print("unable to complete field, results: ");
        }
    }

    public Cell getCell(Vec2 loc) {
        for (Group group : groups) {
            for (Cell cell : group.cells) {
                if (cell.getX() == loc.getX() && cell.getY() == loc.getY()) return cell;
            }
        }
        return null;
    }
}
