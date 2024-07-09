package project2;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class PangramMaker {
	
	//fillWordsArray fills the words array that stores all words with the maximum number of distinct letters possible in a word out of the total number of letters missing from pangram.
	public static void fillWordsArray (ArrayList<String> words, ArrayList<String> dictionary, ArrayList<Character> letters, int max) {
		// for each loop to iterate through every word in the dictionary.
		for (String word : dictionary) {
			int count =0;
			// creates temporary array after every word to ensure every word is compared to all missing letters.
			ArrayList<Character> tempArray = new ArrayList<Character>(letters);
			// outer for loop to iterate over every character of the word.
			for (int i=0; i<word.length(); i++) {
				// inner for loop iterates over each letter in the missing letters array.
				for (int j=0; j<tempArray.size(); j++) {
					// if that character of that word is equal to the missing letter in our temp array.
					if (word.charAt(i)== tempArray.get(j)) {
						// increment count, then remove the missing letter from our temporary array to ensure a missing letters isnt counted twice.
						// because we only want the count of distinct letters missing from our pangram present in the word.
						count++;
						tempArray.remove(j);
						// breaks inner for loop and checks next character in the word.
						break;
					}
				}
			}
			// after iterating over the entire word, if the count == to the max number of distinct missing letters possible in a word, then add word to the words array. 
			if (count == max) {
				words.add(word);
			}
			// reset count to 0 for next word.
			count =0;
		}
	}
	
	//printWords method prints the words array randomly if more than 5 words. If not, prints random words from dictionary.
	public static void printWords (ArrayList<String> dictionary,ArrayList<String> words) {
		Random rand = new Random();
		//prints words array randomly if more than 5 words in array, removes them once printing to ensure no repetition of words.
		if (words.size() >= 5) {
			for (int i=0; i<5; i++) {
				int randIndex = rand.nextInt(words.size());
				System.out.println(words.remove(randIndex));
			}
			// returns and ends method once printing all 5.
			return;
		} 
		
		int numOfLoops = words.size();
		int remainingWords = 5 - words.size();
		
		// if words array contains less than 5 words, prints all of them in a random order.
		for (int i=0; i<numOfLoops; i++) {
			int randIndex = rand.nextInt(words.size());
			System.out.println(words.remove(randIndex));
		}
		// then prints the remaining number of words randomly from the dictionary.
		for (int i=0; i<remainingWords; i++) {
			int randIndex = rand.nextInt(dictionary.size());
			System.out.println(dictionary.get(randIndex));
		}
	}
	
	//maxDistinctLetters counts the max number of distinct missing letters possible in a word from the dictionary.
	public static int maxDistinctLetters (ArrayList<Character> letters, ArrayList<String> dictionary) {
		int count =0;
		//iterates over each word in the dictionary
		for (String word : dictionary) {
			//reset temp to 0 for each word.
			int temp = 0;
			// creates temp array for each word to compare to the missing letters from our pangram.
			ArrayList<Character> tempArray = new ArrayList<Character>(letters);
			// iterates over each character of the word.
			for (int i=0; i<word.length(); i++) {
				// if the temp array contains the character, then remove the letter from the temp array and increment value of temp variable by one.
				if (tempArray.contains(word.charAt(i))){
					temp++;
					tempArray.remove(Character.valueOf(word.charAt(i)));
				}
			}
			// temp variable keeps count of number of distinct missing letters in the current word.
			// if temp count is greater than count, than count is now equal to temp.
			if (temp > count) {
				count = temp;
			}
		}
		// once we've gone through each word in the dictionary, the count variable should store the maximum number of missing letters that could be present in a possible word in the dictionary.
		return count;
	}
	
	//help method uses all other methods and finds all the missing letters left for the pangram to be complete.
	//then using other methods, prints the help words.
	public static void help (ArrayList<String> dictionary, String sentence) {
		ArrayList <Character> letters = new ArrayList<Character>();
		ArrayList <String> words = new ArrayList<String>();
		
		// for loop fills letter array list with all letters from alphabet.
		for (int i=0; i<26; i++) {
			letters.add ((char)(i + 65));
		}
		
		// outer for loop iterates 26 times, but decrements to avoid out of bound exceptions.
		for (int i=25; i>=0; i--) {
			// inner for loop iterates over each character in our pangram.
			for (int j=0; j<sentence.length(); j++) {
				// if the current character == to the current letter. 
				if (sentence.charAt(j) == letters.get(i)) {
					// remove letter from letters array list and break inner loop, move on to next letter in letters array list.
					letters.remove(i);
					break;
				}
			}
			// this way at the end of the 25 loops, the letters array will only hold the missing letters for our pangram needed to be complete.
		}
		
		// call maxDistinctLetters method to get maximum number of missing letters that can be present in a word.
		int max = maxDistinctLetters (letters, dictionary);
		// call fillWordsArray method to fill words array with every word that contains the maximum number of missing letters that can be present in a word.
		fillWordsArray (words, dictionary, letters, max);
		// call printWords method to finally print all the words and any remaining words.
		printWords(dictionary, words);
	}
	
	//countWords method counts number of words and returns int value.
	public static int countWords (String sentence) {
		// if empty or null return 0.
		if (sentence == null || sentence.isEmpty()) {
			return 0;
		}
		// trim pangram for any unwanted whitespace.
		sentence = sentence.trim();
		// split pangram between every space character, and store in words array.
		String[] words = sentence.split(" ");
		// returns the length of words array, which should equal to number of words in pangram.
		return words.length;
	}
	
	//countLetters method counts the number of letters and returns int value.
	public static int countLetters (String sentence) {
		// if our pangram is null or empty, which it shouldn't be, but check just incase, returns 0.
		if (sentence == null || sentence.isEmpty()) {
			return 0;
		}
		int count = 0;
		//for loop iterates through every character in our pangram and checks if its between ASCII Values 65 and 90, if yes then its a letter, increments count by one.
		for (int i = 0; i < sentence.length(); i++) {
			char c = sentence.charAt(i);
			if (c >= 'A' && c <= 'Z') {
				count++;
			}
		}
		// returns count.
		return count;
	}
	
	//testPangramComplete method checks if our pangram has every letter in the alphabet atleast once and returns true if it does.
	public static boolean testPangramComplete (String sentence) {
		char letter = 'A';
		// uses nested for loop to iterate through our pangram 26 times.
		// comparing each character to our 'letter' variable and if == then incrementing letter variable by 1 ASCII value.
		for (int j=0; j<26; j++) {
			for (int i=0; i<sentence.length(); i++) {
				if (sentence.charAt(i) == letter) {
					letter++;
				}
			}
		}
		// once iterated through our pangram 26 times, assuming the pangram had every letter in the alphabet,
		// our 'letter' variable should hold the value 'Z' + 1, if true then the pangram is complete. If not, return false.
		if (letter == 'Z' + 1) {
			return true;
		} else {
			return false;
		}
	}
	
	//isWordInDictionary method checks if the entered word is present in the dictionary.
	public static boolean isWordInDictionary (ArrayList<String> dictionary, String s) {
		// uses a for each loop to go through each word in the dictionary array list and compares to entered word.
		//returns true if they're equal, else returns false.
		for (String word : dictionary) {
			if (word.equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	//printPrompt method used to print the prompt and the current pangram we have till now.
	public static void printPrompt (String sentence) {
		System.out.println();
		System.out.println("Your pangram so far is: " + sentence);
		System.out.print("Enter the next word (in all uppercase) or enter help for suggestions: ");
	}
	
	//Main method.
	public static void main (String [] args) {
		//read the words.txt file and store in Array list called dictionary.
		//use try-catch block to check if code runs into FileNotFoundException Error.
		ArrayList<String> dictionary = new ArrayList<String>(); 
		try {
			Scanner fileInput = new Scanner(new File("words.txt"));
			while (fileInput.hasNext()) {
				dictionary.add(fileInput.next());
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found!");
		}
		dictionary.add("A");
		dictionary.add("I");
		
		//initialize our pangram String.
		String pangram = "";
		Scanner input = new Scanner (System.in);
		
		System.out.println("Welcome to Pangram Maker!");
		
		//Outer while loop keeps running until the pangram is complete and contains every letter of the alphabet.
		while(!testPangramComplete(pangram)){
			printPrompt(pangram);
			String nextWord = input.next();
			// Inner while loop keeps running when the entered word is either "help" or not in the dictionary.
			while (nextWord.equals("help") || !isWordInDictionary(dictionary, nextWord)) {
				// If entered word is "help", call help method.
				if(nextWord.equals("help")) {
					help(dictionary, pangram);
					printPrompt(pangram);
					nextWord = input.next();
				} 
				// else prints not a valid word and prompts the user to enter another word.
				else {
					System.out.println("That's not a valid word!");
					printPrompt(pangram);
					nextWord = input.next();
				}
			}
			//add entered word after all checks to our pangram.
			pangram += nextWord + " ";
			
		}
		// Once while loop exits, pangram has been completed and we print out the pangram and the number of words and letters.
		System.out.println("Your pangram is complete!");
		System.out.println(pangram);
		System.out.println("Total Words: " + countWords(pangram) );
		System.out.println("Total Letters: " + countLetters(pangram));	
		input.close();
	}
}
