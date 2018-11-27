package arcade.cs622;

import java.awt.Graphics;
import java.awt.Color;


public class PongPaddle
{

	public int paddleNumber; //determines what player a paddle is assigned to
	public int x, y, width = 10, height = 175; //sets the length and width of paddle
	public int score; 

	//sets the paddle location based on if they are player one or two
	public PongPaddle(PongGameLogic pong, int paddleNumber)
	{
		this.paddleNumber = paddleNumber;
		if (paddleNumber == 1)
		{
			this.x = 0;
		}
		if (paddleNumber == 2)
		{
			this.x = pong.width - width;
		}
		this.y = pong.height / 2 - this.height / 2;
	}

	//paints the paddle
	public void render(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

	public void move(boolean up)
	{
		int speed = 10;

		if (up)
		{
			if (y - speed > 0)
			{
				y -= speed;
			}
			else
			{
				y = 0;
			}
		}
		else
		{
			if (y + height + speed < PongGameLogic.pong.height)
			{
				y += speed;
			}
			else
			{
				y = PongGameLogic.pong.height - height;
			}
		}
	}

}
// end PongPaddel
