package net.kapitencraft.sudoku.elements;

import net.kapitencraft.sudoku.elements.data.DataLine;
import net.kapitencraft.sudoku.elements.data.DataList;

import java.util.ArrayList;
import java.util.List;

public class GroupLine {
    private final DataLine target;
    private final List<Cell> members = new ArrayList<>();
    private final byte num;

    public GroupLine(DataLine target, byte num, List<Cell> cells) {
        this.target = target;
        this.num = num;
        this.addAll(cells);
        this.target.accordChangeWithout(num, cells); //tell the line that this number has been added without removing the possibility of these cells to contain that value
    }

    public void removeCell(Cell cell) {
        this.members.remove(cell);
    }

    public void filled() {
        this.members.forEach(cell -> cell.removeGroupLine(this));
    }

    public void addAll(List<Cell> possible) {
        this.members.addAll(possible);
        this.members.forEach(cell -> cell.addLine(this));
    }

    public byte getNum() {
        return this.num;
    }

    public boolean matches(byte val, byte num) {
        return this.num == num && this.target.getIdentifier() == val;
    }
}
