package project4;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BoggleGame {

	private Scanner input = new Scanner (System.in);
	private ArrayList <BoggleCube> cubes;
	private ArrayList <BogglePlayer> players;
	private ArrayList <String> dictionary;
	
	// Constructor initializes the game by setting up dictionary, cubes, and players, then starts the game loop.
	public BoggleGame () {
		fillDictionary();
		fillCubes();
		fillPlayers();
		// Main game loop until a player reaches 100 points
		while (!testForWinner()) {
			displayBoard();
			acceptWords();
			computeAndDisplay();
		}
		System.out.println("Game Over, A player has reached 100 points!");
	}
	
	// Loads dictionary words from a file into the dictionary ArrayList
	public void fillDictionary() {
		dictionary = new ArrayList<String>(); 
		try {
			Scanner fileInput = new Scanner(new File("words.txt"));
			while (fileInput.hasNext()) {
				dictionary.add(fileInput.next());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
		}
	}
	
	// Initializes the players by prompting the user for the number of players and their names
	public void fillPlayers () {
		System.out.print("How many players? ");
		int numOfPlayers = input.nextInt();
		players = new ArrayList <BogglePlayer>();
		for (int i=0 ; i < numOfPlayers ; i++) {
			System.out.print("Enter player " + (i+1) + "'s name (no spaces):");
			BogglePlayer player = new BogglePlayer(input.next());
			players.add(player);
		}
	}
	
	// Initializes the cubes by prompting the user to enter the letters for each cube
	public void fillCubes () {
		System.out.println("Enter the cube faces as sixteen lines below:");
		char [][] letters = new char [16][6];
		// Reads six letters for each of the sixteen cubes
		for (int i = 0; i < 16 ; i++) {
			String tempWord = input.next();
			for (int j=0 ; j < tempWord.length(); j++) {
				letters [i][j] = tempWord.charAt(j);
			}
		}
		 // Creates BoggleCube objects from the input letters
		cubes = new ArrayList<BoggleCube>(BoggleCube.makeCubes(letters));
	}
	
	// Checks if any player has reached 100 points
	public boolean testForWinner () {
		// Iterates through the list of players to check their scores
		for (int i = 0; i < players.size(); i++) {
			if ((players.get(i)).getScore() >= 100) {
				return true;
			}
		}
		return false;
	}
	
	// Displays the current board with random letters from random cubes each time.
	public void displayBoard () {
		ArrayList <Character> tempLetters = new ArrayList<Character>();
		// Collects a random letter from each cube
		for (int i=0; i< 16 ; i++) {
			tempLetters.add((cubes.get(i)).getRandomLetter());
		}
		 // Prints the letters in a random order in a 4x4 grid format
		for (int i = 0; i< 4 ; i++) {
			for (int j=0; j<4 ; j++) {
				int tempIndex = (int)(Math.random()*tempLetters.size());
				System.out.print(tempLetters.get(tempIndex) + " ");
				tempLetters.remove(tempIndex);
			}
			System.out.println();
		}
	}
	
	//isInDictionary method checks if the entered word is present in the dictionary.
	public boolean isInDictionary (String s) {
		// uses a for each loop to go through each word in the dictionary array list and compares to entered word.
		//returns true if they're equal, else returns false.
		for (String word : dictionary) {
			if (word.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	// Checks if a word is new for the player in the current round
	public boolean newWordForPlayer(String s, int index) {
		ArrayList <String> tempArray = (players.get(index).getWords() == null) ? new ArrayList<String>() : new ArrayList<String>(players.get(index).getWords());
		for (int i = 0; i < tempArray.size() ; i++) {
			if (s.equals(tempArray.get(i))){
				return false;
			}
		}
		return true;
	}
	
	// Accepts words from each player
	public void acceptWords() {
		// Iterates through each player to accept their words
		for (int i = 0; i < players.size(); i++) {
			System.out.println ("Enter " + (players.get(i)).getName() + "'s words, then enter done.");
			String tempWord = input.next();
			// Loops until the player types 'done'
			while ((!tempWord.equals("done"))) {
				// Validates the entered word
				if (tempWord.length() < 3) {
					System.out.println("Words must contain at least three letters!");
				} else if (!isInDictionary(tempWord)) {
					System.out.println("Not a word in the dictionary!");
				} else if (!newWordForPlayer(tempWord, i)) {
					System.out.println("You already used that word this round!");
				} else {
					// Adds the word to the player's list if valid
					players.get(i).addWord(tempWord);
				}
				tempWord = input.next();
			} 
		}
	}
	
	// Checks if a word is new for all players in the current round
	public boolean newWordForAll(String S, int index) {
		for (int i = 0; i < players.size() ; i++) {
			if (players.get(i).getWords().contains(S) && i != index) {
				return false;
			}
		}
		return true;
	}
	
	// Computes Fibonacci sequence for scoring
	public int fibonacci (int n) {
		if (n <= 1) {
	        return 1;
	    }
	    int a = 1, b = 1;
	    for (int i = 2; i <= n; i++) {
	        int temp = a + b;
	        a = b;
	        b = temp;
	    }
	    return b;
	}
	
	// Computes and displays the scores
	public void computeAndDisplay() {
		// Iterates through each player to compute their scores
		for (int i = 0; i < players.size(); i++) {
			for (int j = 0 ; j < ((players.get(i)).getWords()).size() ; j++) {
				// Adds points if the word is new for all players
				if (newWordForAll(((players.get(i)).getWords()).get(j), i)) {
					players.get(i).addPoints(fibonacci((((players.get(i)).getWords()).get(j)).length() - 3));
				}
			}
		}
		
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getName() + ": " + players.get(i).getScore());
			// Clear words for the next round
			players.get(i).clearWords();
		}
	}
	
	// Main method to start the game
	public static void main (String [] args) {
		new BoggleGame ();
	}
}
