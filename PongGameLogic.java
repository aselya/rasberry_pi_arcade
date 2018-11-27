package arcade.cs622;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * The class contains the game logic for the pong game
 */

public class PongGameLogic implements ActionListener, KeyListener
{
	public PlayerProfileNode pongPlayer = new PlayerProfileNode(); 
	//creating empty node prevents null pointer exception
	//pongPlayer value is reassigned by constructor
	public int losses = 0;
	public int wins = 0;
	
	//keys pressed vars
	public boolean w;
	public boolean s;
	public boolean up;
	public boolean down;

	//vars to access classes needed for the game
	public static PongGameLogic pong;
	public PongBoardRender renderer;
	public PongBall ball;
	public PongPaddle player1;
	public PongPaddle player2;



	//sets default enum value to mainMenu 
	PongGameStatus pongGameStatus =  PongGameStatus.MainMenu;

	//player vars
	public int playerWon; 
	public int scoreLimit = 3;
	

	public Random random;
	
	//jframe vars s
	public JFrame jframe;
	public int width = 700;
	public int height = 700;
	
	
	//constructor that sets up JFrame, assigns player values, starts times
	public PongGameLogic(PlayerProfileNode player)
	{
		//pulls in player profile
		pongPlayer = player;
		
		//prints to console used for debugging
			losses = pongPlayer.getPongLossesCount();
			System.out.println("player Losses:"+ losses); 
			wins = pongPlayer.getPongWinsCount();
			System.out.println("player wins:"+ wins);

		
		Timer timer = new Timer(30, this);
		random = new Random();

		//jframe specifications
		jframe = new JFrame(pongPlayer.getGamer()+": Pong");
		renderer = new PongBoardRender();
		jframe.setSize(width + 15, height + 35);
		jframe.setVisible(true);
		jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jframe.add(renderer);
		jframe.addKeyListener(this);

		timer.start();
		
	}
	
	
	//starts the game
	public void start()
	{
		pongGameStatus = PongGameStatus.Playing; //updates enum to reflect playing
		//assigns new player paddles and ball
		player1 = new PongPaddle(this, 1);
		player2 = new PongPaddle(this, 2);
		ball = new PongBall(this, pongPlayer);
	}
	
	//updates the game for each cycle
	public void update(PlayerProfileNode player)
	{
		
		//first check if the score limit is reached 
		if (player1.score >= scoreLimit)
		{
			playerWon = 1;
			//incriments the number of wins and adds them to the player node
			wins ++;
			player.setPongWinsCount(wins);

			
			//prints to console used for debugging
				System.out.println("player:" + player.getGamer());
				System.out.println("player Losses:"+ player.getPongLossesCount());
				System.out.println("player wins:"+ player.getPongWinsCount());
			
			pongGameStatus = PongGameStatus.GameOver;
			//displays the player's statistics for the pong game
			JOptionPane.showMessageDialog(null, "player: " + player.getGamer()+ "\nPong Wins: "+ player.getPongWinsCount() +"\nPong Losses:"+ player.getPongLossesCount()+"\nLongest Rally: "+ player.getMaxRally());
		}
		//Checks if the score limit is reached by player 2
		if (player2.score >= scoreLimit)
		{
			playerWon = 2;
			losses ++;
			pongGameStatus = PongGameStatus.GameOver;
			player.setPongLossesCount(losses);
			
			//prints to console used for debugging
				System.out.println("player:" + player.getGamer());
				System.out.println("player Losses:"+ player.getPongLossesCount());
				System.out.println("player wins:"+ player.getPongWinsCount());
			
			//displays the player's statistics for the pong game
			JOptionPane.showMessageDialog(null, "player: " + player.getGamer()+ "\nPong Wins: "+ player.getPongWinsCount() +"\nPong Losses:"+ player.getPongLossesCount()+"\nLongest Rally: "+ player.getMaxRally());

					
		}
		
		//checks if a key is pressed and updates the values for the ball
		if (w){
			player1.move(true);
			}
		
		if (s){
			player1.move(false);
			}

		if (up){
			player2.move(true);
			}
		
		if (down){
			player2.move(false);
			}
		
		ball.update(player1, player2);
	}

	public void render(Graphics2D g)
	{
		g.setColor(Color.BLACK); //got to go black for the classic pong look!
		g.fillRect(0, 0, width, height);//fills the screen starting in left corner
		
		//paints the values for the main menu when enum value is main menu
		if (pongGameStatus == PongGameStatus.MainMenu)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", 1, 50));

			g.drawString("PONG", width / 2 - 75, 50);

			g.setFont(new Font("TimesRoman", 1, 25));

			g.drawString("Press Space to Play", width / 2 - 150, height / 2 - 25);
			//displays number of points needed to win based on scoreLimit var
			g.drawString("First to " + scoreLimit + " wins!", width / 2 - 140, height / 2 + 70);
		}

		//paints the values for pause screen when enum value is Paused
		if (pongGameStatus == PongGameStatus.Paused)
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", 1, 50));
			g.drawString("PAUSED", width / 2 - 100, height / 2 - 20);
		}
		//paints the game board when the game is being played or paused
		if (pongGameStatus == PongGameStatus.Paused || pongGameStatus == PongGameStatus.Playing)
		{
			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(2f));
			g.setFont(new Font("TimesRoman", 1, 50));
			g.drawString(String.valueOf(player1.score), width / 2 - 90, 50);
			g.drawString(String.valueOf(player2.score), width / 2 + 65, 50);
			
			g.drawString("Rally: "+ball.rallyLength, width/2 - 100, height/5 );
			//paint the padells and ball
			player1.render(g);
			player2.render(g);
			ball.render(g);
		}
		//paints game board to reflect the player who has won
		if (pongGameStatus == PongGameStatus.GameOver)
		{ 
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString("PONG", width / 2 - 75, 50);

			if (playerWon == 2)
			{
				//pongPlayer.setPongLossesCount(pongPlayer.getPongLossesCount()+1);

				g.drawString("You been defeated!", width / 2 - 170, 200);
				//increases the number of losses the pong player has saved
			}
			else
			{
				//pongPlayer.setPongWinsCount(pongPlayer.getPongWinsCount()+1);

				g.drawString("Player 2 Wins!", width / 2 - 165, 200);
				//increases the number of losses the pong player has saved
			}

			g.setFont(new Font("Arial", 1, 30));

			g.drawString("Press Space to Play Again", width / 2 - 185, height / 2 - 25);
		}
	}
	//updates after each action taken
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (pongGameStatus == PongGameStatus.Playing)
		{
			

			update(pongPlayer);
		}
		renderer.repaint();
	}

	//key pressed event calls to move paddels 
	@Override
	public void keyPressed(KeyEvent e)
	{
		int id = e.getKeyCode(); //gets the key pressed

		if (id == KeyEvent.VK_W)
		{
			w = true;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = true;
		}
		else if (id == KeyEvent.VK_UP)
		{
			up = true;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			down = true;
		}
		
		//the space bar is pressed to pause game or restart it
		else if (id == KeyEvent.VK_SPACE)
		{
			//starts game if space bar is pressed in main menu or end of game screen
			if (pongGameStatus == PongGameStatus.MainMenu || pongGameStatus == PongGameStatus.GameOver)
			{
				start(); 
			}
			else if (pongGameStatus == PongGameStatus.Paused)
			{
				pongGameStatus = PongGameStatus.Playing;
			}
			else if (pongGameStatus == PongGameStatus.Playing)
			{
				pongGameStatus = PongGameStatus.Paused;
			}
		}
	}
	
	//reverses the keypressed values when they are released
	@Override
	public void keyReleased(KeyEvent e)
	{
		int id = e.getKeyCode();

		if (id == KeyEvent.VK_W)
		{
			w = false;
		}
		else if (id == KeyEvent.VK_S)
		{
			s = false;
		}
		else if (id == KeyEvent.VK_UP)
		{
			up = false;
		}
		else if (id == KeyEvent.VK_DOWN)
		{
			down = false;
		}
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
}

// end the PongGameLogic Class
