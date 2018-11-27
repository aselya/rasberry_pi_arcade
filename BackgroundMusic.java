package arcade.cs622;

	import javax.sound.sampled.AudioInputStream;
  import javax.sound.sampled.AudioSystem;
  import javax.sound.sampled.Clip;
  import javax.swing.*;
	import sun.audio.*;
	import java.awt.event.*;
	import java.io.*;
	public class BackgroundMusic {

		
		
		/**
		 * This class is called by the thread on the ArcadeLogin page
		 * it opens up the background music file and plays it
		 */
		
		
		public BackgroundMusic(){
			
			soundPlay("../Arcade/src/arcade/cs622/marioGalaxySound.wav");
		}
		 
		public static void soundPlay(String soundName) { 
		    try {
		        AudioInputStream audioInputStream; 
		        audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		        Clip clip = AudioSystem.getClip();
		        clip.open(audioInputStream);
		        clip.start();
		        do {                
		            try {
		                Thread.sleep(50);
		            } catch (InterruptedException ie) { 
		                ie.printStackTrace();
		            }
		        } while (clip.isActive());
		    } catch(Exception error) {           
		        System.out.println("Error with playing sound."+error);
		    }
		}
		
		
	}

