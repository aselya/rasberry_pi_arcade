package arcade.cs622;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")

/**
 * Contains the board for the snake game.
 * uses the paintComponent to render the board snake and food components as determined 
 * by the snakeMain class
 */


public class SnakeBoard extends JPanel
{
	
	//i couldn't get the background image to work but I'm leaving this to try later
    public static Image image = Toolkit.getDefaultToolkit().getImage("../background.png");
	
	@Override
	//paints the snake on the board
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//g.drawImage(image, 0, 0, this);

		SnakeMain snake = SnakeMain.snake;

		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, 800, 700);

		g.setColor(Color.RED);
		//makes the body alternate colors for the snake
		for (Point point : snake.snakeBodyParts)
		{
			if (g.getColor() == Color.RED){
				g.setColor(Color.BLUE);
			g.fillArc(point.x * SnakeMain.SCALE, point.y * SnakeMain.SCALE, SnakeMain.SCALE, SnakeMain.SCALE, 360, 360);
			}else{
				g.setColor(Color.RED);
				g.fillArc(point.x * SnakeMain.SCALE, point.y * SnakeMain.SCALE, SnakeMain.SCALE, SnakeMain.SCALE, 360, 360);

			}
			
			//g.fillRect(point.x * SnakeMain.SCALE, point.y * SnakeMain.SCALE, SnakeMain.SCALE, SnakeMain.SCALE);
		}
		//makes the snake head orange so it's easier to tell apart
		g.setColor(Color.ORANGE);
		g.fillArc(snake.snakeHead.x * SnakeMain.SCALE, snake.snakeHead.y * SnakeMain.SCALE, SnakeMain.SCALE, SnakeMain.SCALE, 300, 360);
		
		//makes snake food green
		g.setColor(Color.GREEN);
		
		g.fillRect(snake.snakeFood.x * SnakeMain.SCALE, snake.snakeFood.y * SnakeMain.SCALE, SnakeMain.SCALE, SnakeMain.SCALE);
		
		String string = "Score: " + snake.score + ", Length: " + snake.tailLength + ", Time: " + snake.time / 20;
		
		//sets the text at top of board and prints it in white
		g.setColor(Color.white);
		g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);

		string = "The Game is Over!";

		if (snake.gameOver)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.screenDimension.getHeight() / 4);
		}

		string = "The is Paused!";

		if (snake.paused && !snake.gameOver)
		{
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.screenDimension.getHeight() / 4);
		}
	}
}
