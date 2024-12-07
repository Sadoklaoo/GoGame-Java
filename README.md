# GoGame-Java

## Description
GoGame-Java is a Java-based implementation of the ancient board game *Go*. This project simulates the rules and mechanics of Go, including move validation, capturing stones, scoring, and handling endgame scenarios. It includes a robust testing suite using JUnit to ensure reliable gameplay functionality.

---

## Features
- **Board Mechanics**: Implements an NxN Go board with stone placement and turn management.
- **Move Validation**: Ensures legal moves based on Go rules, including prevention of suicidal moves and repeated states (Ko rule).
- **Capture Detection**: Automatically detects and handles captures during gameplay.
- **Scoring System**: Tracks captured stones for both players.
- **Testing**: Comprehensive test suite to verify functionality and edge cases.

---

## How to Run
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Sadoklaoo/GoGame-Java.git
   cd GoGame-Java
   ```
2. **Compile the Project: Use javac to compile the project**:
```bash
   javac -cp .:junit5all.jar:checkthat.jar ./src/*.java ./tests/*.java
   ```
3. **Run the Test Suite: Execute all tests using JUnit**:
```bash
java -cp .:junit5all.jar:checkthat.jar org.junit.platform.console.ConsoleLauncher --scan-class-path
   ```

## Project Structure
```bash
GoGame-Java/
│
├── src/
│   ├── Board.java         # Represents the Go board
│   ├── GoState.java       # Handles game state and logic
│   ├── BoardSize.java     # Manages board dimensions
│   ├── BoardSpace.java    # Represents spaces on the board (stone or empty)
│   ├── GoPane.java        # GUI component for the Go game
│   ├── GoSwing.java       # Main class for running the game with Swing GUI
│   ├── Point.java         # Represents points on the board
│   └── Stone.java         # Enum for BLACK, WHITE, and EMPTY stones

│
├── tests/
│   ├── GoGameTestSuite.java   # Runs all unit tests
│   ├── GoStateTest.java       # Tests GoState functionality
│   └── BoardTest.java         # Tests Board mechanics
│
├── .gitignore            # Specifies files to ignore in Git
└── README.md             # Project description and instructions
   ```

   ## Contributing
   Contributions are welcome! If you'd like to improve the project or fix a bug:
1. Fork the repository.
2. Create a new branch for your feature:
```bash
 git checkout -b feature-name
   ```
3. Commit your changes:
```bash
git commit -m "Add your message here"
   ```
4. Push to your branch:
```bash
git push origin feature-name
   ```
5. Open a Pull Request.





