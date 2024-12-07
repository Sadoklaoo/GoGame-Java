package gogame;

public enum BoardSpace {
    EMPTY(null),
    BLACK(Stone.BLACK),
    WHITE(Stone.WHITE);

    public final Stone stone;

    BoardSpace(Stone stone) {
        this.stone = stone;
    }

    public static BoardSpace fromStone(Stone stone) {
        if (stone == null) return EMPTY;
        return stone == Stone.BLACK ? BLACK : WHITE;
    }
}
