// JeongGyu Tak
// 02/12/2024
// CS145
// LAB#4
//
// This program manages the Hangman game logic
// It tracks player guesses, manages a list of potential words, and updates the game state.

// Initializes the game with a dictionary, word length, and maximum incorrect guesses.
public static void HangmanManager(List<String> dictionary, int length, int max)
// Initialize variables: attemptsLeft, words, guessesMade, and currentPattern.

// Returns the current set of words being considered.
public static void words()
// Return the set of possible words.

// Finds out how many guesses the player has left.
public static void guessesLeft()
// Return the number of remaining guesses.

// Finds out the current set of letters that have been guessed.
public static void guesses()
// Return the set of guessed letters.

// Returns the current pattern to be displayed for the hangman game.
public static void pattern()
// Return the current pattern with dashes for unguessed letters and spaces between them.

// Records the next guess made by the user and updates the game state.
public static void record(char guess)
// Validate the guess, update guessesMade, attemptsLeft, and currentPattern.
// Adjust the words set based on the guess.
// Return the number of occurrences of the guessed letter in the new pattern.

// Selects the best pattern for the current state.
private static void selectBestPattern(Map<String, Set<String>> patterns)
// Determine and return the most common pattern among the current set of words.

// Counts the occurrences of a guessed letter in the current pattern.
private static void numberOfCharInString(String s, char c)
// Count and return the number of times char 'c' appears in String 's'.

// Generates a pattern for a word based on the current guesses.
private static void generatePattern(String word, char guess)
// Generate and return a pattern for 'word' considering the guessed letter 'guess'.

