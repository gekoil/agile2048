package Controller;

import Boards.DefaultBoard;
import Boards.EasyBoard;
import Boards.HardBoard;
import Entities.User;
import Interfaces.Board;
import Interfaces.Difficulty;
import Interfaces.IDAO;
import Panels.BoardPanel;
import Panels.LogOnPanel;
import Panels.SettingsPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.InputMismatchException;

public class GameController {

    private static int[] DEFAULT_FRAME_SIZE = {340, 400};

    IDAO dao;
    JFrame gameFrame;
    JPanel boardPanel;
    User userEntity;

    public GameController(IDAO dao) {
        this.dao = dao;
    }

    public void LaunchGame() {
        gameFrame = new JFrame();
        gameFrame.setTitle("2048 Game");
        gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
        gameFrame.setSize(DEFAULT_FRAME_SIZE[0], DEFAULT_FRAME_SIZE[1]);
        gameFrame.setResizable(false);
        LogOnPanel logOn = new LogOnPanel();
        logOn.registerToLogin(this);

        gameFrame.add(logOn);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
    }

    public void onLogin(String username) {
        if (username == null || username.isEmpty())
            return;
        userEntity = dao.getUser(username);
        if (userEntity != null) {
            JOptionPane.showMessageDialog(gameFrame,
                    "Welcome back, " + userEntity.name + "!\n" + userEntity,
                    "Welcome back, " + userEntity.name + "!",
                    JOptionPane.INFORMATION_MESSAGE);
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

        gameFrame.setSize(DEFAULT_FRAME_SIZE[0], DEFAULT_FRAME_SIZE[1]);
        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().add(settings);
        gameFrame.validate();
        gameFrame.update(gameFrame.getGraphics());
    }

    public void onSettingsSet(Difficulty difficulty, int boardSize, int targetScore) {
        //BoardPanel boardPanel;
        Board board;

        switch (difficulty) {
            case EASY:
                board = new EasyBoard(boardSize, targetScore);
                break;
            case NORMAL:
                board = new DefaultBoard(boardSize, targetScore);
                break;
            case HARD:
                board = new HardBoard(boardSize, targetScore);
                break;
            default:
                throw new InputMismatchException("Invalid Difficulty!");
        }
        board.setControl(this);
        boardPanel = new BoardPanel(board);
        boardPanel.setFocusable(true);

        gameFrame.setSize(boardPanel.getSize());
        gameFrame.getContentPane().removeAll();
        gameFrame.getContentPane().add(boardPanel);
        gameFrame.validate();
        gameFrame.update(gameFrame.getGraphics());
        boardPanel.requestFocusInWindow();
    }

    public void onWin(Difficulty dif, int score) {
        int oldScore = userEntity.highScores.get(dif);
        if (score > oldScore) {
            userEntity.highScores.put(dif, score);
        }
        userEntity.gamesWon++;
        dao.storeUser(userEntity);

        int res = JOptionPane.showOptionDialog(gameFrame,
                "You Win!\nOne more?",
                "Game Won!", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (res == JOptionPane.YES_OPTION) {
            showSettings();
        } else {
            onClose();
        }
    }

    public void onLose(Difficulty dif) {
        userEntity.gamesLost++;
        dao.storeUser(userEntity);
        int res = JOptionPane.showOptionDialog(gameFrame,
                "You Lost!\nTry again?",
                "Game Lost!", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (res == JOptionPane.YES_OPTION) {
            showSettings();
        } else {
            onClose();
        }
    }

    private void onClose() {
        dao.closeDao();
        System.exit(0);
    }
}
