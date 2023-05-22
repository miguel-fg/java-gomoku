
/* AUTHORS
 * Miguel Fierro	100385947 
 * Victor Lino		100378701
*/
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Gomoku {

	public static boolean[][] moveboard = new boolean[15][15]; // the board to store the occupied positions
	public static ArrayList<Match> matches = new ArrayList<Match>(); // array list to store the matches
	public static int[][] winningBoard = new int[15][15];

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		mainMenu(input);

	}

	public static void generatePlayers(Player players[], Scanner input) { // creates new player objects
		String name;

		for (int i = 0; i < 2; i++) {
			System.out.printf("Enter the name of player %d: ", i + 1);
			name = input.next();
			players[i] = new Player(name);

		}
	}

	public static void mainMenu(Scanner input) { // function for the main menu

		int option;

		do {
			System.out.println("---GOMOKU---");
			System.out.println("1. Winners List");
			System.out.println("2. Play");
			System.out.println("3. Exit");
			System.out.println("Choose your option: ");

			option = input.nextInt();

			if (option == 1) { // display winners
				winners(input);
			} else if (option == 2) { // play the game
				play(input);
			} else if (option == 3) { // exit
				System.out.println("See you next time!");
				exit();
				break;
			} else {
				System.out.println("Please choose a valid option");
			}

		} while (true);

	}

	public static void winners(Scanner input) { // display a list of all winners from matches played

		System.out.println("Hall of winners!");

		reader();

		System.out.println();
	}

	public static void play(Scanner input) { // does everything
		Player players[] = new Player[2];
		Player p1, p2;
		int newMoves[][] = new int[15][15];
		moveboard = new boolean[15][15]; // <---clears the board

		Match current = new Match();// create new match
		current.setStart(Calendar.getInstance());// record start of new game
		// need to import the current match value to the nextMoves

		System.out.println("Play GOMOKU");
		generatePlayers(players, input); // players are created
		p1 = players[0];
		p2 = players[1];
		p1.setColour("black");
		p2.setColour("white");
		p1.setMoveBoard(newMoves);
		p2.setMoveBoard(newMoves);
		generateBoard(p1, p2); // new board is created
		firstMove(p1, p2, input); // player1 chooses first three pieces
		secondMove(p1, p2, input); // player2 chooses color or places more stones
		nextMoves(p1, p2, input, current); // rest of the game happens here

	}

	public static void exit() { // quit the game
		System.out.println("Thank you for playing!");
		System.out.println("Don't forget to check the match info at ./src/match.csv");

	}

	public static void generateBoard(Player p1, Player p2) { // GOMOKU board
		String board[][] = new String[15][15]; // 2D 15x15 array
		String empty_cell = "   "; // empty cells are 3 spaces
		ArrayList<int[]> p1_moves;
		ArrayList<int[]> p2_moves;

		System.out.println();
		System.out.print("    "); // board header
		for (int column = 0; column < board.length; column++) { // prints numbers from 1 to 15
			if (column < 9) // if statement to even the spaces of the numbers
				System.out.printf(" %d  ", column + 1);
			else
				System.out.printf("%d  ", column + 1);
		}
		System.out.println();
		System.out.print("   ");
		for (int column = 0; column < board.length; column++) // first horizontal line separation
			System.out.print(" ---");
		System.out.println();

		for (int row = 0; row < board.length; row++) {

			if (row < 9) // if statement to even the spaces of the row number
				System.out.printf("%d  |", row + 1);
			else
				System.out.printf("%d |", row + 1);

			for (int column = 0; column < board[row].length; column++) { // fills the board with empty cells
				p1_moves = p1.getMoves();
				p2_moves = p2.getMoves();

				int currCoord[] = { row + 1, column + 1 }; // current iteration of the for loop in coordinate form

				if (pieceMatch(p1_moves, currCoord)) { // check if the current coordinate is in the list of moves of
														// player 1
					if (p1.getColour() == "black")
						System.out.print(" O " + "|");
					else
						System.out.print(" X " + "|");
				} else if (pieceMatch(p2_moves, currCoord)) { // check if the current coordinate is in the list of moves
																// of player 2
					if (p2.getColour() == "white")
						System.out.print(" X " + "|");
					else
						System.out.print(" O " + "|");
				} else
					System.out.print(empty_cell + "|"); // empty cell

			}
			System.out.println();
			System.out.print("   ");
			for (int column = 0; column < board[row].length; column++) // horizontal dashes
				System.out.print(" ---");
			System.out.println();
		}
		System.out.println();
	}

	public static void firstMove(Player p1, Player p2, Scanner input) { // first player chooses the 3 first pieces
		String name = p1.getName();
		int x, y;
		do {
			System.out.printf("%s, choose the coordinates for the first black piece (eg. 4 7): ", name);
			x = input.nextInt();
			y = input.nextInt();

			if (validateCoord(x, y, moveboard)) {
				p1.addMove(x, y); // adds first black move to player 1 list
				winningBoard[x - 1][y - 1] = 1;
				break;
			}
		} while (true);

		do {
			System.out.printf("%s, choose the coordinates for the first white piece: ", name);
			x = input.nextInt();
			y = input.nextInt();

			if (validateCoord(x, y, moveboard)) {
				p2.addMove(x, y); // adds first white move to player 2 list
				winningBoard[x - 1][y - 1] = 50;
				break;
			}
		} while (true);

		do {
			System.out.printf("%s, choose the coordinates for the second black piece: ", name);
			x = input.nextInt();
			y = input.nextInt();

			if (validateCoord(x, y, moveboard)) {
				p1.addMove(x, y);
				winningBoard[x - 1][y - 1] = 1;
				break;
			}
		} while (true);

		generateBoard(p1, p2); // generates board after the move
	}

	public static boolean pieceMatch(ArrayList<int[]> moves, int currCoord[]) { // returns true if the coordinate is in
																				// the list of the player
		int coord[];

		for (int i = 0; i < moves.size(); i++) { // iterate over the player's list of moves
			coord = moves.get(i);

			if (coord[0] == currCoord[0] && coord[1] == currCoord[1])
				return true;
		}

		return false;
	}

	public static void secondMove(Player p1, Player p2, Scanner input) {

		String name = p2.getName();
		int choice;
		do {
			System.out.printf("%s, choose between 3 options: \n", name);
			System.out.println("1. Play as black"); // just go to play
			System.out.println("2. Play as white, and place another white stone"); // change player color to white and
																					// add
																					// another white piece
			System.out.println(
					"3. Place two more stones, one white and one black, and let " + p1.getName()
							+ " choose the color.");
			// add two more stones (b and w) and move to player 1 to let him chose his color
			// again
			System.out.println("Choose your option: ");
			choice = input.nextInt();

			if (choice == 1) {
				secondMoveOne(p1, p2, input);
				break;
			}

			else if (choice == 2) {
				secondMoveTwo(p1, p2, input);
				break;
			}

			else if (choice == 3) {
				secondMoveThree(p1, p2, input);
				break;
			} else
				System.out.println("Please choose a valid option");
		} while (true);

	}

	public static void secondMoveOne(Player p1, Player p2, Scanner input) { // swaps move lists. p1 plays white and p2
																			// plays black
		System.out.println("You have chosen option 1");
		swapPlayers(p1, p2); // calls for the swap
		p1.setColour("white");
		p2.setColour("black");
		p1.setNext_turn(true); // next turn is the white pieces
		System.out.printf("%s plays white (X)\n%s plays black (O)\n", p1.getName(), p2.getName());
		generateBoard(p1, p2);
	}

	// move list stays the same
	public static void secondMoveTwo(Player p1, Player p2, Scanner input) { // p1 plays black and p2 plays white
		int x, y;
		System.out.println("You have chosen option 2");

		String name = p2.getName();
		do {
			System.out.printf("%s, choose the coordinates for the next white piece: ", name);
			x = input.nextInt();
			y = input.nextInt();

			if (validateCoord(x, y, moveboard)) {
				p2.addMove(x, y);
				winningBoard[x - 1][y - 1] = 50;
				break;
			}
		} while (true);
		p1.setNext_turn(true); // next turn is the black pieces
		System.out.printf("%s plays white (X)\n%s plays black (O)\n", p2.getName(), p1.getName());
		generateBoard(p1, p2);
	}

	// add two more stones (1 b and 1 w) and prompt white to change color or not
	public static void secondMoveThree(Player p1, Player p2, Scanner input) { //
		System.out.println("You have chosen option 3");
		int x, y;
		String name = p2.getName();
		do { // next white piece
			System.out.printf("%s, choose the coordinates for the next white piece: ", name);
			x = input.nextInt();
			y = input.nextInt();

			if (validateCoord(x, y, moveboard)) {
				p2.addMove(x, y);
				winningBoard[x - 1][y - 1] = 50;
				break;
			}
		} while (true);

		do { // next black piece
			System.out.printf("%s, choose the coordinates for the next black piece: ", name);
			x = input.nextInt();
			y = input.nextInt();

			if (validateCoord(x, y, moveboard)) {
				p1.addMove(x, y);
				winningBoard[x - 1][y - 1] = 1;
				break;
			}
		} while (true);

		generateBoard(p1, p2); // shows current state of the board before player 1 chooses the colour

		chooseColour(p1, p2, input); // player 1 chooses the colour
		generateBoard(p1, p2);
	}

	// swaps the data between p1 moves arrlist and p2 moves arrlist
	public static void swapPlayers(Player p1, Player p2) {

		ArrayList<int[]> temp = new ArrayList<int[]>(); // empty ArrayList
		temp = p1.getMoves();// copy p1 values to temp var
		p1.setMoves(p2.getMoves()); // put p2 data into p1
		p2.setMoves(temp); // put temp data into p2

		int tempBoard[][] = new int[15][15]; // same algorithm for the players' move boards
		tempBoard = p1.getMoveBoard();
		p1.setMoveBoard(p2.getMoveBoard());
		p2.setMoveBoard(tempBoard);
	}

	public static void chooseColour(Player p1, Player p2, Scanner input) { // first player chooses the colour
		String name = p1.getName();
		int choice;
		do { // menu starts
			System.out.printf("%s, choose the colour you want to play as: \n", name);
			System.out.println("1. Play as black (0)");
			System.out.println("2. Play as white (X)");
			choice = input.nextInt();

			if (choice == 1) {
				p1.setColour("black");
				p2.setColour("white");
				p2.setNext_turn(true); // next turn is whoever gets white
				System.out.printf("%s plays white (X)\n%s plays black (O)\n", p2.getName(), p1.getName());
				break;
			} else if (choice == 2) {
				p1.setColour("white");
				p2.setColour("black");
				swapPlayers(p1, p2);
				p1.setNext_turn(true);
				System.out.printf("%s plays white (X)\n%s plays black (O)\n", p1.getName(), p2.getName());
				break;
			} else
				System.out.println("Please choose a valid option");

		} while (true);
	}

	public static boolean validateCoord(int x, int y, boolean[][] board) { // validates the user's coordinate selection

		if (x > 15 || x < 1 || y > 15 || y < 1) { // coordinate outside the board's limits

			System.out.println("Please choose a valid coordinate");
			return false;
		}

		if (board[x - 1][y - 1] == true) { // coordinate on an occupied cell

			System.out.println("Invalid input, this position is already occupied by another piece.");
			System.out.println("Please choose a valid coordinate");
			return false;
		}

		else {
			board[x - 1][y - 1] = true; // universal board is updated
			return true;
		}
	}

	public static void nextMoves(Player p1, Player p2, Scanner input, Match current) {

		do {
			String name = "";
			String colour = "";
			int x, y;
			if (p1.isNext_turn()) { // P1 next turn
				name = p1.getName();
				colour = p1.getColour();
				do {
					System.out.printf("%s, choose the coordinates for the next %s piece: ", name, colour);
					x = input.nextInt();
					y = input.nextInt();

					if (validateCoord(x, y, moveboard)) { // coordinate is validated
						p1.addMove(x, y);
						if (colour == "black")
							winningBoard[x - 1][y - 1] = 1;
						else
							winningBoard[x - 1][y - 1] = 50;
						break;

					}
				} while (true);
				generateBoard(p1, p2);
				p1.setNext_turn(false); // next turn is set
				p2.setNext_turn(true);
			} else if (p2.isNext_turn()) { // P2 next turn
				name = p2.getName();
				colour = p2.getColour();
				do {
					System.out.printf("%s, choose the coordinates for the next %s piece: ", name, colour);
					x = input.nextInt();
					y = input.nextInt();

					if (validateCoord(x, y, moveboard)) { // coordinate is validated
						p2.addMove(x, y);
						if (colour == "black")
							winningBoard[x - 1][y - 1] = 1;
						else
							winningBoard[x - 1][y - 1] = 50;
						break;
					}
				} while (true);
				generateBoard(p1, p2);
				p2.setNext_turn(false); // next turn is set
				p1.setNext_turn(true);
			}

			int winner_val;
			winner_val = checkWinningBoard(); // check the board for winning combinations
			if (winner_val != 0) { // winner found
				if (winner_val == 1) { // black piece winner, Player is set as winner
					if (p1.getColour() == "black") {
						p1.setWinner(true);
						p2.setWinner(false);
					} else {
						p1.setWinner(false);
						p2.setWinner(true);
					}
				} else {
					if (p1.getColour() == "white") { // white piece winner, Player is set as winner
						p1.setWinner(true);
						p2.setWinner(false);
					} else {
						p1.setWinner(false);
						p2.setWinner(true);
					}
				}

				break;
			}

		} while (true);

		// winning message / match info is saved
		if (p1.isWinner()) {
			saveEnd(current, p1, p2, p1);
			System.out.printf("The winner is %s!!!\n", p1.getName());
		}

		else if (p2.isWinner()) {
			saveEnd(current, p2, p2, p2);
			System.out.printf("The winner is %s!!!\n", p2.getName());
		}
	}

	private static int checkWinningBoard() {
		for (int row = 0; row < winningBoard.length; row++) {
			for (int column = 0; column < winningBoard[row].length; column++) {
				if (winningBoard[row][column] != 0) { // check if piece on the board is the start of any winning
														// combinations

					// a player has a won
					if (hSum(row, column) == 1)
						return 1;
					else if (hSum(row, column) == 2)
						return 2;
					else if (vSum(row, column) == 1)
						return 1;
					else if (vSum(row, column) == 2)
						return 2;
					else if (d1Sum(row, column) == 1)
						return 1;
					else if (d1Sum(row, column) == 2)
						return 2;
					else if (d2Sum(row, column) == 1)
						return 1;
					else if (d2Sum(row, column) == 2)
						return 2;
				}
			}
		}
		return 0;
	}

	public static int hSum(int x, int y) { // looks for horizontal winning combinations
		if (y > 10)
			return 0;

		int sum = 0;

		for (int i = 0; i < 5; i++, y++) {
			sum += winningBoard[x][y];
		}
		if (sum == 5) { // black wins
			return 1;
		} else if (sum == 250) { // white wins
			return 2;
		}

		return 0;
	}

	public static int vSum(int x, int y) { // looks for vertical winning combinations
		if (x > 10)
			return 0;

		int sum = 0;

		for (int i = 0; i < 5; i++, x++) {
			sum += winningBoard[x][y];
		}

		if (sum == 5) { // black wins
			return 1;
		} else if (sum == 250) { // white wins
			return 2;
		}
		return 0;
	}

	public static int d1Sum(int x, int y) { // looks for \ diagonal winning combinations
		if (y > 10 || x > 10) {
			return 0;
		}

		int sum = 0;

		for (int i = 0; i < 5; i++, x++, y++) {
			sum += winningBoard[x][y];
		}

		if (sum == 5) { // black wins
			return 1;
		} else if (sum == 250) { // white wins
			return 2;
		}
		return 0;
	}

	public static int d2Sum(int x, int y) { // looks for / diagonal winning combinations
		if (y > 10 || x < 4) {
			return 0;
		}

		int sum = 0;

		for (int i = 0; i < 5; i++, x--, y++) {
			sum += winningBoard[x][y];
		}

		if (sum == 5) { // black wins
			return 1;
		} else if (sum == 250) { // white wins
			return 2;
		}
		return 0;
	}

	private static void saveEnd(Match current, Player p1, Player p2, Player winner) {

		current.setFinish(Calendar.getInstance()); // sets now as time match ended
		current.setDuration(); // this calculates the end of the match
		current.setColour(winner.getColour());// save player color
		current.setWinner(winner.getName());// save player name
		current.setP1(p1.getName());
		current.setP2(p2.getName());
		current.setMoves((p1.getMoves().size()) + (p2.getMoves().size()));// get the total number of moves in the match
		matches.add(current);// store current match into arraylist matches
		printer(matches); // records everything when the match ends
	}

	// function to print the match data
	public static void printer(ArrayList<Match> matches) { // get data from the arraylist of matches

		// create a loop to print data from objects in the matches array

		try {

			FileWriter fw = new FileWriter("match.csv"); // destination file
			PrintWriter pw = new PrintWriter(fw);
			Match data;
			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, HH:mm");
			long time, seconds, minutes, hours, days;

			// create loop here

			// now i just need to this dis loop
			for (int i = 0; i < matches.size(); i++) {
				data = matches.get(i); // current match
				time = data.getDuration();// get duration into time var
				seconds = time / 1000;
				minutes = seconds / 60; // manipulate time var to display minutes,
				hours = minutes / 60; // hours,
				days = hours / 24; // and days

				// file printing format
				pw.printf("%d\n", i + 1);
				pw.println("New match started on: " + sdf.format(data.getStart().getTime()));
				pw.println("Ended on: " + sdf.format(data.getFinish().getTime()));
				pw.println("Duration of the match was: " + days + " days " + hours + " hours and " + minutes
						+ " minutes " + seconds + " seconds.");
				pw.println("Total moves: " + (data.getMoves() - 1));
				pw.println("The winner was " + data.getWinner() + ", playing " + data.getColour());
			}

			fw.close();// close the file

		} catch (Exception ex) {
			System.out.println("error: " + ex.getMessage());
		}

	}

	public static void reader() { // reads the file and extracts the player and colour information
		try {
			String sourceFilename = "match.csv"; // source file

			File f = new File(sourceFilename);
			Scanner readFile = new Scanner(f);

			String data;
			String elements[];
			int counter = 1;
			while (readFile.hasNextLine()) { // skips 5 lines per entry in the file
				for (int i = 0; i < 5; i++) {
					readFile.nextLine();
				}
				data = readFile.nextLine();
				elements = data.split(" ");
				System.out.printf("%d\t%s %s\n", counter, elements[3], elements[5]); // prints name and piece colour of
																						// winner
				counter++;
			}

			readFile.close();

		} catch (Exception ex) { // something went wrong while reading the file (probably file doesn't exist yet)
			System.out.println("error: " + ex.getMessage());
			return; // returns to main menu
		}
	}

}
