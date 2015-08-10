import Interfaces.Difficulty;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private Game game;
    private Difficulty selectedDifficulty;

    public SettingsPanel() {
        setLayout(new GridLayout(3, 1, 1, 1));

        JLabel title = new JLabel("Choose difficulty level:");
        add(title);

        JPanel radioPanel = new JPanel();
        ButtonGroup radioGroup = new ButtonGroup();
        for (Difficulty dif : Difficulty.values()) {
            JRadioButton button = new JRadioButton(dif.name());
            button.addActionListener(e -> selectedDifficulty = dif);
            radioGroup.add(button);
            radioPanel.add(button);
        }
        add(radioPanel);

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(e -> game.onSettingsSet(selectedDifficulty));
        add(okBtn);
    }

    public void registerToSet(Game game) {
        this.game = game;
    }
}
