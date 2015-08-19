package Boards;

import java.util.List;
import java.util.Optional;

public class EasyBoard extends DefaultBoard {

    public EasyBoard(int size, int target) {
        super(size, target);
    }

    @Override
    protected void addCell() {
        List<Integer[]> list = availableSpace();
        if (!availableSpace().isEmpty()) {
            int index = (int) (Math.random() * list.size()) % list.size();
            Integer[] indexes = list.get(index);
            cells[indexes[0]][indexes[1]] = Optional.of(2);//Optional.of(Math.random() < 0.9 ? startValues[0] : startValues[1]);
        }
    }
}
