import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Interfaces.Board;
import Interfaces.Direction;

public class DefaultBoard implements Board{

	private static final int[] startValues = { 2, 4 };
	private static final int[] possibleBValues = { 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048 };
	private static final int SIZE = 4;
	private Optional<Integer>[][] cells;

	public DefaultBoard() {
		initBoard();
	}

	@SuppressWarnings("unchecked")
	private void initBoard() {
		cells = new Optional[SIZE][SIZE];
		for (int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				cells[i][j] = Optional.empty();
		addCell();
		addCell();
	}

	private void addCell() {
		List<Integer[]> list = availableSpace();
		if (!availableSpace().isEmpty()) {
			int index = (int) (Math.random() * list.size()) % list.size();
			Integer[] indexes = list.get(index);
			cells[indexes[0]][indexes[1]] = Optional.of(Math.random() < 0.9 ? startValues[0] : startValues[1]);
		}
	}

	private List<Integer[]> availableSpace() {
		final List<Integer[]> list = new ArrayList<Integer[]>(SIZE*SIZE);
		for (int i=0; i<SIZE; i++)
			for(int j=0; j<SIZE; j++)
				if (!cells[i][j].isPresent())
					list.add(new Integer[] {i, j});
		return list;
	}

	@Override
	public Optional<Integer> cell(int x, int y) {
		return cells[x][y];
	}

	@Override
	public Board move(Direction dir) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean gameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean won() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Board flipDiagonally() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Board flipHorizontally() {
		// TODO Auto-generated method stub
		return null;
	}

	public void show(){
		for (int i=0; i<SIZE; i++)
		{
			for(int j=0; j<SIZE; j++)
				if (cells[i][j].isPresent())
					System.out.printf("%-3d", cells[i][j].get());
				else
					System.out.printf("%-3d", 0);
			System.out.println();
		}
	}

}
