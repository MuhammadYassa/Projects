package project4;
import java.util.List;
import java.util.ArrayList;

public class BoggleCube {
	
	private char cube [];
	
	//creates a boggle cube with the given letters
	private BoggleCube (char[] letters) {
		cube = new char[6]; 
		for (int i = 0 ; i < 6; i++) {
			cube[i] = letters[i];
		}
	}
	
	//returns a random letter from the boggle cube
	public char getRandomLetter() {
		return cube [(int) (Math.random() * 6)];
	}
	
	//creates and returns a list of boggle cubes
	//each row in the parameter represents one cube
	//Precondition: the parameter has sixteen rows
	public static List<BoggleCube> makeCubes(char[][] allLetters){
		ArrayList<BoggleCube> listOfCubes = new ArrayList<BoggleCube>();
		for (int row = 0; row < allLetters.length; row++) {
			char [] tempLetters = new char [allLetters[0].length];
			for (int col = 0; col < allLetters[0].length; col++) {
				tempLetters [col] = allLetters [row][col];
			}
			BoggleCube tempCube = new BoggleCube(tempLetters);
			listOfCubes.add(tempCube);
		}
		return listOfCubes;
	}
}
