import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Optional;

import javax.swing.JPanel;

public class BoardPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private static final Color BG_COLOR = new Color(0xbbada0);
	private static final String FONT_NAME = "Arial";
	private static final int CELL_SIZE = 64;
	private static final int CELL_MARGIN = 16;
	private final DefaultBoard board = new DefaultBoard();
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(BG_COLOR);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		for (int i = 0; i < board.getBoardSize(); i++) {
			for (int j = 0; j < board.getBoardSize(); j++) {
				drawCell(g, board.cell(i, j), i, j);
			}
		}
	}
	
	private void drawCell(Graphics g2, Optional<Integer> cell, int x, int y) {
		Graphics2D g = ((Graphics2D) g2);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_NORMALIZE);
		int value = cell.isPresent() ? cell.get().intValue() : 0;
		int xOffset = offsetCoors(x);
		int yOffset = offsetCoors(y);
		g.setColor(getBackground(value));
		g.fillRoundRect(xOffset, yOffset, CELL_SIZE, CELL_SIZE, 14, 14);
		g.setColor(getForeground(value));
		final int size = value < 100 ? 36 : value < 1000 ? 32 : 24;
		final Font font = new Font(FONT_NAME, Font.BOLD, size);
		g.setFont(font);
		String s = String.valueOf(value);
		final FontMetrics fm = getFontMetrics(font);
		final int w = fm.stringWidth(s);
		final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];
		if (cell.isPresent())
			g.drawString(s, xOffset + (CELL_SIZE - w) / 2, yOffset + CELL_SIZE
					- (CELL_SIZE - h) / 2 - 2);
		g.setFont(new Font(FONT_NAME, Font.PLAIN, 18));
		g.drawString("Score: " + 0, 200, 365);
	}
	
	private static int offsetCoors(int arg) {
		return arg * (CELL_MARGIN + CELL_SIZE) + CELL_MARGIN;
	}
	
	private static Color getForeground(int value) {
		return value < 16 ? new Color(0x776e65) : new Color(0xf9f6f2);
	}
	
	private static Color getBackground(int value) {
		switch (value) {
		case 2:
			return new Color(0xeee4da);
		case 4:
			return new Color(0xede0c8);
		case 8:
			return new Color(0xf2b179);
		case 16:
			return new Color(0xf59563);
		case 32:
			return new Color(0xf67c5f);
		case 64:
			return new Color(0xf65e3b);
		case 128:
			return new Color(0xedcf72);
		case 256:
			return new Color(0xedcc61);
		case 512:
			return new Color(0xedc850);
		case 1024:
			return new Color(0xedc53f);
		case 2048:
			return new Color(0xedc22e);
		}
		return new Color(0xcdc1b4);
	}
}
