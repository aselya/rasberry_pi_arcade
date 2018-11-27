package arcade.cs622;

import java.awt.EventQueue;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ArcadeLogin implements Runnable{

	private JFrame frame;
	private JTextField textField;
	private playerLL players = new playerLL();
	private JTextField textField_1;


	/**
	 * Launches the main arcade login page.
	 * Uses threading to launch background music that plays during the program
	 */
	//opens page
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ArcadeLogin window = new ArcadeLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ArcadeLogin() {
		initialize(players);
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(playerLL players) {

		frame = new JFrame();
		//this thread runs in the background allowing the music to run at the same time as the game
		new Thread() {
			  @Override
			  public void run() {
			    BackgroundMusic music = new BackgroundMusic();
			    }
			}.start();
		
//buttons and text fields on screens initated
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Welcome to the Arcade!");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(89, 6, 217, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Username:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setBounds(81, 73, 85, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(178, 73, 96, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		
		
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			@Override
			//upon pressing the start button a login is attempted
		
			public void actionPerformed(ActionEvent e) {
				
					try {
						if (players.addToTail(textField.getText(), textField_1.getText()) != null){
						System.out.println("\nhead is:"+players.getHead().getGamer()+"\npassword is:"+players.getHead().getPassword());
						players.toString();
						String passwordField = textField_1.getText();
						textField_1.setText(null);
						ArcadeMainPage frame2 = new ArcadeMainPage(textField.getText(), players.findPlayer(textField.getText(), passwordField));
					}else{
						textField_1.setText(null);
					}} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnStart.setBounds(157, 164, 117, 29);
		frame.getContentPane().add(btnStart);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(89, 122, 75, 16);
		frame.getContentPane().add(lblPassword);
		
		textField_1 = new JTextField();
		textField_1.setBounds(176, 117, 130, 26);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
	

}// end class
