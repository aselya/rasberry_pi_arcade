package arcade.cs622;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class SnakeMain implements ActionListener, KeyListener 
{
	//initiailizes the snake, player vars and the board
	public static SnakeMain snake;
	public PlayerProfileNode snakePlayer = null;
	public JFrame jframe;
	public SnakeBoard snakeGameBoard;
	
	//values to start the game are set
	public int ticks = 0;
	public int direction = DOWN;
	public int score = 0;
	public int tailLength = 10;
	public int time = 0;
	public Timer timer = new Timer(20, this);
	public boolean gameOver = false;
	public boolean paused = false;
	
		//head and food are set
	public Point snakeHead;
	public Point snakeFood;
	
	//snake body is stored as an array list of points 
	public ArrayList<Point> snakeBodyParts = new ArrayList<Point>();

	//directions are given final ints to prevent changes
	public static final int UP = 0;  
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int SCALE = 10;
	

	public Random random;

	public Dimension screenDimension;
	
	//starts the game setting the snake in the upper left corner of the snakeBoard
	//with the snake moving down
	//resets values
	public void startGame()
	{
		gameOver = false;
		paused = false;
		time = 0;
		score = 0;
		tailLength = 20;
		ticks = 0;
		direction = DOWN;
		snakeHead = new Point(0, -1);
		random = new Random();
		snakeBodyParts.clear();
		snakeFood = new Point(random.nextInt(79), random.nextInt(66));
		timer.start();
	}

	//snake game constructor
	//pulls in the player profile builds board and starts the game
	public SnakeMain( PlayerProfileNode player)
	{
		snakePlayer = player;
		//snake = SnakeMain();
		screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		jframe = new JFrame("Snake");
		jframe.setVisible(true);
		jframe.setSize(805, 700);
		jframe.setResizable(false);
		jframe.setLocation(screenDimension.width / 2 - jframe.getWidth() / 2, screenDimension.height / 2 - jframe.getHeight() / 2);
		jframe.add(snakeGameBoard = new SnakeBoard());
		jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		jframe.addKeyListener(this);
		startGame();
	}
	//makes sure there is no tail at the cordinates 
	//used for making sure the food isn't placed on the snake
	public boolean noTailAt(int x, int y)
	{
		for (Point point : snakeBodyParts)
		{
			if (point.equals(new Point(x, y)))
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		snakeGameBoard.repaint();
		ticks++;

		if (ticks % 2 == 0 && snakeHead != null && !gameOver && !paused)
		{
			time++;

			snakeBodyParts.add(new Point(snakeHead.x, snakeHead.y));

			if (direction == UP)
			{
				if (snakeHead.y - 1 >= 0 && noTailAt(snakeHead.x, snakeHead.y - 1))
				{
					snakeHead = new Point(snakeHead.x, snakeHead.y - 1);
				}
				else
				{
					snakePlayer.addScoreToSnakeArray(score);
					
					snakePlayer.printSnake();
					String message = snakePlayer.printSnake();
					JOptionPane.showMessageDialog(null, message);
					gameOver = true;

				}
			}

			if (direction == DOWN)
			{
				if (snakeHead.y + 1 < 67 && noTailAt(snakeHead.x, snakeHead.y + 1))
				{
					snakeHead = new Point(snakeHead.x, snakeHead.y + 1);
				}
				else
				{
					snakePlayer.addScoreToSnakeArray(score);
					snakePlayer.printSnake();
					String message = snakePlayer.printSnake();
					JOptionPane.showMessageDialog(null, message);
					gameOver = true;
				}
			}

			if (direction == LEFT)
			{
				if (snakeHead.x - 1 >= 0 && noTailAt(snakeHead.x - 1, snakeHead.y))
				{
					snakeHead = new Point(snakeHead.x - 1, snakeHead.y);
				}
				else
				{
					snakePlayer.addScoreToSnakeArray(score);
					snakePlayer.printSnake();
					String message = snakePlayer.printSnake();
					JOptionPane.showMessageDialog(null, message);
					gameOver = true;
				}
			}

			if (direction == RIGHT)
			{
				if (snakeHead.x + 1 < 80 && noTailAt(snakeHead.x + 1, snakeHead.y))
				{
					snakeHead = new Point(snakeHead.x + 1, snakeHead.y);
				}
				else
				{
					snakePlayer.addScoreToSnakeArray(score);
					snakePlayer.printSnake();
					JOptionPane optionPane = new JOptionPane(snakePlayer.printSnake());
					optionPane.setVisible(true);
					gameOver = true;
					String message = snakePlayer.printSnake();
					JOptionPane.showMessageDialog(null, message);

				}
			}

			if (snakeBodyParts.size() > tailLength)
			{
				snakeBodyParts.remove(0);

			}

			if (snakeFood != null)
			{
				if (snakeHead.equals(snakeFood))
				{
					score += 10;
					tailLength++;
					snakeFood.setLocation(random.nextInt(79), random.nextInt(66));
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT)
		{
			direction = LEFT;
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT)
		{
			direction = RIGHT;
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN)
		{
			direction = UP;
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP)
		{
			direction = DOWN;
		}

		if (i == KeyEvent.VK_SPACE)
		{
			if (gameOver)
			{
				startGame();
			}
			else
			{
				paused = !paused;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}

} // end class
