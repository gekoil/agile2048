package Panels;

import Controller.GameController;
import Interfaces.Difficulty;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private static int[] possibleScores = {1024, 2048, 4096};
    private static int[] possibleGridSize = {4, 5, 6};

    private int        boardSize;
    private int        targetScore;
    private GameController game;
    private Difficulty selectedDifficulty;

    public SettingsPanel() {
        setLayout(new GridLayout(8, 1, 1, 1));

        JLabel title = new JLabel("Controller.Game Options");
        title.setHorizontalAlignment(JLabel.CENTER);
        add(title);

        JLabel difTitle = new JLabel("Difficulty:");
        add(difTitle);

        JPanel difPanel = new JPanel();
        ButtonGroup difRadioGroup = new ButtonGroup();
        for (Difficulty dif : Difficulty.values()) {
            JRadioButton button = new JRadioButton(dif.name());
            button.addActionListener(e -> selectedDifficulty = dif);
            difRadioGroup.add(button);
            difPanel.add(button);
        }
        add(difPanel);

        JLabel scoreTitle = new JLabel("Target Score:");
        add(scoreTitle);

        JPanel scorePanel = new JPanel();
        ButtonGroup scoreGroup = new ButtonGroup();
        for (int score : possibleScores) {
            JRadioButton button = new JRadioButton("" + score);
            button.addActionListener(e -> targetScore = score);
            scoreGroup.add(button);
            scorePanel.add(button);
        }
        add(scorePanel);

        JLabel boardTitle = new JLabel("Board Size:");
        add(boardTitle);

        JPanel boardPanel = new JPanel();
        ButtonGroup boardGroup = new ButtonGroup();
        for (int size : possibleGridSize) {
            JRadioButton button = new JRadioButton("" + size);
            button.addActionListener(e -> boardSize = size);
            boardGroup.add(button);
            boardPanel.add(button);
        }
        add(boardPanel);

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(e -> game.onSettingsSet(selectedDifficulty, boardSize, targetScore));
        add(okBtn);
    }

    public void registerToSet(GameController game) {
        this.game = game;
    }
}
