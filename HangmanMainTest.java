// Rewrote Main class to compare the outputs in output folder with the actual output

import java.util.*;
import java.io.*;

public class HangmanMainTest {
	public static final String DICTIONARY_FILE_1 = "dictionary.txt";
	public static final String DICTIONARY_FILE_2 = "dictionary2.txt";

    public static final String INPUT_FILE_PREFIX = "./inputs/input_"; // TODO: Seperate dir from prefix
    public static final String OUTPUT_FILE_PREFIX = "./outputs/expected_output_"; // TODO: Seperate dir from prefix
    public static final String TXTFILE_SUFFIX = ".txt";

	public static boolean DEBUG = true; // show words left

	public static void main(String[] args) throws FileNotFoundException {    
        String output;

        for (int i=1; i<=9; i++) {
            if (i <= 3) {
                DEBUG = true;
                List<String> dictionary1 = wordsFromFile(DICTIONARY_FILE_2);
                Scanner fileInput = new Scanner(new File(INPUT_FILE_PREFIX+i+TXTFILE_SUFFIX));
                output = mainCycle(dictionary1, fileInput);
            } else {
                DEBUG = false;
                List<String> dictionary2 = wordsFromFile(DICTIONARY_FILE_1);
                Scanner fileInput = new Scanner(new File(INPUT_FILE_PREFIX+i+TXTFILE_SUFFIX));
                output = mainCycle(dictionary2, fileInput);
            }
            if (output.equals(stringFromFile(OUTPUT_FILE_PREFIX+i+TXTFILE_SUFFIX))) {
                System.out.println("TestNo " + i + " Passed!\n");
            } else {
                System.out.println("TestNo " + i + " Failed!\n");
                System.out.println(output + "\n");
            } 
        }
	}

    public static String stringFromFile(String fileName) throws FileNotFoundException {
        String s = "";
        Scanner data = new Scanner(new File(fileName));
        while (data.hasNextLine()) {
            s += data.nextLine();
            s += "\n";
        }
        data.close();
        return s;
    }
    
    public static List<String> wordsFromFile(String fileName) throws FileNotFoundException {
        Scanner input = new Scanner(new File(fileName));
		List<String> dictionary = new ArrayList<String>();
		while (input.hasNext()) {
            dictionary.add(input.next().toLowerCase());
		}
        input.close();
        return dictionary;
    }

    public static String mainCycle(List<String> dictionary, Scanner console) {
        String output = "";
        output += "Welcome to the cs 145 hangman game.";
        output += "\n";
        output += "\n";
        
		// set basic parameters
		output += "What length word do you want to use? ";
		int length = console.nextInt();
        output += length + "\n"; // userInput
		output += "How many wrong answers allowed? ";
		int max = console.nextInt();
        output += max + "\n"; // userInput
		output += "\n";

		// set up the HangmanManager and start the game
		List<String> dictionary2 = Collections.unmodifiableList(dictionary);
		HangmanManager hangman = new HangmanManager(dictionary2, length, max);
		if (hangman.words().isEmpty()) {
			output += "No words of that length in the dictionary." + "\n";
		} else {
			output += playGame(console, hangman);
			output += showResults(hangman);
		}
        return output;
    }

	// Plays one game with the user
	public static String playGame(Scanner console, HangmanManager hangman) {
        String output = "";
		while (hangman.guessesLeft() > 0 && hangman.pattern().contains("-")) {
			output += "guesses : " + hangman.guessesLeft() + "\n";
			if (DEBUG) {
				output += hangman.words().size() + " words left: "
						+ hangman.words() + "\n";
			}
			output +="guessed : " + hangman.guesses() + "\n";
			output +="current : " + hangman.pattern() + "\n";
			output +="Your guess? ";
			char ch = console.next().toLowerCase().charAt(0);
            output += ch + "\n"; // userInput
			if (hangman.guesses().contains(ch)) {
				output +=("You already guessed that") + "\n";
			} else {
				int count = hangman.record(ch);
				if (count == 0) {
					output += "Sorry, there are no " + ch + "'s" + "\n";
				} else if (count == 1) {
					output += "Yes, there is one " + ch + "\n";
				} else {
					output += "Yes, there are " + count + " " + ch
							+ "'s" + "\n";
				}
			}
			output += "\n";
		}
        return output;
	}

	// reports the results of the game, including showing the answer
	public static String showResults(HangmanManager hangman) {
        String output = "";
		// if the game is over, the answer is the first word in the list
		// of words, so we use an iterator to get it
		String answer = hangman.words().iterator().next();
		output += "answer = " + answer + "\n";
		if (hangman.guessesLeft() > 0) {
			output += "You beat me" + "\n"; // No newline at end of file
		} else {
			output += "Sorry, you lose" + "\n"; // No newline at end of file
		}
        return output;
	}
}