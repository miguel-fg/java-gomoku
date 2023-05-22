/* AUTHORS
 * Miguel Fierro	100385947 
 * Victor Lino		100378701
*/

import java.util.Calendar;

public class Match {

	private int matchNo;
	private String winner;
	private String p1;
	private String p2;
	private String colour;
	private Calendar start;
	private Calendar finish;
	private long duration;
	private long moves;
	
	
	public String getP1() {
		return p1;
	}

	public void setP1(String p1) {
		this.p1 = p1;
	}

	public String getP2() {
		return p2;
	}

	public void setP2(String p2) {
		this.p2 = p2;
	}

	public Match() {
		winner = "none";
		colour = "none";
		start = null;
		finish = null;
		duration = 0;

	}

	public long getMoves() {
		return moves;
	}

	public void setMoves(long moves) {
		this.moves = moves;
	}

	public int getMatchNo() {
		return matchNo;
	}

	public void setMatchNo(int matchNo) {
		this.matchNo = matchNo;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	//starting date of the match
	public Calendar getStart() { 
		return start;
	}

	public void setStart(Calendar start) {
		this.start = start;
	}

	//end date of the match
	public Calendar getFinish() {
		return finish;
	}

	public void setFinish(Calendar finish) {
		this.finish = finish;
	}
	
	//duration of the match
	public long getDuration() {
		return duration;
	}

	public void setDuration() {
		this.duration = getFinish().getTimeInMillis() - getStart().getTimeInMillis();
	}


}