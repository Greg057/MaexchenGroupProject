import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Mäxchen implements ActionListener  {
	
	public static final List<Integer> listCombi = Arrays.asList(31, 32, 41, 42, 43, 51, 52, 53, 54, 61, 62, 63, 64, 65, 11, 22, 33, 44, 55, 66, 21);
	public static final List<Double> listProbaLying = Arrays.asList(0.00, 0.01, 0.02, 0.03, 0.04, 0.05, 0.08, 0.12, 0.15, 0.20, 0.25, 0.30, 0.35, 0.40, 0.50, 0.60, 0.70, 0.80, 0.90, 0.95, 0.99);
		
	// here we create our new frames
	JFrame frame = new JFrame();
	JFrame gameDone = new JFrame();
	
	//here we create our labels (texts or images)
	JLabel center = new JLabel();
	JLabel right = new JLabel();
	JLabel turnText = new JLabel(); 
	JLabel Label_gameDone = new JLabel(); 
	JLabel label_listCombinations = new JLabel(); 
	JLabel backgroundLabel = new JLabel(); 
	
	// create panels that will contain our labels 
	JPanel panel_center = new JPanel(); 
	JPanel panel_right = new JPanel();
	JPanel east = new JPanel();
	JPanel east2 = new JPanel();
	JPanel center2 = new JPanel();
	JPanel west2 = new JPanel(); 
	JPanel panel_rightButton = new JPanel();
	JPanel panel_leftButton = new JPanel(); 
	JPanel west = new JPanel(); 
	JPanel turn = new JPanel(); 
	JPanel listCombinations = new JPanel();
	
	// create buttons for the user to use 
	JButton roll_dices = new JButton();
	JButton lie = new JButton(); 
	JButton noLie = new JButton(); 
	JButton restart = new JButton();
	JButton exit = new JButton(); 
	
	// create ComboBox in order to choose the bluffed combination
	String[] combinations = {"31", "32", "41", "42", "43", "51", "52", "53", "54", "61", "62", "63", "64", "65", "11", "22", "33", "44", "55", "66", "21"}; 
	JComboBox comboBox = new JComboBox(combinations); 
	
	// import all images used 
	ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("logoMäxchen.png"));
	ImageIcon sadGhostie = new ImageIcon(getClass().getClassLoader().getResource("sadGhostie.png")); // https://www.flaticon.com/free-icon/ghost_1234845?related_id=1234845&origin=search
	ImageIcon happyGhostie = new ImageIcon(getClass().getClassLoader().getResource("477155.png")); //https://www.flaticon.com/free-icon/ghost_477155?related_id=477104&origin=search
	ImageIcon gameOverGhostie = new ImageIcon(getClass().getClassLoader().getResource("1234838.png")); //https://www.flaticon.com/free-icon/ghost_1234838?related_id=1234838#
	ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("background.png")); // https://itch.io/game-assets/tag-robots/tag-sprites 
	ImageIcon backgroundwithDice = new ImageIcon(getClass().getClassLoader().getResource("backgroundwithDice.png")); //// https://itch.io/game-assets/tag-robots/tag-sprites
	ImageIcon clapTrap = new ImageIcon(getClass().getClassLoader().getResource("clapTrap.png")); //https://www.resetera.com/threads/endearing-video-game-characters-that-you-cannot-stand.30477/
	
	// import sounds and music
	URL diceSound, bad, great, robotTalking, claptrapSound, oneUp;
	static URL music; 
	 
	// initiate 2 instances of the class player
	Player player = new Player(); 
	Player bot = new Player(); 
	 
	
	boolean from0 = true;
	boolean next = false;
	boolean callingLie = false; 
	boolean paidLife = false;  
	boolean paidLifeOptionUsed = false; 

	
	
	public static void main(String[] args) {
		
		new Mäxchen(); 
		Mäxchen.playMusic(music);
		
	}

	
	Mäxchen() {
		
		// set up sounds
		diceSound = getClass().getResource("DiceSound.wav");
		bad = getClass().getResource("bad.wav");
		great = getClass().getResource("great.wav");
		robotTalking = getClass().getResource("robotTalking.wav");
		oneUp = getClass().getResource("oneUp.wav");
		claptrapSound = getClass().getResource("claptrapSound.wav");
		music = getClass().getResource("music.wav"); 
		
		 
		
		// set up panels 
		panel_center.setBackground(new Color(247, 90, 255));
		panel_center.setLayout(new BorderLayout());
		panel_center.add(center);
		
		turn.setBackground(new Color(0, 0, 0));
		turn.setLayout(new BorderLayout());
		turn.setPreferredSize(new Dimension(200,50));
		turn.add(turnText);
		
		panel_right.setBackground(new Color(247, 90, 255));
		panel_right.setLayout(new BorderLayout());
		panel_right.setPreferredSize(new Dimension(200, 100));
		panel_right.add(right);
		panel_right.add(turn, BorderLayout.SOUTH);
		panel_right.add(listCombinations, BorderLayout.WEST);
		
		east.setBackground(new Color(247, 90, 255));
		east.setLayout(new BorderLayout());
		east.setPreferredSize(new Dimension(200, 100));
		east.add(panel_rightButton, BorderLayout.SOUTH);
		east.add(noLie);
		
		east2.setBackground(new Color(247, 90, 255));
		east2.setLayout(new BorderLayout());
		east2.setPreferredSize(new Dimension(200, 100));
		east2.add(restart);
		
		west2.setBackground(new Color(247, 90, 255));
		west2.setLayout(new BorderLayout());
		west2.setPreferredSize(new Dimension(200, 100));
		west2.add(exit);
		
		center2.setBackground(new Color(247, 90, 255));
		center2.setLayout(new BorderLayout());
		center2.add(Label_gameDone);
		
		
		west.setBackground(new Color(247, 90, 255));
		west.setLayout(new BorderLayout());
		west.setPreferredSize(new Dimension(200, 100));
		west.add(lie); 
		west.add(panel_leftButton, BorderLayout.SOUTH);
		
		
		panel_rightButton.setBackground(new Color(247, 90, 255));
		panel_rightButton.setLayout(new BorderLayout());
		panel_rightButton.setPreferredSize(new Dimension(200,100));
		panel_rightButton.add(roll_dices);
		
		panel_leftButton.setBackground(new Color(247, 90, 255));
		panel_leftButton.setLayout(new BorderLayout());
		panel_leftButton.setPreferredSize(new Dimension(200,100));
		panel_leftButton.add(comboBox);
		
		listCombinations.setBackground(new Color(247, 90, 255));
		listCombinations.setLayout(new BorderLayout());
		listCombinations.setPreferredSize(new Dimension(500,100));
		listCombinations.add(label_listCombinations); 

		
		// set up frames
		frame.setTitle("Mäxchen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout()); 
		frame.setVisible(true);
		frame.setIconImage(logo.getImage()); 
		frame.getContentPane().setBackground(new Color(247, 90, 255));
       	frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		frame.add(panel_center, BorderLayout.CENTER); 
		frame.add(panel_right, BorderLayout.NORTH);
		frame.add(east, BorderLayout.EAST);
		frame.add(west, BorderLayout.WEST); 
		
		gameDone.setTitle("Mäxchen");
		gameDone.setIconImage(logo.getImage());
		gameDone.getContentPane().setBackground(new Color(247, 90, 255));
		gameDone.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameDone.setResizable(false);
		gameDone.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		gameDone.setLayout(new BorderLayout());
		gameDone.add(east2, BorderLayout.EAST); 
		gameDone.add(west2, BorderLayout.WEST); 
		gameDone.add(center2, BorderLayout.CENTER); 
		
		
		
		// set up labels
		turnText.setText("Your Turn"); 
		turnText.setFont(new Font("Comic Sans MS", Font.BOLD, 35)); 
		turnText.setForeground(Color.WHITE); 
		turnText.setVerticalAlignment(JLabel.CENTER);
		turnText.setHorizontalAlignment(JLabel.CENTER);
		
		label_listCombinations.setText("<html>List possible combinations from weakest to strongest:<br>31>32>41>42>43>51>52>53>54>61>62>63>64>65<br>>11>22>33>44>55>66>21</html>");
		label_listCombinations.setFont(new Font("Comic Sans MS", Font.BOLD, 12)); 
		label_listCombinations.setVerticalAlignment(JLabel.TOP);
		label_listCombinations.setHorizontalAlignment(JLabel.LEFT);
		
		center.setFont(new Font("Comic Sans MS", Font.BOLD, 35)); 
		center.setIcon(background);
		center.setHorizontalTextPosition(SwingConstants.CENTER);
		center.setVerticalTextPosition(SwingConstants.TOP); 
		center.setIconTextGap(20); 
		center.setVerticalAlignment(JLabel.CENTER);
		center.setHorizontalAlignment(JLabel.CENTER);
		
		right.setText("Your life remaining: " + player.lifeRemaining + " | Life remaining Bot: " + bot.lifeRemaining);
		right.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		right.setVerticalAlignment(JLabel.CENTER);
		right.setHorizontalAlignment(JLabel.LEFT);
		
		Label_gameDone.setFont(new Font("Comic Sans MS", Font.BOLD, 50)); 
		Label_gameDone.setHorizontalTextPosition(SwingConstants.CENTER);
		Label_gameDone.setVerticalTextPosition(SwingConstants.TOP); 
		Label_gameDone.setIconTextGap(30); 
		Label_gameDone.setVerticalAlignment(JLabel.CENTER);
		Label_gameDone.setHorizontalAlignment(JLabel.CENTER);
	 
		
		//set up buttons
		roll_dices.setText("Roll dices");
		roll_dices.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		roll_dices.setFocusable(false);
		roll_dices.addActionListener(this); 
		
		lie.setVisible(false);
		lie.setFocusable(false);
		noLie.setFocusable(false);
		noLie.setVisible(false);
		lie.addActionListener(this);
		noLie.addActionListener(this);
		noLie.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		lie.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		
		comboBox.setVisible(false);	
		comboBox.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		comboBox.addActionListener(this); 
		
		restart.setVisible(true); 
		restart.setEnabled(true); 
		restart.setFocusable(false);
		restart.addActionListener(this);
		restart.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		restart.setText("Restart");
		
		exit.setEnabled(true); 
		exit.setText("Exit"); 
		exit.setFont(new Font("Comic Sans MS", Font.BOLD, 35));
		exit.setVisible(true); 
		exit.setFocusable(false);
		exit.addActionListener(this);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) { 
		if(e.getSource() == restart) { // here is what happens when user click the button "restart"
			new Mäxchen(); 
			gameDone.dispose(); 
		}
		
		if(e.getSource() == exit) { // here is what happens when user click the button "exit"
			System.exit(0);
		}
		
		if(e.getSource() == roll_dices) { // here is what happens when user click the button "roll_dices"
			if(callingLie == true) {
				callingLie = false; 
				checkWin();
				if(player.lifeRemaining == 1 && paidLifeOptionUsed == false) {
					payForLife();
					
				}
				roll_dices.setEnabled(true);
				roll_dices.setText("Roll dices");	
				from0 = true;
			}
			else if(next == true) {
				checkWin();
				
				roll_dices.setEnabled(false);
				center.setIcon(background);
				if(player.lifeRemaining == 1 && paidLifeOptionUsed == false) {
					payForLife();
					
				}
				else {
					botPlay();
				}
				 
			}
			else {
				playSound(diceSound);
				callingLie = false;
				turnText.setText("Your Turn");
				center.setIcon(backgroundwithDice);
				center.setText(null);
				roll_dices.setEnabled(false);
				rollDices(player); 
				roll_dices.setText("<html>You got: " + player.value + ".<br>Keep " + player.value + "?</html>");
				
				lie.setEnabled(true);
				noLie.setEnabled(true);
				lie.setVisible(true);
				noLie.setVisible(true);
				
				noLie.setText("Yes, keep " + player.value);
				lie.setText("<html>No, I want<br>to bluff</html>");
			}	
		}
		
		
		if(e.getSource() == lie) { // here is what happens when user click the button "lie"
			if(paidLife == true) {
				paidLife = false; 
				botPlay(); 
				
			}
			else if(callingLie == true) {
				lie.setVisible(false);
				noLie.setVisible(false);
				lie.setEnabled(false);
				noLie.setEnabled(false); 
				if(bot.isLying == true) {
					center.setText("Good job, you are right!");
					center.setIconTextGap(20);
					center.setIcon(happyGhostie);
					bot.lifeRemaining--;
					playSound(great);
				}
				else {
					center.setText("oh no, you are wrong!");
					center.setIconTextGap(20);
					center.setIcon(sadGhostie);
					player.lifeRemaining--;	
					playSound(bad);
					
					 
				}
				right.setText("Your life remaining: " + player.lifeRemaining + " | Life remaining Bot: " + bot.lifeRemaining);
				next = false; 
				roll_dices.setVisible(true);
				roll_dices.setEnabled(true); 
				roll_dices.setText("Next");
				from0 = true;
				 
			}
			else {
				lie.setEnabled(false);
				noLie.setEnabled(false);
				comboBox.setVisible(true);
				comboBox.setEnabled(true);
				noLie.setVisible(false);
				roll_dices.setText("Choosing...");
				lie.setText("<html>Choose your new<br>combination below:</html>");
				
			}
			
		}
		
		if(e.getSource() == comboBox) { // here is what happens when user click the button "noLie"
			comboBox.setEnabled(false);
			player.index = (int)comboBox.getSelectedIndex(); 
			player.value = listCombi.get(player.index);
			lie.setVisible(false);
			player.isLying = true;  
			checkIndex();
		}
		
		
		if(e.getSource() == noLie) {
			if(paidLife == true) {
				playSound(oneUp); 
				paidLife = false; 
				player.lifeRemaining++;
				right.setText("Your life remaining: " + player.lifeRemaining + " | Life remaining Bot: " + bot.lifeRemaining);
				botPlay();
			}
			else if(callingLie == true) {
				lie.setVisible(false);
				noLie.setVisible(false);
				lie.setEnabled(false);
				noLie.setEnabled(false);
				next = false; 
				roll_dices.setVisible(true);
				roll_dices.setEnabled(true); 
				callingLie = false; 
				roll_dices.setText("Roll dices");
				from0 = false; 
								
			}
			else {
				noLie.setVisible(false);
				lie.setVisible(false);
				noLie.setEnabled(false);
				lie.setEnabled(false); 
				player.index = listCombi.indexOf(player.value); 
				player.isLying = false;
				checkIndex();
		
				
			}			
		}
	}
	
	// here we check if the chosen value is not below the value of the bot, if it is the case, the user automatically loses one life
	public void checkIndex() {
		if(from0 == false && player.index <= bot.index) {
			center.setIconTextGap(20);
			center.setIcon(sadGhostie);
			center.setText("<html>Your combination is smaller than needed!<br>You lost a life! Be more Careful!</html>");
			player.lifeRemaining--;
			playSound(bad);
			right.setText("Your life remaining: " + player.lifeRemaining + " | Life remaining Bot: " + bot.lifeRemaining);
			roll_dices.setText("Next");
			roll_dices.setEnabled(true);
			next = true;
			comboBox.setVisible(false);
			lie.setVisible(false);
			noLie.setVisible(false);
			from0 = true; 
		}
		else {
			botCallOutLie();
		}
	}
	
	
	// method for the bot to choose between lying or not and if lying then choose what value he is gonna bluff
	public void botPlay() {
		playSound(robotTalking);
		rollDices(bot); 
		turnText.setText("Bot's Turn");
		center.setIconTextGap(-200);
		center.setIcon(background);
		center.setFont(new Font("Comic Sans MS", Font.BOLD, 25)); 
	
		if (from0 == true) {
			bot.index = listCombi.indexOf(bot.value);
			center.setText("Bot: \"I have a " + bot.value + "\".");
			bot.isLying = false;
			
		}
		else if (from0 == false) {
			if (listCombi.indexOf(bot.value) <= listCombi.indexOf(player.value)) {
				player.index = listCombi.indexOf(player.value);
				if (player.index == 20) {
					bot.index = (int)(Math.random()*(listCombi.size()-player.index-1) + player.index);
				}
				else {
					bot.index = (int)(Math.random()*(listCombi.size()-player.index-1) + player.index + 1);
				}
				bot.value = listCombi.get(bot.index);
				center.setText("Bot: \"I have a " + bot.value + "\".");
				bot.isLying = true; 
				
			}
			else {
				bot.index = listCombi.indexOf(bot.value);
				center.setText("Bot: \"I have a " + bot.value + "\".");
				bot.isLying = false;
			}
		}
		callOutLie(); 	
	}
	
	
	// method for the user to call out the bluff of the bot 
	public void callOutLie() {
		roll_dices.setVisible(false);
		callingLie = true; 
		lie.setVisible(true);
		noLie.setVisible(true);
		noLie.setEnabled(true); 
		lie.setEnabled(true);
		noLie.setText("<html>I think it<br>is true.</html>");
		lie.setText("<html>I think he<br>is bluffing!</html>");	
	}
	
	
	// method to generate 2 new dices and their respective values, as well as combine them to create a combination
	public void rollDices(Player name) {
		int a = (int)(Math.random()*6 + 1);
		int b = (int)(Math.random()*6 + 1);
		if (a >= b) {
			 name.value = a*10 + b;	
		}
		else {
			name.value = b*10 + a;
		}
	}
	
	// algorithm behind the choice of the bot calling out a bluff or not. 
	// As the given value by the user gets higher, the probability of the bot calling out a bluff increases --> see "listProbaLying"
	public void botCallOutLie() {
		double randomNum = Math.random(); 
		if (randomNum <= listProbaLying.get(player.index)) {
			if (player.isLying == true) {
				center.setText("Oh no! You got caught bluffing!");
				comboBox.setVisible(false);
				center.setIconTextGap(20);
				center.setIcon(sadGhostie);
				
				player.lifeRemaining--; 
				playSound(bad);
			}
			else {
				center.setText("<html>Great! Bot thought you were bluffing and<br>lost a life!</html>");
				center.setIconTextGap(20);
				center.setIcon(happyGhostie);
				bot.lifeRemaining--;
				playSound(great);
			}
			right.setText("Your life remaining: " + player.lifeRemaining + " | Life remaining Bot: " + bot.lifeRemaining);
			from0 = true; 
			
		}
		else {
			from0 = false;
			roll_dices.setText("Wait...");
		}
		roll_dices.setText("Next");
		roll_dices.setEnabled(true);
		next = true;
		comboBox.setVisible(false);
		lie.setVisible(false);
		noLie.setVisible(false);				
	}
	
	// method to give the user the possibility to gain an extra life when his life = 1
	public void payForLife() {
		if(paidLifeOptionUsed == false) {
			playSound(claptrapSound); 
			roll_dices.setEnabled(false);
			roll_dices.setVisible(false);
			lie.setEnabled(true);
			noLie.setEnabled(true);
			lie.setVisible(true);
			noLie.setVisible(true);
			lie.setText("<html>Decline<br>offer.</html>");
			noLie.setText("<html>Accept<br>offer.</html>");
			center.setIcon(clapTrap);
			center.setFont(new Font("Comic Sans MS", Font.BOLD, 20)); 
			center.setText("<html>ClapTrap has appeared!<br>He offers you an extra life if you consider adding points<br>to the final grade of this project!</html>");
			paidLife = true; 
			paidLifeOptionUsed = true; 
			
		}
				
	}
	
	
	// method to check if one of the player has won
	public void checkWin() {
		if (player.lifeRemaining == 0 || bot.lifeRemaining == 0) {
			frame.setVisible(false);
			frame.dispose(); 
			gameDone.setVisible(true);
			if (bot.lifeRemaining == 0) {
				Label_gameDone.setIcon(happyGhostie);
				Label_gameDone.setText("You Won!");	
			}
			else {
				Label_gameDone.setIcon(gameOverGhostie);
				Label_gameDone.setText("Game Over.");	
			}		
		
		}	
	}
	
	// method to play the given sounds
	// method mostly inspired from Stack overflow --> https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
	public void playSound(URL soundName) {
		   try 
		   {
			   AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundName);
			    Clip clip1 = AudioSystem.getClip();
			    clip1.open(audioInputStream);
			    clip1.start();
		   }
		   catch(Exception e)
		   {
			   System.out.println("Error with playing sound.");
			   e.printStackTrace(); 
		   }
		 }
	 
	
	// method to play music in a loop as long as the program is not exited
	// method mostly inspired from Stack overflow --> https://stackoverflow.com/questions/4875080/music-loop-in-java
	public static void playMusic(URL soundName) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundName);	    
		    AudioFormat audioFormat = audioInputStream.getFormat();
	        Clip clip2 = AudioSystem.getClip();
	        DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
	        Line line1 = AudioSystem.getLine(info);
	        if (!line1.isOpen()) {
	        	clip2.open(audioInputStream);
		        clip2.loop(Clip.LOOP_CONTINUOUSLY);
		        clip2.start(); 
	        }
	    } 
		catch(Exception e) {
		   System.out.println("Error with playing music.");
		   e.printStackTrace();
	     
	   }
	 }
	
	
}
