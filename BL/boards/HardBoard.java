package Boards;

import java.util.List;
import java.util.Optional;

public class HardBoard extends DefaultBoard {

    private static final int[] startValues = { 2, 4, 8 };

    public HardBoard(int size, int target) {
        super(size, target);
    }

    @Override
    protected void addCell() {
        List<Integer[]> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Integer[] indexes = list.get(index);
            double chance = Math.random();
            if (chance < 0.2) {
                cells[indexes[0]][indexes[1]] = Optional.of(startValues[0]);
            } else if (chance < 0.6) {
                cells[indexes[0]][indexes[1]] = Optional.of(startValues[1]);
            } else {
                cells[indexes[0]][indexes[1]] = Optional.of(startValues[2]);
            }
        }
    }
}
