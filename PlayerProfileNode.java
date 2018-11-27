package arcade.cs622;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerProfileNode implements Serializable {
	 /**
	 * This class contains the player profile information including passwords, login name and scores
	 * Each node is designed to serve as part of a singly Linked list of profiles
	 */
	
	//linked list vars
	 private static final long serialVersionUID = 1L;
	 private PlayerProfileNode next; 
	//player Specific vars
	 private String player;
	 private String password;

	//game specific vars
     private ArrayList<Integer> snakeArray = new ArrayList<Integer>(); 
	 private int pongWinsCount =0;
	 private int pongLossesCount = 0;
	 private int maxRally =0;
	 
	 public  PlayerProfileNode(){
		 //constructor
		 
	 }
	 //adds new score to snake array and sorts so scores are in descending order
	 public void addScoreToSnakeArray(int score ){
		 snakeArray.add(score);
	     Collections.sort(snakeArray, Collections.reverseOrder()); 
	 }

	 //prints up to 5 top snake scores
	 public String printSnake(){
		String str = player +"'s Snake Scores";
		for (int x=0; x<snakeArray.size(); x++)
			if (x< 5){
		    str = str+"\n"+((x+1)+". score: "+snakeArray.get(x));
			}
		 return str;
	 }
//setters and getters

	public PlayerProfileNode getNext() {
		return next;
	}

	public void setNext(PlayerProfileNode next) {
		this.next = next;
	}

	public String getGamer() {
		return player;
	}



	public void setGamer(String gamer) {
		this.player = gamer;
	}


	public int maxRally() {
		return maxRally;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPongLossesCount() {
		return pongLossesCount;
	}

	public void setPongLossesCount(int pongLossesCount) {
		this.pongLossesCount = pongLossesCount;
	}

	public int getPongWinsCount() {
		return pongWinsCount;
	}

	public void setPongWinsCount(int count) {
		this.pongWinsCount = pongWinsCount + count;
	}

	public int getMaxRally() {
		return maxRally;
	}

	public void setMaxRally(int maxRally) {
		this.maxRally = maxRally;
	}

	public ArrayList<Integer> getSnakeArray() {
		return snakeArray;
	}

	public void setSnakeArray(ArrayList<Integer> snakeArray) {
		this.snakeArray = snakeArray;
	}
	 
	 
}// end class playerProfileNode
