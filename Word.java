package project5;

/**
 * Represents a word and its occurrence count.
 * This class provides functionality to track the occurrence counts of a word
 * and to compare words based on their natural order.
 * Implements the Comparable interface to allow for natural ordering of Word
 * objects.
 * Implements equal method to check whether two Word objects are equal based on
 * their count and words.
 * 
 * @author Olivia Yu
 * @version December 4, 2023
 */
public class Word implements Comparable<Word> {

    private String word;
    private int count;

    /**
     * Constructs a new Word object with the specified word.
     * The count is initialized to 1.
     *
     * @param word The word stored in this Word object.
     */
    public Word(String word) {
        this.word = word;
        this.count = 1;
    }

    /**
     * Increments the count associated with this Word object by 1.
     * 
     * @return The updated count value.
     */
    public int incrementCount() {
        count += 1;
        return count;
    }

    /**
     * Returns the word associated with this Word object.
     *
     * @return The word as a String.
     */
    public String getWord() {
        return word;
    }

    /**
     * Returns the count associated with this Word object.
     *
     * @return The count as an integer.
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns a string representation of this Word object.
     * The string consists of the count (right-aligned in a field of 5 characters),
     * followed by two spaces, followed by the word itself.
     *
     * @return The formated string representation of the Word object.
     */
    @Override
    public String toString() {
        return String.format("%5d  %s", getCount(), getWord());
    }

    /**
     * Compares this Word object with another Word object for order.
     * Returns a negative integer, zero, or a positive integer
     * is this object's word's natural order less than, equal to, or greater than
     * the specified object's word.
     *
     * @param o The Word object to be compared.
     * @return A negative integer, zero, or a positive integer as this word
     *         is lexicographically less than, equal to, or greater than the word of
     *         the specified object.
     */
    @Override
    public int compareTo(Word o) {
        return this.word.compareTo(o.word);
    }

    /**
     * Indicates whether some other object is "equal to" this Word object.
     * The result is true if and only if both the words and counts of the two Word
     * objects are equal.
     *
     * @param o The object to compare this Word object against.
     * @return true if the given object represents a Word equivalent to this object,
     *         false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (o == this) {
            return true;
        }

        if (!(o instanceof Word))
            return false;

        Word obj = (Word) o;

        return this.count == obj.count && this.word.equals(obj.word);
    }

}