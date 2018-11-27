package arcade.cs622;

import java.awt.Color;
import java.util.Random;
import java.awt.Graphics;


/**
 * This class contains the atributes for the ball class
 * Determines location of ball and it's momentum
 */
public class PongBall
{
	//ball property vars
	public int x, y, width = 20, height = 20; //sets size of the ball
	public int motionX;
	public int motionY;
	
	//player vars
	public int rallyLength;
	public PlayerProfileNode pongPlayer = new PlayerProfileNode();
	public Random random;

	private PongGameLogic pong;

	//constructor that starts the ball moving when called
	public PongBall(PongGameLogic pong, PlayerProfileNode pongPlayerProfile)
	{
		this.pong = pong;
		
		pongPlayer = pongPlayerProfile;

		this.random = new Random();

		startBall();
	}

	public void update(PongPaddle paddle1, PongPaddle paddle2)
	{
		int speed = 5; 
		//takes the location of the ball and updates it  
		//based on how fast the ball is travelling
		this.x += motionX * speed;
		this.y += motionY * speed;

		//checks to see if the location of the ball has reached top of the screen or bottom 
		if (this.y + height - motionY > pong.height || this.y + motionY < 0)
		{
			//corrects for a ball value below the screen and sets it to 0
			if (this.motionY < 0)
			{
				this.y = 0;
				this.motionY = random.nextInt(4);

				if (motionY == 0)
				{
					motionY = 1;
				}
			}
			else
			//corrects for a ball value above the screen 
			//sets it to the top of the screen
			{
				this.motionY = -random.nextInt(4);
				this.y = pong.height - height;

				if (motionY == 0)
				{
					motionY = -1;
				}
			}
		}
		//checks for collision if one occurs rally score is updated
		
		if (checkCollision(paddle1) == 1)
		{
			//increases speed for each rally
			this.motionX = 1+ rallyLength;
			
			//randomly determines if the ball will go up or down
			this.motionY = -2 + random.nextInt(4); 
			
			//makes sure ball does not in a strait line towards the other player
			if (motionY == 0)
			{
				motionY = 1;
			}

			rallyLength++;
		}
		else if (checkCollision(paddle2) == 1)
		{
			//increases speed for each rally
			this.motionX = -1 - rallyLength ;
			this.motionY = -2 + random.nextInt(4);

			//makes sure ball does not in a strait line towards the other player
			if (motionY == 0)
			{
				motionY = 1;
			}

			rallyLength++;
		}

		//if a ball reaches a side and no paddel blocks 
		//it update the score and start game again if not over
		if (checkCollision(paddle1) == 2)
		{
			
			System.out.println("player max value "+ pongPlayer.getMaxRally());
			if (rallyLength >= pongPlayer.getMaxRally()){
			pongPlayer.setMaxRally(rallyLength);
			}
			
			paddle2.score++;
			startBall();
		}
		else if (checkCollision(paddle2) == 2)
		{
			System.out.println("player max value "+ pongPlayer.getMaxRally());
				
			if (rallyLength >= pongPlayer.getMaxRally()){
				pongPlayer.setMaxRally(rallyLength);
				}
			
			paddle1.score++;
			startBall();
		}
	}

	public void startBall()
	{
		
		this.rallyLength = 0;//resets rally total
		
		//places the ball in middle of the screen
		this.x = pong.width / 2 - this.width / 2;
		this.y = pong.height / 2 - this.height / 2;

		this.motionY = -2 + random.nextInt(4);
		//makes sure vertical motion isn't 0
		if (motionY == 0)
		{
			motionY = 1;
		}

		//randomly determines horizontal direction it goes after start
		if (random.nextInt(2) == 0)
		{
			motionX = 1;
		}
		else
		{
			motionX = -1;
		}
	}

	//checks if a ball has hit a paddle
	
	public int checkCollision(PongPaddle paddle)
	{
		if (this.x < paddle.x + paddle.width && this.x + width > paddle.x && this.y < paddle.y + paddle.height && this.y + height > paddle.y)
		{
			return 1; //collision detected with paddle 
		}
		else if ((paddle.x > x && paddle.paddleNumber == 1) || (paddle.x < x - width && paddle.paddleNumber == 2))
		{
			return 2; //score, no collision detected but the ball is past the paddle
		}

		return 0; //nothing
	}

	//sets the color and shape of ball
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}

}
//end PongBall
