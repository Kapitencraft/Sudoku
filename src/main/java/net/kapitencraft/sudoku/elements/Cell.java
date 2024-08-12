package net.kapitencraft.sudoku.elements;

import net.kapitencraft.sudoku.elements.data.DataContainer;
import net.kapitencraft.sudoku.SudokuField;
import net.kapitencraft.sudoku.elements.fill_helper.GroupLine;
import net.kapitencraft.sudoku.util.Vec2;

import java.util.ArrayList;
import java.util.List;

public class Cell extends DataContainer {
    private final Vec2 location;
    private final Group group;
    private final Row row;
    private final Column column;
    private final List<GroupLine> lines = new ArrayList<>();
    private byte value = 0;

    public Cell(Vec2 location, Group group, SudokuField field) {
        super(field);
        this.location = location;
        this.group = group;
        this.row = (Row) field.getRow(this.getY()).addElement(this);
        this.column = (Column) field.getColumn(this.getX()).addElement(this);
    }

    @Override
    public void addValue(byte id) {
        super.addValue(id);
        this.update();
    }

    private void update() {
        if (filled()) return; //it already has a value, you don't need to update
        List<Byte> possibles = remaining();
        if (possibles.size() == 1) {
            this.value = possibles.get(0);
            accordChanges();
        }
    }

    private void accordChanges() {
        this.group.accordChange(this.value);
        this.row.accordChange(this.value);
        this.column.accordChange(this.value);
        this.lines.forEach(line -> {
            if (line.getNum() == this.value) {
                line.filled();
            } else {
                line.removeCell(this);
            }
        });
    }

    public void set(byte[][] array) {
        byte val = array[getY()][getX()];
        if (val > 0) {
            insert(val);
        }
    }

    public void apply(byte[][] array) {
        array[getY()][getX()] = this.value;
    }

    @Override
    public String toString() {
        return "Cell{y=" + getY() + ", x=" + getX() + ", value=" + this.value;
    }

    public byte getX() {
        return this.location.getX();
    }

    public byte getY() {
        return this.location.getY();
    }

    public void insert(byte b) {
        if (filled()) return;
        this.value = b;
        accordChanges();
    }

    public boolean isNum(byte num) {
        return this.value == num;
    }

    public void addLine(GroupLine line) {
        this.lines.add(line);
    }

    public void removeGroupLine(GroupLine groupLine) {
        this.lines.remove(groupLine);
    }

    public boolean filled() {
        return this.value > 0;
    }

    @Override
    public List<Byte> remaining() {
        if (filled()) return List.of(this.value);
        return super.remaining();
    }
}