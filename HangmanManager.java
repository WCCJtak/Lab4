import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
/** 
 * HanganManager.class
 * @Author: JeongGyu Tak, Same Clarke, Nick Ivancovich
 * @Date: 240212
 * @Class: CS&145
 * @Assignment: LAB#4
 * @Purpose: Using sets and maps.
 */
public class HangmanManager {

    private int attemptsLeft;
    private Set<String> words;
    private Set<Character> guessesMade;
    private String currentPattern;

    private static final char EMPTY_CHAR = '-';

    /**
     * Initializes a new Hangman game manager.
     *
     * @param dictionary The list of words to use as the dictionary.
     * @param length The target length of the word to find.
     * @param max The maximum number of incorrect guesses allowed.
     * @throws IllegalArgumentException If the length is less than 1 or max is negative.
     */
    public HangmanManager(List<String> dictionary, int length, int max) {
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException("Length should be longer than 1 "
                                            + "and max must be bigger than 0.");
        }
        this.attemptsLeft = max;
        this.words = new TreeSet<>();
        this.guessesMade = new TreeSet<>();
        currentPattern = ""; // init
        for (String word : dictionary) {
            if (word.length() == length) {
                words.add(word);
            }
        }
        for (int i=0; i<length; i++) {
            this.currentPattern += EMPTY_CHAR;
        }
    }

    /**
     * Returns an unmodifiable set of words currently considered possible matches.
     *
     * @return A set of words.
     */
    public Set<String> words() {
        return Collections.unmodifiableSet(words);
    }

    /**
     * Returns the number of guesses left before game over.
     *
     * @return The number of guesses left.
     */
    public int guessesLeft() {
        return attemptsLeft;
    }

    /**
     * Returns an unmodifiable set of characters that have been guessed so far.
     *
     * @return A set of guessed characters.
     */
    public Set<Character> guesses() {
        return Collections.unmodifiableSet(guessesMade);
    }

    /**
     * Returns the current pattern of the word being guessed, with guessed letters revealed 
     * and unguessed letters represented by dashes. Letters and dashes are separated by spaces.
     *
     * @return The current word pattern.
     * @throws IllegalStateException If the set of words is empty.
     */
    public String pattern() {
        if (words.isEmpty()) {
            throw new IllegalStateException("The set of words is empty.");
        }
        return String.join(" ", currentPattern.split(""));
    }

    /**
     * Records a guess made by the player and updates the game state accordingly.
     *
     * @param guess The character guessed by the player.
     * @return The number of occurrences of the guessed letter in the new pattern.
     * @throws IllegalStateException If no guesses are left or the set of words is empty.
     * @throws IllegalArgumentException If the character has already been guessed.
     */
    public int record(char guess) {
        if (words.isEmpty() || attemptsLeft < 1) {
            throw new IllegalStateException("Either no guesses left or the set of words is empty.");
        }
        if (guessesMade.contains(guess)) {
            throw new IllegalArgumentException("The character has already been guessed.");
        }
    
        guessesMade.add(guess);
        
        // Get possible patterns - Patterns  = { pattern : [words] }
        Map<String, Set<String>> patterns = new TreeMap<String, Set<String>>();
        for (String word : words) {
            String pattern = generatePattern(word);
            if (!patterns.containsKey(pattern)) {
                patterns.put(pattern, new TreeSet<>());
            }
            patterns.get(pattern).add(word);
        }

        // Update pattern and words. If they failed to guess, attempt--.
        String bestPattern = selectBestPattern(patterns);
        words = patterns.get(bestPattern);
        if (!currentPattern.equals(bestPattern)) {
            currentPattern = bestPattern; // Update current pattern to the best pattern
        } else {
            attemptsLeft--;
        }
        return numberOfCharInString(currentPattern, guess);
    }

    /**
     * Returns the most common pattern from the given map of patterns to words.
     *
     * @param patterns A map from word patterns to sets of words matching those patterns.
     * @return The pattern corresponding to the largest set of words.
     */
    private String selectBestPattern(Map<String, Set<String>> patterns) {
        int maxPatternsCount = 0;
        String bestPattern = currentPattern;
        for (String pattern : patterns.keySet()) {
            Set<String> wordSet = patterns.get(pattern);
            if (wordSet.size() > maxPatternsCount) {
                maxPatternsCount = wordSet.size();
                bestPattern = pattern;
            }
        }
        return bestPattern;
    }
    
    /**
     * Counts the number of occurrences of a specific character in a string.
     *
     * @param s The string to search.
     * @param c The character to count.
     * @return The number of occurrences of the character in the string.
     */
    private int numberOfCharInString (String s, char c) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == c) {
                count++;
            }
        }
        return count;
    }

    /**
     * Generates a pattern for a word based on the current guesses.
     *
     * @param word The word to generate a pattern for.
     * @return A pattern representing the word with guessed letters revealed and others hidden.
     */
    private String generatePattern(String word) {
        String pattern = "";
        for (int i = 0; i < word.length(); i++) {
            char letter = word.charAt(i);
            if (guessesMade.contains(letter)) {
                pattern += letter;
            } else if (currentPattern.charAt(i) != EMPTY_CHAR) {
                pattern += currentPattern.charAt(i);
            } else {
                pattern += EMPTY_CHAR;
            }
        }
        return pattern;
    }
}
