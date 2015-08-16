import Boards.DefaultBoard;
import org.junit.Before;
import org.junit.Test;

public class DefaultBoardTest {

    DefaultBoard testBoard;

    @Before
    public void init() {
        testBoard = new DefaultBoard();
    }

    @Test
    public void FlipHorizontal() {
        // TODO: Verify flip
        testBoard.flipHorizontally();
    }

    @Test
    public void FlipDiagonally() {
        // TODO: Verify flip
        testBoard.flipDiagonally();
    }

    @Test
    public void moveUp() {

    }

    @Test
    public void moveDown() {

    }

    @Test
    public void moveLeft() {

    }

    @Test
    public void moveRight() {

    }
}
