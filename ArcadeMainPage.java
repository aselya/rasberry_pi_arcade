package arcade.cs622;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ArcadeMainPage extends JPanel {
	private JFrame frame;
	public PlayerProfileNode player = new PlayerProfileNode();
	
	

	/**
	 * Creates the main arcade page where games and high scores can be found.
	 */
	public ArcadeMainPage( String name, PlayerProfileNode newPlayer) {
		player = newPlayer;
		initialize(name, player);
	}

	/**
	 * Initialize the contents of the frame and player values.
	 */
	private void initialize(String name, PlayerProfileNode newPlayer) {
		player = new PlayerProfileNode();
		player = newPlayer;
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setVisible(true);
		frame.setTitle(name+"'s arcade!" );
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//starts snake on push
		JButton btnSnake = new JButton("Snake");
		btnSnake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SnakeMain.snake = new SnakeMain(player);	
			}
		});
		btnSnake.setBounds(78, 105, 117, 29);
		frame.getContentPane().add(btnSnake);
		
		//starts pong on push
		JButton btnPong = new JButton("Pong");
		btnPong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PongGameLogic.pong = new PongGameLogic(player);
			}
		});
		btnPong.setBounds(256, 105, 117, 29);
		frame.getContentPane().add(btnPong);
		
		//uses JOptionPane to display highscores
		JButton highScores = new JButton("High Scores");
		highScores.setBounds(174, 172, 117, 29);
		frame.getContentPane().add(highScores);
		
		highScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane.showMessageDialog(null, "player: " + player.getGamer()+ "\nPONG STATS\nPong Wins: "+ player.getPongWinsCount() +"\nPong Losses:"+ player.getPongLossesCount()+"\nLongest Rally: "+ player.getMaxRally()
				+"\n\nSNAKE\n"+ player.printSnake()
						);
			}
		});
		
	}

}//end class
