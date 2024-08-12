package net.kapitencraft.sudoku.elements.data;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.sudoku.SudokuField;
import net.kapitencraft.sudoku.elements.Cell;
import net.kapitencraft.sudoku.elements.fill_helper.DataGroup;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class DataList extends DataContainer {
    public final List<Cell> cells = new ArrayList<>();
    private final List<DataGroup> groups = new ArrayList<>();

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
        checkRemainingGroups();
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

    public void checkRemainingGroups() {
        Map<Cell, List<Byte>> cellToData = this.cells.stream().collect(Collectors.toMap(Function.identity(), Cell::remaining));
        Multimap<List<Byte>, Cell> matches = HashMultimap.create();
        for (Map.Entry<Cell, List<Byte>> entry : cellToData.entrySet()) {
            if (matches.containsKey(entry.getValue())) continue;
            for (Map.Entry<Cell, List<Byte>> entry1 : cellToData.entrySet()) {
                List<Byte> val = entry.getValue();
                if (val.size() > 1 && val.equals(entry1.getValue()) && !hasGroup(val)) { //make sure it doesn't stack the groups
                    matches.put(val, entry.getKey());
                    matches.put(val, entry1.getKey());
                }
            }
        }
        if (!matches.isEmpty()) {
            matches.keySet().forEach(bytes -> {
                Collection<Cell> content = matches.get(bytes);
                if (content.size() == bytes.size()) {//make sure that the group has a spot for each num applied and not less or more (less shouldn't happen anyway...)
                    DataGroup group = new DataGroup(bytes, this);
                    group.add(matches.get(bytes));
                    this.groups.add(group);
                }
            });
        }
    }

    private boolean hasGroup(List<Byte> group) {
        return this.groups.stream().anyMatch(dataGroup -> dataGroup.is(group));
    }
}