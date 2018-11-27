package arcade.cs622;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class playerLL implements Serializable{
	/**
	 * Class colntains the linked list storing all player profiles
	 */
	private static final long serialVersionUID = 1L;
	private PlayerProfileNode head = null;
	
	//saves the player LL to the designated file
	void savePlayerLL() throws IOException{
		ObjectOutputStream out =  new ObjectOutputStream(new FileOutputStream("../PlayerLL_Saved"));
		out.writeObject(getHead());
		out.close();
	}
	
	
	//returns the player node selected
	public PlayerProfileNode findPlayer(String gamerName, String password) throws IOException{
		//checks to make sure there is a head if not sets new player as head
		if (head == null){
			return addToTail(gamerName, password);
		}else if(head.getGamer().equals(gamerName) ){
			if(head.getPassword().equals(password)){
			return head;}
			}else{
				System.out.println("password and user name donet match");
				JOptionPane optionPane = new JOptionPane("Password and username don't match");
				optionPane.setVisible(true);
				return null;
			}
		
		PlayerProfileNode curr = head;
		Boolean done = false;
		
		while (!done){
			if(curr.getGamer().equals(gamerName)){
				done = true;
				return curr;
			}else if (curr.getNext() != null){
				curr = curr.getNext();
			}else {
				done = true;
				addToTail(gamerName, password);	
			}
		}
		return head;
	}
	
	
	
	//adds new profile
	public PlayerProfileNode addToTail (String gamerName, String password) throws IOException{
		Boolean match = true;
		if (head == null){
			head = makeNewNode(gamerName, password);
			savePlayerLL();

		}else if(head.getGamer().equals(gamerName)){
			if (head.getPassword().equals(password)){
			return head;
			}
			else {
				System.out.println("password and user name don't match");
				match = false;
				
				JOptionPane.showMessageDialog(null, "Your Password and Username don't match");

				return null;
			}
		}
		Boolean done = false;
		PlayerProfileNode curr = head;
		if(match = true){
		while (!done){
			if (curr.getGamer().equals(gamerName)){
				done = true;
				return curr;
			}else if (curr.getNext() != null){
				curr = curr.getNext();
				
			}else{
				
				PlayerProfileNode newPlayer = makeNewNode(gamerName, password);
				curr.setNext(newPlayer);
				done = true;
			}	
				}		
		return curr.getNext();}
		else{ 
			return null;
			}
		}
		
		
	//makes new node and sets values
	public PlayerProfileNode makeNewNode(String gamerName, String password){
		PlayerProfileNode newPlayer = new PlayerProfileNode();
		newPlayer.setGamer(gamerName);
		newPlayer.setPassword(password);
		return newPlayer;
		}
	
	
//prints list
public String toString (){
	String str = "";
	PlayerProfileNode curr = head;
	boolean done = false;
	
	if (head == null){
		str = "No list initialized";
		System.out.println(str);
		return str;
	}
	
	while (!done){
		str = str+"\nplayer: "+ curr.getGamer();
		if (curr.getNext() == null){
			done = true;
		}else{
			curr = curr.getNext();
		}
	} 
	
	System.out.println(str);
	return str;
	
}



//getters and setters
public PlayerProfileNode getHead() {
	return head;
}

public void setHead(PlayerProfileNode head) {
	this.head = head;
}

}//end class
