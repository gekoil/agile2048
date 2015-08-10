import Interfaces.Board;
import Interfaces.Direction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

public class DefaultBoard implements Board {

	private static final int[] startValues = { 2, 4 };
	private static final int[] possibleBValues = { 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048 };
	private static final int DEFAULT_SIZE = 4;
	private Optional<Integer>[][] cells;
	private int boardSize;
	private int score;

	public DefaultBoard() {
		this(DEFAULT_SIZE);
	}

	public DefaultBoard(int size) {
		boardSize = size;
		score = 0;
		initBoard();
	}

	@SuppressWarnings("unchecked")
	private void initBoard() {
		cells = new Optional[boardSize][boardSize];
		for (int i=0; i< boardSize; i++)
			for(int j=0; j< boardSize; j++)
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
		final List<Integer[]> list = new ArrayList<Integer[]>(boardSize * boardSize);
		for (int i=0; i< boardSize; i++)
			for(int j=0; j< boardSize; j++)
				if (!cells[i][j].isPresent())
					list.add(new Integer[] {i, j});
		return list;
	}

	@Override
	public Optional<Integer> cell(int x, int y) {
		if (x < boardSize && x >=0 && y < boardSize && y >=0) {
			return cells[x][y];
		} else {
			return null;
		}
	}

	@Override
	public Board move(Direction dir) {
		switch (dir) {
			case Up:
				break;
			case Down:
				break;
			case Left:
				break;
			case Right:
				break;
			default:
				throw new InputMismatchException("Wrong direction!");
		}
		return this;
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
		for (int i=0; i< boardSize; i++)
		{
			for(int j=0; j< boardSize; j++)
				if (cells[i][j].isPresent())
					System.out.printf("%-3d", cells[i][j].get());
				else
					System.out.printf("%-3d", 0);
			System.out.println();
		}
	}

	public int getBoardSize() {
		return boardSize;
	}

	@Override
	public int getScore() {
		return score;
	}
}
