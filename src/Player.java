/* AUTHORS
 * Miguel Fierro	100385947 
 * Victor Lino		100378701
*/

import java.util.ArrayList;

public class Player {
	private String name;
	private boolean winner;
	private boolean next_turn;
	private String colour;
	private ArrayList<int[]> moves = new ArrayList<int[]>();
	private int moveBoard[][] = new int[15][15];
	
	public Player(String name) {
		this.name = name;
		colour = "None";
		winner = false;
		next_turn = false;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public String getName() {
		return name;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public boolean isNext_turn() {
		return next_turn;
	}

	public void setNext_turn(boolean next_turn) {
		this.next_turn = next_turn;
	}
	
	public void addMove(int x, int y) { //adds a new move coordinate to the player's list
		int coord[] = {x, y};
		moves.add(coord);
		this.moveBoard[x-1][y-1] = 1;
	}
	
	public ArrayList<int[]> getMoves() { //returns the moves as an ArrayList of arrays
		return moves;
	}
	
	public void setMoves(ArrayList<int[]> moves) { //sets the current player moves arraylist as the one in the parameter
		this.moves = moves;
	}
	
	public void setMoveBoard(int board[][]) { //replaces the move board (in case of player colour swapping)  
		this.moveBoard = board;
	}
	
	public int[][] getMoveBoard(){ //returns current move board
		return moveBoard;
	}
	
}
