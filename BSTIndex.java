package project5;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Represents a Binary Search Tree structure to store and manage Word objects.
 * The BSTIndex class implements the Index interface, allowing for ordered
 * storage and retrieval of words.
 * This class provides an iterator for traversing the tree in an ordered manner
 * and removing the specified Word object
 * 
 * @author Olivia Yu
 * @version December 4, 2023
 */
public class BSTIndex implements Index {
    private Node root = null;
    private int size = 0;

    /**
     * Provides an iterator for the BSTIndex.
     * The iterator traverses the tree in sorted order according to the natural
     * ordering of the Word objects and implement remove function.
     *
     * @return an Iterator for the BSTIndex.
     */
    @Override
    public Iterator<Word> iterator() {
        BSTIterator bsIterator = new BSTIterator(root);

        return bsIterator;
    }

    /**
     * Adds a word to the BSTIndex. If the word already exists, its count is
     * incremented.
     *
     * @param item the word to be added.
     * @throws IllegalArgumentException if the provided item is null.
     */
    @Override
    public void add(String item) throws IllegalArgumentException {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add null element");
        }

        if (root == null) {
            root = new Node(new Word(item));
            size++;
            return;
        }

        Node cur = root;
        Node tmp = null;

        while (cur != null) {
            int compare = item.compareTo(cur.word.getWord());

            if (compare > 0) {
                tmp = cur;
                cur = cur.right;
            } else if (compare < 0) {
                tmp = cur;
                cur = cur.left;
            } else {
                cur.word.incrementCount();

                return;
            }
        }

        Node newNode = new Node(new Word(item));
        if (item.compareTo(tmp.word.getWord()) > 0) {
            tmp.right = newNode;
        } else {
            tmp.left = newNode;
        }
        size++;
    }

    /**
     * Removes a word from the BSTIndex.
     *
     * @param item the word to be removed.
     * @throws IllegalArgumentException if the provided item is null.
     */
    @Override
    public void remove(String item) throws IllegalArgumentException {
        if (item == null)
            throw new IllegalArgumentException("cannot remove a node whose name is null");

        this.root = removeHelper(item, this.root);

    }

    /**
     * Recursively removes the specified item from the tree.
     *
     * @param item The word to be removed.
     * @param cur  The current node being inspected.
     * @return The updated node after removal.
     */
    private Node removeHelper(String item, Node cur) {

        if (cur == null) {
            return null;
        }

        int compare = cur.word.getWord().compareTo(item);

        if (compare > 0) {
            cur.left = removeHelper(item, cur.left);
        }
        else if (compare < 0) {
            cur.right = removeHelper(item, cur.right);
        }
        else {
            if (cur.left == null && cur.right == null) {
                size--;
                return null;
            }
            else if (cur.left == null) {
                size--;
                return cur.right;
            }
            else if (cur.right == null) {
                size--;
                return cur.left;
            }
            else {
                Node tmp = traceSuccessor(cur.right);
                cur.word = tmp.word;
                cur.right = removeHelper(tmp.word.getWord(), cur.right);
            }
        }
        return cur;
    }

    /**
     * Traces and returns the in-order successor of the given node.
     *
     * @param n The node whose successor is to be found.
     * @return The in-order successor of the given node.
     */
    private Node traceSuccessor(Node n) {
        while (n.left != null) {
            n = n.left;
        }

        return n;
    }

    /**
     * Retrieves the count of the specified word in the tree.
     *
     * @param item The word whose count is to be returned.
     * @throws NullPointerException if the provided String item is null.
     * @return The count of the word, or -1 if the word is not in the tree.
     */
    @Override
    public int get(String item) throws IllegalArgumentException {
        if (item == null)
            throw new IllegalArgumentException("can not get null element");

        if (this.size() == 0)
            return -1;

        return getHelper(item, this.root);
    }

    /**
     * Recursively retrieves the count of the specified word, starting from the
     * given node.
     *
     * @param item The word whose count is to be retrieved.
     * @param cur  The current node being inspected.
     * @return The count of the word, or -1 if not found.
     */
    private int getHelper(String item, Node cur) {
        if (cur == null)
            return -1;

        int compare = cur.word.getWord().compareTo(item);

        if (compare == 0) {
            return cur.word.getCount();

        } else if (compare > 0)
            return getHelper(item, cur.left);

        else {
            return getHelper(item, cur.right);
        }

    }

    /**
     * Returns the size of the BSTIndex.
     *
     * @return the number of unique words in the BSTIndex.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Compares the specified object with this BSTIndex for equality.
     * Two Index objects are considered equal if they contain the same number of
     * words,
     * and those words are pairwise equal.
     *
     * @param o the object to be compared for equality with this BSTIndex.
     * @return true if the specified object is equal to this BSTIndex.
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
        // every data value
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
     * Returns the string representation of this BSTIndex.
     * The string representation consists of a list of all Word objects in sorted
     * order,
     * enclosed in square brackets ("[]"). Adjacent elements are separated by ", "
     * (comma and space).
     *
     * @return the string representation of the BSTIndex.
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
     * Represents a node in the BSTIndex.
     * Each node stores a Word object and references to its left and right children.
     */
    private class Node implements Comparable<Node> {

        Word word;
        Node left;
        Node right;

        /**
         * Constructs a Node with the specified Word object.
         *
         * @param word The Word object to be stored in this node.
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
         * @param o The Node to be compared.
         * @return a negative integer, zero, or a positive integer as this node's Word
         *         is less than, equal to, or greater than the specified node's Word.
         */
        @Override
        public int compareTo(Node o) {
            return this.word.compareTo(o.word);
        }

    }

    /**
     * Implements the Iterator interface, providing a way to iterate through the
     * BSTIndex in sorted order and able to remove specified item.
     * This iterator traverses the tree using in-order traversal.
     * This iterator is implemented using a queue structure
     */
    private class BSTIterator implements Iterator<Word> {

        private Queue<Word> queue = null;
        private Word nextToReturn = null;

        /**
         * Constructs an iterator for the given BSTIndex.
         * traverse the tree in-order inside the constructor to store Word elements in
         * queue in order.
         *
         * @param root The root of the BSTIndex.
         */
        public BSTIterator(Node root) {
            this.queue = new LinkedList<>();
            inOrder(root);
        }

        /**
         * Performs an in-order traversal of the tree, starting from the given node,
         * and stores the Word objects in a queue.
         *
         * @param n The starting node for traversal.
         */
        private void inOrder(Node n) {
            if (n == null) {
                return;
            }

            inOrder(n.left);
            queue.offer((n.word));
            inOrder(n.right);
        }

        /**
         * Checks if there are more elements in the iteration.
         *
         * @return true if the iteration has more elements.
         */
        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return The next Word object in the iteration.
         * @throws NoSuchElementException if the iteration has no more elements.
         */
        @Override
        public Word next() throws NoSuchElementException {
            if (!hasNext()) {
                throw new NoSuchElementException("end of the tree!");
            }
            nextToReturn = queue.poll();
            return nextToReturn;
        }

        /**
         * Removes the last element returned by this iterator from the BSTIndex.
         * 
         * @throws IllegalStateException if the next method has not yet been called,
         *                               or the remove method has already been called
         *                               after the last call to next.
         */
        public void remove() throws IllegalStateException {
            if (nextToReturn == null) {
                throw new IllegalStateException("next() must be called before remove method");
            }

            BSTIndex.this.remove(nextToReturn.getWord());
            nextToReturn = null;
        }

    }
}