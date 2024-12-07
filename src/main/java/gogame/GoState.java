package gogame;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GoState implements Predicate<Point> {
    BoardSpace[][] board;
     int blackCaptured;
     int whiteCaptured;
    Stone turn;
    private Set<GoState> previousStates;

    // Primary Construct
    public GoState(int size) {
        this.board = Arrays
                .stream(new BoardSpace[size][size])
                .map(row -> Arrays.stream(row)
                        .map(cell -> BoardSpace.EMPTY)
                        .toArray(BoardSpace[]::new))
                .toArray(BoardSpace[][]::new);
        this.blackCaptured = 0;
        this.whiteCaptured = 0;
        this.turn = Stone.BLACK;
        this.previousStates = new HashSet<>();
    }


    // Copy Constructor
    public GoState(GoState other) {
        this.board = Arrays.stream(other.board)
                .map(BoardSpace[]::clone)
                .toArray(BoardSpace[][]::new);
        this.blackCaptured = other.blackCaptured;
        this.whiteCaptured = other.whiteCaptured;
        this.turn = other.turn;
        this.previousStates = new HashSet<>(other.previousStates);
    }

    public int getBoardSize() {
        return board.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(board), turn);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GoState other = (GoState) obj;
        return Arrays.deepEquals(this.board, other.board) && this.turn == other.turn;
    }

    @Override
    public boolean test(Point point) {
        int size = getBoardSize();
        return point.x >= 0 && point.x < size && point.y >= 0 && point.y < size;
    }

    public Point[] getNeighbors(Point p) {
        Point[] neighbors = new Point[]{
                new Point(p.x + 1, p.y),
                new Point(p.x - 1, p.y),
                new Point(p.x, p.y + 1),
                new Point(p.x, p.y - 1),
        };

        return Arrays.stream(neighbors)
                .filter(this)
                .toArray(Point[]::new);
    }

    public Point[] getLiberties(Stone stone, Point start, Set<Point> scanned) {
        Set<Point> liberties = new HashSet<>();
        Set<Point> toScan = new HashSet<>();
        toScan.add(start);

        while (!toScan.isEmpty()) {
            Point current = toScan.iterator().next();
            toScan.remove(current);

            if (scanned.contains(current)) {
                continue;  // Skip if already scanned
            }

            scanned.add(current);

            BoardSpace currentBoardSpace = board[current.x][current.y];

            if (currentBoardSpace == BoardSpace.EMPTY) {
                liberties.add(current);  // This is a liberty, as it's an empty space
            } else if (currentBoardSpace.stone == stone) {
                // This is a stone of the same color, check its neighbors
                Arrays.stream(getNeighbors(current))
                        .filter(neighbor -> !scanned.contains(neighbor))  // Only add unscanned neighbors
                        .forEach(toScan::add);
            }
        }

        return liberties.toArray(new Point[0]);
    }

    public void checkCapture(Point point) {
        BoardSpace currentBoardSpace = board[point.x][point.y];

        if (currentBoardSpace.stone == null) {
            return;
        }

        Stone stone = currentBoardSpace.stone;
        Stone opposite = stone.opposite();

        Set<Point> scanned = new HashSet<>();
        Point[] liberties = getLiberties(opposite, point, scanned);

        if (liberties.length == 0) {

            /* Remove captured stones*/
            scanned.stream().map(p->{
                board[p.x][p.y] = BoardSpace.EMPTY;
                return p;
            }).collect(Collectors.toList());

            /* Update stones count*/
            if (stone == Stone.BLACK) {
                whiteCaptured += scanned.size();
            }else{
                blackCaptured += scanned.size();
            }
        }

    }

    public GoState placeStone(Point point){
        board[point.x][point.y] = BoardSpace.fromStone(turn);
        turn = turn.opposite();
        Arrays.stream(getNeighbors(point)).forEach(this::checkCapture);
        return this;
    }

    public boolean isLegalMove(Point p) {
        // point is valid and space too
        if(!test(p) || board[p.x][p.y] != BoardSpace.EMPTY ) {
            return false;
        }

        Point[] liberties = getLiberties(turn,p,new HashSet<>());
        // No liberty
        if (liberties.length == 0) {
            return false;
        }

        // Capture condition
        boolean isCapture = Arrays.stream(getNeighbors(p))
                .anyMatch(neighbor -> {
                   // if the neighbor is opposite and no lib then its captured
                   BoardSpace neighborBoardSpace = board[neighbor.x][neighbor.y];
                   if (neighborBoardSpace.stone != turn
                           && neighborBoardSpace.stone != null
                           && getLiberties(turn.opposite(),neighbor,new HashSet<>()).length == 0 ) {
                       checkCapture(neighbor);
                       return true;
                   }
                   return false;
                });

        // Suicide Move with no capture
        if(!isCapture && liberties.length == 1) {
            return false;
        }

        GoState state = new GoState(this);

        // State already been seen -> KO
        if (state.previousStates.contains(this)) {
            return false;
        }
        GoState newState = placeStone(p);
        state.previousStates.add(newState);

        return true;
    }

    public boolean makeMove(Point p) {
        GoState state = new GoState(this);
        state.previousStates.add(state);
        if(p == null) {
            turn = state.turn.opposite();
            // End game with both players passing
            return state.previousStates.contains(this);
        }

        if(!isLegalMove(p)){
            return false;
        }

        placeStone(p);
        turn = state.turn.opposite();
        return state.previousStates.contains(this);
    }

    @Override
    public String toString() {
        return "Black Captured: " + blackCaptured + System.lineSeparator()
                + "White Captured:" + whiteCaptured;
    }
}
