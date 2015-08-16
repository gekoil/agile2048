import Controller.GameController;
import DAOs.DAO;

public class Main {

	public static void main(String[] args) {
		GameController game = new GameController(DAO.getInstance());
		game.LaunchGame();
	}
}
