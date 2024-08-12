package net.kapitencraft.sudoku.elements.fill_helper;

import net.kapitencraft.sudoku.elements.Cell;
import net.kapitencraft.sudoku.elements.data.DataList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataGroup {
    private final List<Cell> cells = new ArrayList<>();
    private final List<Byte> values;
    private final DataList list;

    public DataGroup(List<Byte> bytes, DataList list) {
        this.values = bytes;
        this.list = list;
    }

    public void add(Collection<Cell> cells) {
        this.cells.addAll(cells);
        this.values.forEach(aByte -> list.accordChangeWithout(aByte, this.cells));
        this.cells.forEach(cell -> cell.removeOther(values));
    }


    public boolean is(List<Byte> group) {
        return this.values.equals(group);
    }
}
