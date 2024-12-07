package gogame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GoStateTest {

    private GoState goState;

    @ParameterizedTest
    @ArgumentsSource(NeighborArgumentsProvider.class)
    void testGetNeighbors(Point point, Set<Point> expectedNeighbors) {
        goState = new GoState(9);
        Set<Point> actualNeighbors = Set.of(goState.getNeighbors(point));
        assertEquals(expectedNeighbors, actualNeighbors, "Neighbors do not match expected");
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "1, 1",
    })
    void testIsLegalMoveNonEmpty(int x, int y) {
        goState = new GoState(9);
        goState.placeStone(new Point(x, y));
        assertFalse(goState.isLegalMove(new Point(x, y)));

    }

    @Test
    void testIsLegalMoveSuicide() {
        goState = new GoState(9);
        goState.placeStone(new Point(4, 4)); //b
        goState.placeStone(new Point(5, 4)); //w
        goState.placeStone(new Point(4, 3));//b
        goState.placeStone(new Point(4, 5));//w


        assertFalse(goState.isLegalMove(new Point(3, 4)), "Should not allow suicide move");
    }

    @Test
    void testIsLegalMovePreventsRepeatedGameState() {
        goState = new GoState(9);
        Point firstMove = new Point(3, 3);
        goState.makeMove(firstMove);

        int firstStateHash = goState.hashCode();

        boolean result = goState.isLegalMove(firstMove);

        assertFalse(result, "The move should be rejected due to repeated game state.");

        assertEquals(firstStateHash, goState.hashCode(), "The game state should remain unchanged.");

    }

    @BeforeEach
    public void setUp() {
        goState = new GoState(9);
    }
    @Test
    void testCheckCaptureUpdatesCapturedCountAndBoard(){
        goState = new GoState(9);
        goState.placeStone(new Point(4, 4)); //b
        goState.placeStone(new Point(5, 4)); //w
        goState.placeStone(new Point(4, 3));//b
        goState.placeStone(new Point(4, 5));//w
        Arrays.stream(goState.board)
                .forEach(row -> System.out.println(Arrays.toString(row)));

        int whiteCapturedBefore = goState.whiteCaptured;
        int blackCapturedBefore = goState.blackCaptured;
        System.out.println("white capt "+whiteCapturedBefore);
        System.out.println("black capt "+blackCapturedBefore);
//       // goState.checkCapture(new Point(3, 4));
//
//
//        assertEquals(blackCapturedBefore + 2, goState.blackCaptured);
//        assertEquals(BoardSpace.EMPTY, goState.board[4][5]);
//        assertEquals(BoardSpace.EMPTY, goState.board[3][5]);
//        assertEquals(whiteCapturedBefore, goState.whiteCaptured);
//        assertNotEquals(BoardSpace.WHITE, goState.board[4][5]);
//        assertNotEquals(BoardSpace.WHITE, goState.board[3][5]);
//        assertNotEquals(BoardSpace.EMPTY, goState.board[4][4]);
//        assertNotEquals(BoardSpace.EMPTY, goState.board[5][5]);


    }

    @Test
    void testGetLiberties() {
        goState = new GoState(9);

        //Black
        goState.placeStone(new Point(4, 4));
        //White
        goState.placeStone(new Point(4, 5));

        Set<Point> visited = new HashSet<>();
        Point[] liberties = goState.getLiberties(Stone.BLACK, new Point(4, 4), visited);

        Point[] expectedLiberties = {
                new Point(4, 3),
                new Point(3, 4),
                new Point(5, 4)
        };


       // assertArrayEquals(expectedLiberties, liberties, "Liberties does not match expected values");

        //  assertTrue(visited.contains(new Point(4, 4)), "Visited set should include the checked stone");
    }

    @Test
    void checkCaptured() {
    }

    @Test
    void isLegalMove() {
    }

    @Test
    void makeMove() {
    }

    static class NeighborArgumentsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return Stream.of(
                    Arguments.of(new Point(0, 0), Set.of(new Point(0, 1), new Point(1, 0))),
                    Arguments.of(new Point(0, 8), Set.of(new Point(0, 7), new Point(1, 8))),
                    Arguments.of(new Point(8, 0), Set.of(new Point(7, 0), new Point(8, 1))),
                    Arguments.of(new Point(4, 4), Set.of(new Point(4, 3), new Point(4, 5),  new Point(3, 4), new Point(5, 4)))
            );
        }
    }
}