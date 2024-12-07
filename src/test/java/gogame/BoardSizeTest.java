package gogame;


import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.*;

public class BoardSizeTest {

    @Nested
    class ValidFromStringTests {

        @ParameterizedTest()
        @CsvSource({ "9x9", "13x13", "19x19" })
        void testFromString(String input) {
            BoardSize expected = switch (input) {
                case "9x9" -> BoardSize.NINE;
                case "13x13" -> BoardSize.THIRTEEN;
                case "19x19" -> BoardSize.NINETEEN;
                default -> throw new IllegalArgumentException("Unexpected input: " + input);
            };
            assertEquals(expected, BoardSize.fromString(input),
                    "fromString should return " + expected + " for input \"" + input + "\"");
        }
    }

    @Nested
    class InvalidFromStringTests {

        @ParameterizedTest()
        @CsvSource({ "10x10", "invalid", "19x20" })
        void testFailingFromString(String input) {
            assertThrows(IllegalArgumentException.class, () -> {
                BoardSize.fromString(input);
            }, "fromString should throw IllegalArgumentException for input \"" + input + "\"");
        }
    }
}