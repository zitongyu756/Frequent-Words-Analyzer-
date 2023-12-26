package project5;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Represents a sorted linked list that stores Word objects.
 * The list is sorted based on the natural ordering of the Word objects.
 * This class implements the Index interface and provides an iterator for
 * traversing the list and removing the specified Word object.
 * 
 * @author Olivia Yu
 * @version December 4, 2023
 */
public class SortedLinkedList implements Index {

    private Node head;
    private Node tail;
    private int size;

    /**
     * Constructs a new empty sorted linked list.
     */
    public SortedLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns an iterator over elements of type Word.
     * The elements are returned in their natural order.
     * 
     * @return an Iterator.
     */
    @Override
    public Iterator<Word> iterator() {
        return new ListIterator(this);
    }

    /**
     * Adds a new element to the sorted linked list.
     * If the element already exists, its count is incremented.
     *
     * @param item the word to be added.
     * @throws IllegalArgumentException if the item is null.
     */
    @Override
    public void add(String item) throws IllegalArgumentException {
        if (item == null)
            throw new IllegalArgumentException("can not add null element");

        Word word = new Word(item);
        Node n = new Node(word);
        if (this.size() == 0) {
            head = n;
            tail = n;
            size++;
            return;
        }
        Node cur = head;
        while (cur != null && cur.word.getWord().compareTo(item) < 0) {
            cur = cur.next;
        }
        // tail
        if (cur == null) {
            tail.next = n;
            n.prev = tail;
            tail = n;
            size++;
        }

        // when Word object already exist, count is increased
        else if (cur.word.getWord().equals(item)) {
            cur.word.incrementCount();
            return;
        }

        // head
        else if (cur.word.getWord().compareTo(item) > 0 && cur == head) {
            n.next = head;
            head.prev = n;
            head = n;
            size++;
        }

        else {
            n.prev = cur.prev;
            cur.prev.next = n;
            cur.prev = n;
            n.next = cur;
            size++;
        }
    }

    /**
     * Removes the specified element from the list.
     *
     * @param item the word to be removed.
     * @throws IllegalArgumentException if the item is null.
     */
    @Override
    public void remove(String item) throws IllegalArgumentException {
        if (item == null)
            throw new IllegalArgumentException("can not remove null element");

        if (size() == 0)
            return;

        Node cur = head;
        while (cur != null) {
            if (cur.word.getWord().equals(item)) {

                if (size() == 1) {
                    head = null;
                    tail = null;

                }

                else if (cur == head) {
                    head = cur.next;
                    head.prev = null;

                }

                else if (cur == tail) {
                    tail = cur.prev;
                    tail.next = null;

                }

                else {
                    cur.prev.next = cur.next;
                    cur.next.prev = cur.prev;

                }
                size--;
                break;
            }

            cur = cur.next;

        }
    }

    /**
     * Retrieves the count of the specified word in the list.
     *
     * @param item the word whose count is to be returned.
     * @return the count of the word, or -1 if the word is not in the list.
     * @throws IllegalArgumentException if the item is null.
     */
    @Override
    public int get(String item) throws IllegalArgumentException {
        if (item == null)
            throw new IllegalArgumentException("can not get null element");

        Node cur = head;
        while (cur != null) {

            if (cur.word.getWord().equals(item)) {
                return cur.word.getCount();
            }
            cur = cur.next;
        }
        return -1;
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the size of the list.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Compares the specified object with this list for equality.
     * Returns true if the given object is also an instance of Index, the two lists have the same
     * size,
     * and all corresponding pairs of elements are equal.
     *
     * @param o the object to be compared for equality with this list.
     * @return true if the specified object is equal to this list.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (o == this)
            return true;

        if (!(o instanceof Index))
            return false;

        Index obj = (Index) o;

        if (this.size() != obj.size())
            return false;

        // using two iterators to iterate through two lists and checks the equality for
        // each data value
        Iterator<Word> it1 = this.iterator();
        Iterator<Word> it2 = obj.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            Word data1 = it1.next();
            Word data2 = it2.next();

            if (!data1.equals(data2))
                return false;

        }

        return !it1.hasNext() && !it2.hasNext();

    }

    /**
     * Returns the string representation of this list.
     * The string representation consists of a list of the list's elements
     * enclosed in square brackets ("[]").
     * Adjacent elements are separated by the characters ", " (comma and space).
     *
     * @return the string representation of this list.
     */
    public String toString() {
        if (size() == 0) {
            return "[]";
        }

        String str = "[";
        Iterator<Word> it = iterator();

        if (it.hasNext()) {
            str += it.next();
        }

        while (it.hasNext()) {
            str += ", " + it.next();
        }

        return str + "]";
    }

    /**
     * Represents a node in the sorted linked list.
     * Each node stores a Word object and references to the next and previous nodes.
     */
    private class Node implements Comparable<Node> {

        Word word;
        Node next;
        Node prev;

        /**
         * Constructs a Node that stores the given Word object.
         *
         * @param word the Word object to be stored in this node.
         * @throws NullPointerException if the provided Word object is null.
         */
        Node(Word word) throws NullPointerException {
            if (word == null)
                throw new NullPointerException("does not allow null");
            this.word = word;
        }

        /**
         * Compares this Node with another Node based on the Word objects they store.
         *
         * @param o the Node to be compared.
         * @return a negative integer, zero, or a positive integer as this node's Word
         *         is less than, equal to, or greater than the specified node's Word.
         */
        @Override
        public int compareTo(Node o) {
            return this.word.getWord().compareTo(o.word.getWord());
        }

    }

    /**
     * implements the Iterator<Word> interface.
     * providing a way to iterate through the sorted linked list.
     */
    private class ListIterator implements Iterator<Word> {
        private Node nextToReturn;
        private Node lastReturned = null;

        /**
         * Constructs a ListIterator for the provided SortedLinkedList.
         *
         * @param sl the SortedLinkedList to create an iterator for.
         */
        public ListIterator(SortedLinkedList sl) {
            this.nextToReturn = sl.head;
        }

        /**
         * Checks if there are more elements in the iteration.
         *
         * @return true if the iteration has more elements.
         */
        @Override
        public boolean hasNext() {
            return nextToReturn != null;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next Word in the iteration.
         * @throws NoSuchElementException if the iteration has no more elements.
         */
        @Override
        public Word next() throws NoSuchElementException {
            if (nextToReturn == null)
                throw new NoSuchElementException("the end of the list reached");
            lastReturned = nextToReturn;
            nextToReturn = nextToReturn.next;
            return lastReturned.word;
        }

        /**
         * Removes from the list the last element returned by this iterator.
         *
         * @throws IllegalStateException if the next method has not yet been called,
         *                               or the remove method has already been called
         *                               after the last call to next.
         */
        @Override
        public void remove() throws IllegalStateException {
            if (lastReturned == null) {
                throw new IllegalStateException("next() must be called before remove method");
            }

            SortedLinkedList.this.remove(lastReturned.word.getWord());
            lastReturned = null;

        }

    }
}
