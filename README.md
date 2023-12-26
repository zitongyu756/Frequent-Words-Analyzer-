# Frequent Words Analyzer 

The Frequent Words Analyzer is a Java program designed to analyze the frequency of words in a text file. It leverages two data structures - a sorted linked list and a binary search tree (BST) - to compute and compare the performance of these structures in managing word frequencies.

## Objectives
The main objective is to evaluate and compare the performance of sorted linked lists and binary search trees in the context of word frequency analysis.

## Components
### `FrequentWords` Class

* Entry point for the program.
* Parses command-line arguments to specify input file, cutoff value, and output file.
* Orchestrates the word counting process using different data structures.

### `FileParser` Class
* Responsible for reading and parsing words from the input file.
* Strips non-alphabetic characters and standardizes words to lower case.

### `BSTIndex` Class
* Implements the Index interface using a binary search tree.
* Manages `Word` objects, allowing for adding, removing, and retrieving word counts.
* Provides an iterator for ordered traversal and removal of words.

### `SortedLinkedList` Class
* Implements the `Index` interface using a sorted linked list.
* Manages `Word` objects with functionalities similar to `BSTIndex`.

### `Word` Class
* Represents words and their occurrence counts.
* Supports incrementing count, comparison, and equality checks based on word content.

### `Index` Interface
Defines the contract for data structures used in the program, like `add`, `remove`, `get`, and `size` methods.

## Usage
Compile and run the program with three command-line arguments:
* `inputFile`: Path to the text file containing words.
* `cutOffValue`: Minimum frequency count for a word to be included in the output.
* `outputFile`: Path for the file where results will be written.

Example: 
```bash
java FrequentWords input.txt 5 output.txt
```

## Output 
The program generates a detailed timing report in the console, which includes:
* Time taken to read and process the input file.
* The number of words read from the input file.
* Time taken to create and prune the index using both data structures

Example output:
```bash
INFO: Reading file took 434801 ms (~0.435 seconds).
INFO: 565405 words read.

Processing using Sorted Linked List
INFO: Creating index took 76474070 ms (~76.474 seconds).
INFO: 19783 words stored in index.
INFO: Pruning index took 192947 ms (~0.193 seconds).
INFO: 2446 words remaining after pruning.

Processing using Recursive BST
INFO: Creating index took 134945 ms (~0.135 seconds).
INFO: 19783 words stored in index.
INFO: Pruning index took 17063 ms (~0.017 seconds).
INFO: 2446 words remaining after pruning.
```
