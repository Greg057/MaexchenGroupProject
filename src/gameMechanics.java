import java.lang.Math;
import java.util.Arrays;
import java.util.List;   


public class gameMechanics {
	public static final List<Integer> listCombi = Arrays.asList(31, 32, 41, 42, 43, 51, 52, 53, 54, 61, 62, 63, 64, 65, 11, 22, 33, 44, 55, 66, 21);
	public static final List<Double> listProbaLying = Arrays.asList(0.00, 0.01, 0.02, 0.03, 0.04, 0.05, 0.08, 0.12, 0.15, 0.20, 0.25, 0.30, 0.35, 0.40, 0.50, 0.60, 0.70, 0.80, 0.90, 0.95, 0.99);
	String name; 
	int storedValue;
	int lifeRemaining = 6;
	boolean isLying;
	static boolean from0 = true;
	int index;
	static int gameTurn = 0;
	

	public static void main(String[] args) {
		gameMechanics player1 = new gameMechanics();
		player1.name = "Player 1";
		gameMechanics player2 = new gameMechanics();
		player2.name = "Player 2";

		while (true) {
			generateAndStoreValue(player1);
			playTurn(player1, player2);
			callOutLie(player2, player1);
			
			generateAndStoreValue(player2);
			playTurn(player2, player1);
			callOutLie(player1, player2);	
		}
	}
	
	
	public static void playTurn(gameMechanics playerY, gameMechanics playerX) {
		if (from0 == true) {
			playerY.index = listCombi.indexOf(playerY.storedValue);
			System.out.println(playerY.name + ": \"I have a " + playerY.storedValue + "\".");
			playerY.isLying = false;
		}
		else if (from0 == false) {
			if (listCombi.indexOf(playerY.storedValue) <= listCombi.indexOf(playerX.storedValue)) {
				playerX.index = listCombi.indexOf(playerX.storedValue);
				if (playerX.index == 20) {
					playerY.index = (int)(Math.random()*(listCombi.size()-playerX.index-1) + playerX.index);
				}
				else {
					playerY.index = (int)(Math.random()*(listCombi.size()-playerX.index-1) + playerX.index + 1);
				}
				playerY.storedValue = listCombi.get(playerY.index);
				System.out.println(playerY.name + ": \"I have a " + playerY.storedValue + "\".");
				playerY.isLying = true; 
				 
			}
			else {
				playerY.index = listCombi.indexOf(playerY.storedValue);
				System.out.println(playerY.name + ": \"I have a " + playerY.storedValue + "\".");
				playerY.isLying = false;
				
			}
		}
		gameTurn++; 
		
	}

	public static void callOutLie(gameMechanics playerX, gameMechanics playerY) {
		if (gameTurn != 1) {
			double randomNum = Math.random(); 
			if (randomNum <= listProbaLying.get(playerY.index)) {
				System.out.println(playerX.name + ": \"I think you are lying!\"");
				if (playerY.isLying == true) {
					System.out.println(playerY.name + ": \"You caught me :(\"");
					playerY.lifeRemaining--; 
					
				}
				else {
					System.out.println(playerY.name + ": \"You're wrong!\"");
					playerX.lifeRemaining--;
				
				}
				from0 = true;
			}
			else {
				from0 = false;
			}
			if (playerX.lifeRemaining == 0) {
				System.out.println(playerY.name + " won. The game is over.");
				System.exit(0);
			}
			if (playerY.lifeRemaining == 0) {
				System.out.println(playerX.name + " won. The game is over.");
				System.exit(0);
			}
						
		}
		else {
			from0 = false;
		}
	}
	
	public static void generateAndStoreValue(gameMechanics playerX) {
		int a = (int)(Math.random()*6 + 1);
		int b = (int)(Math.random()*6 + 1);
		System.out.println(playerX.name + ": \n\tDice 1: " + a + ", \n\tDice 2: " + b);
		if (a >= b) {
			playerX.storedValue = a*10 + b;	
		}
		else {
			playerX.storedValue = b*10 + a;
		}
		System.out.println("\t" + playerX.storedValue);	
	}
		

	
	
}
