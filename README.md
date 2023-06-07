## Gomoku CLI Game

This is a command-line implementation of the classic board game Gomoku, also known as Five in a Row. The objective of the game is to be the first player to align five stones in a row, either horizontally, vertically, or diagonally.

## Game Features

- `Two player game`: Play against a friend in a turn-based match
- `Standard board size`: The game board has fixed 15x15 size
- `Save game stats`: The game saves the statistics of each game session to a .CSV file

## Prerequisites

- Java Development Kit (JDK) 8 or above
- Command-line interface (CLI) capable of running Java programs.

## How to run

1. Clone the repository or download the source code files.
2. Open a terminal and navigate to the project directory.
3. Compile the java source files by running the following commands:
```
javac Gomoku.java
javac Player.java
javac Match.java
```
4. Run the game using the following command:
```
java Gomoku
```

## Game instructions
1. The game is played on a 15x15 square board.
2. The first player chooses the first 3 moves:
    - The coordinates of the first Black piece, denoted by `O` as a number tuple separated by a space `eg. 4 7`.
    - The coordinates of the first White piece, denoted by `X`.
    - The coordinates of the second Black piece.
3. The second player then chooses one of 3 options:
    - Play as Black `O`
    - Play as White `X`, and place another white piece.
    - Place two more pieces, one White `X` and one Black `O` and let the first player choose the color.
4. Once the choice is made, the players take turns placing their pieces on the empty intersections of the board.
5. The first player to align five pieces in a row wins the game.
6. If the board is filled completely without a winner, the game is considered a draw.

## Saving game statistics
- After each game session, the game with save the following statistics:
    - Match start date and time
    - Match end date and time
    - Duration of the match in `DD:MM:mm:ss` format
    - Total moves
    - Winner name and colour
- The output `.csv` file can be found at `./src/match.csv` and its content can be accessed through the in-game menu

## Areas of improvement
- The idea of developing a JavaFX GUI is being explored
- Known bug when playing more than one game without leaving the menu: The previous game's moves sometimes aren't cleared from memory, causing accidental wins.
- The match.csv sometimes cannot be read. The cause is still being identified.  
