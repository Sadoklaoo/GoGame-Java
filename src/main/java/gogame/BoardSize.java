package gogame;

import java.util.stream.Stream;


public enum BoardSize {
    NINE(9),
    THIRTEEN(13),
    NINETEEN(19);

    private final int size;

    BoardSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return size + "x" + size;
    }

    public static BoardSize fromString(String s) {
        return Stream.of(values())
                .filter(boardSize -> boardSize.toString().equals(s))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Board Size is Invalid: " + s));
    }

    public static String[] getStringValues() {
        return Stream.of(values())
                .map(BoardSize::toString)
                .toArray(String[]::new);
    }

}
