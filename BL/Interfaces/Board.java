package Interfaces;

import Controller.GameController;

import java.util.Optional;

public interface Board {
	Optional<Integer> cell(int x, int y);
	Board move(Direction dir);
	boolean gameOver();
	boolean won();
	Board flipDiagonally();
	Board flipHorizontally();

	int getBoardSize();
	int getScore();

	void setControl(GameController game);
}
