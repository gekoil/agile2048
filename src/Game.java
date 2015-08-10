import Entities.User;
import Interfaces.Board;
import Interfaces.Difficulty;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;

public class Game {

    DAO dao;
    JFrame gameFrame;
    User userEntity;

    public Game() {
        dao = DAO.getInstance();
    }

    public void LaunchGame() {
        gameFrame = new JFrame();
        gameFrame.setTitle("2048 Game");
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dao.closeDao();
            }
        });
        gameFrame.setSize(340, 400);
        gameFrame.setResizable(false);
        LogOnScreen logOn = new LogOnScreen();
        logOn.registerToLogin(this);

        gameFrame.add(logOn);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    public void onLogin(String username) {
        if (username == null || username.isEmpty() && username != "username")
            return;
        userEntity = dao.getUser(username);
        if (userEntity != null) {
            JOptionPane.showMessageDialog(null,
                    "Welcome back, " + userEntity.name + "!\n" + userEntity,
                    "Welcome back, " + userEntity.name + "!",
                    JOptionPane.INFORMATION_MESSAGE);
            System.out.println(username);
        } else {
            userEntity = new User();
            userEntity.name = username;
            dao.storeUser(userEntity);
        }

        showSettings();
    }

    private void showSettings() {
        SettingsPanel settings = new SettingsPanel();
        settings.registerToSet(this);

        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().add(settings);
        gameFrame.validate();
        gameFrame.update(gameFrame.getGraphics());
    }

    public void onSettingsSet(Difficulty difficulty) {
        BoardPanel boardPanel;
        Board board;
        // TODO: Add new boards for each difficulty
        switch (difficulty) {
            case EASY:
                board = new DefaultBoard(3);
                break;
            case NORMAL:
                board = new DefaultBoard();
                break;
            case HARD:
                board = new DefaultBoard(5);
                break;
            default:
                throw new InputMismatchException("Invalid Difficulty!");
        }
        boardPanel = new BoardPanel(board);

        gameFrame.getContentPane().removeAll();
        gameFrame.setSize(boardPanel.getSize());
        gameFrame.getContentPane().add(boardPanel);
        gameFrame.validate();
        gameFrame.update(gameFrame.getGraphics());
    }
}
