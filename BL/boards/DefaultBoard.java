package Boards;

import Controller.GameController;
import Interfaces.Board;
import Interfaces.Difficulty;
import Interfaces.Direction;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

public class DefaultBoard implements Board {

	private static final int[] startValues = { 2, 4 };
	//private static final int[] possibleBValues = { 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048 };
	private static final int DEFAULT_SIZE = 4;
	private static final int DEFAULT_TARGET_SCORE = 2048;
	private static final Difficulty DIFFICULTY = Difficulty.NORMAL;
	private int boardSize;
	private int score;
	private int targetScore;
	private GameController controller;

	protected Optional<Integer>[][] cells;

	public DefaultBoard() {
		this(DEFAULT_SIZE, DEFAULT_TARGET_SCORE);
	}

	public DefaultBoard(int size, int target) {
		boardSize = size;
		targetScore = target;
		score = 0;
		initBoard();
	}

	@SuppressWarnings("unchecked")
	protected void initBoard() {
		cells = new Optional[boardSize][boardSize];
		for (int i=0; i< boardSize; i++)
			for(int j=0; j< boardSize; j++)
				cells[i][j] = Optional.empty();
		addCell();
		addCell();
	}

	protected void addCell() {
		List<Integer[]> list = availableSpace();
		if (!availableSpace().isEmpty()) {
			int index = (int) (Math.random() * list.size()) % list.size();
			Integer[] indexes = list.get(index);
			cells[indexes[0]][indexes[1]] = Optional.of(Math.random() < 0.9 ? startValues[0] : startValues[1]);
		}
	}

	protected List<Integer[]> availableSpace() {
		final List<Integer[]> list = new ArrayList<Integer[]>(boardSize * boardSize);
		for (int i=0; i< boardSize; i++)
			for(int j=0; j< boardSize; j++)
				if (!cells[i][j].isPresent())
					list.add(new Integer[] {i, j});
		return list;
	}

	protected void collapseCells() {
		for (int i = 1; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (cells[i][j].isPresent()) {
					int k = i - 1, newValue = cells[i][j].get();
					cells[i][j] = Optional.empty();

					while (k > 0 && !cells[k][j].isPresent()) {
						k--;
					}

					if (cells[k][j].isPresent()) {

						if (cells[k][j].get() == newValue) {
							cells[k][j] = Optional.of(newValue * 2);
							score += newValue * 2;
						} else {
							cells[k + 1][j] = Optional.of(newValue);
						}
					} else {
						cells[k][j] = Optional.of(newValue);
					}

				}
			}
		}
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
		// Flip the matrix
		switch (dir) {
			case Up:
				flipDiagonally();
				break;
			case Down:
				flipHorizontally().flipDiagonally();
				break;
			case Left:
				// Nothing to do
				break;
			case Right:
				flipDiagonally().flipHorizontally().flipDiagonally();
				break;
			default:
				throw new InputMismatchException("Wrong direction!");
		}

		collapseCells();

		// Reverse the flip
		switch (dir) {
			case Up:
				flipDiagonally();
				break;
			case Down:
				flipDiagonally().flipHorizontally();
				break;
			case Left:
				// Nothing to do
				break;
			case Right:
				flipDiagonally().flipHorizontally().flipDiagonally();
				break;
			default:
				throw new InputMismatchException("Wrong direction!");
		}

		if (!checkConditions()) {
			addCell();
		}

		return this;
	}

	protected boolean checkConditions() {
		if (won()) {
			controller.onWin(DIFFICULTY, score);
			return true;
		}

		if (gameOver()) {
			controller.onLose(DIFFICULTY);
			return true;
		}
		return false;
	}

	@Override
	public boolean gameOver() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j< boardSize; j++) {
				if (!cell(i, j).isPresent()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean won() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j< boardSize; j++) {
				Optional<Integer> cell = cell(i, j);
				if (cell.isPresent()) {
					if(cell.get() >= targetScore) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public Board flipDiagonally() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = i; j < boardSize; j++) {
				Optional<Integer> tmp = cells[i][j];
				cells[i][j] = cells[j][i];
				cells[j][i] = tmp;
			}
		}
		return this;
	}

	@Override
	public Board flipHorizontally() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize / 2; j++) {
				Optional<Integer> tmp = cells[i][j];
				cells[i][j] = cells[i][boardSize - (j + 1)];
				cells[i][boardSize - (j + 1)] = tmp;
			}
		}
		return this;
	}

	public int getBoardSize() {
		return boardSize;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setControl(GameController game) {
		this.controller = game;
	}
}
