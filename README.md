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
   git clone https://github.com/yourusername/GoGame-Java.git
   cd GoGame-Java
   ```
2. **Compile the Project: Use javac to compile the project:**:
```bash
   javac -cp .:junit5all.jar:checkthat.jar ./src/*.java ./tests/*.java
   ```
2. **Run the Test Suite: Execute all tests using JUnit:**:
```bash
java -cp .:junit5all.jar:checkthat.jar org.junit.platform.console.ConsoleLauncher --scan-class-path
   ```




   
