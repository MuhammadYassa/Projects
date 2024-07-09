package project4;
import java.util.List;
import java.util.ArrayList;

public class BogglePlayer {
	
	private String name;
	private int points = 0;
	private ArrayList<String> words = new ArrayList <String>();
	
	//creates a boggle player with a given name
	public BogglePlayer(String name) {
	this.name = name;
	}
	
	//returns the name of this boggle player
	public String getName() {
		return name;
	}
	
	//adds the given number of points to this player's score
	//player scores begin at zero
	public void addPoints(int points) {
		this.points += points;
	}
	
	//returns this player's score
	public int getScore() {
		return points;
	}
	
	//adds a word to this player's list of words
	public void addWord(String word) {
		words.add(word);
	}
	
	//returns this player's list of words
	public List<String> getWords(){
		return words;
	}
	
	//removes all words from this player's list of words
	public void clearWords() {
		words.clear();
	}

}
