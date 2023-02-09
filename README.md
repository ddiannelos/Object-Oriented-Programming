# Object Oriented Programming
This repository contains assignments for the course object oriented programming.

## Description
It contains five completed assignments in order to acquire in depth knowledge of the object oriented logic. The assignements are the following:

### 1. HW1-Compressed Trie
This assignment contains a program that implements an english dictionary via a compressed trie with radix the english alphabet (a-z). The program continuously takes the following commands as input:

- `-i word`: inserts the word `word` in the trie. It returns `ADD word OK` on success or `ADD work NOK` on failure.

- `-r word`: removes the word `word` from the trie. It returns `RMV word OK` on success or `RMV word NOK` on failure.

- `-f word`: tries to find the `word` inside the trie. It returns `FND word OK` on success or `FND word NOK` on failure.

- `-p`: prints the pre-order traversal of the trie.

- `-d`: prints all the words inside the trie in alphabetic order.

- `-w word X`: searches the Trie for all words of the same length as `word` that differ exactly `X` characters from the `word` and it prints them. 

- `-s suffix`: searches the Trie for all the words that ends with `suffix` and it prints them.

- `-q`: terminates the program.

#### Built With
- Java

#### Contains
- HW1.java
- Trie.java
- TrieNode.java

#### Execution
Write the following commands in the terminal:
```
$ javac *.java
$ java HW1.java
```

### 2. HW2-Image Processing
This assignment contains an image processing program with graphical interface. In the program, you insert PPM images or YUV images and perform modifications on the image. The modifications are the following:
- Change the colorization of the image to gray
- Double the size of the image
- Half the size of the image
- Rotate the image clockwise

Moreover, you can insert a folder with different images in it and use photostacking method to combine all these images to create a new one and balance the histogram of an image.


#### Built With
- Java

#### Contains
- FileFilters.java
- Histogram.java
- Image.java
- ImageProcessing.java
- ImageProcessing2.java
- PPMImage.java
- PPMImageStacker.java
- RGBImage.java
- RGBPixel.java
-UnsupportedFileFormatException.java
- YUVImage.java
- YUVPixel.java

#### Execution
Write the following commands in the terminal:
```
$ javac *.java
$ java ImagProcessing.java or java ImageProcessing2.java
```

### 3. HW3-Sudoku Puzzle in Swing Environment
This assignment contains a sudoku game program with graphical interaface.
In order to use the program, University's of Thessaly VPN connection should be used. To start a game press, `New` and select the difficulty of the game.

#### Built With
- Java

#### Contains
- Homework3.java

#### Execution
Write the following commands in the terminal:
```
$ javac Homework3.java
$ java Homework3.java
```
### 4. HW4-Hash Table
This assignment contains a program that implements an english dictionary using a hash table. In order to use the program a `main.cpp` file should be created.

#### Built With
- C++

#### Contains
- ExtHashTable.cpp
- ExtHashTable.hpp
- HashTable.cpp
- HashTable.hpp
- HashTableException.hpp
- HashTableIterator.cpp
- Makefile

#### Execution
Write the following commands in the terminal:

```
$ make
$ ./main
```
### 5. HW5-Graph
This assignment contains a program that implements a directed or undirected graph. Firstly, the program takes as input the string `graph` or `digraph` in order to distinguish if the graph is undirecdted or directed respectively. Then it continuously takes the following commands as input:

- `av node`: inserts a new vertex `node` in the graph. It returns `av node OK` on success or `av node NOK` on failure.

- `rv node`: removes the vertex `node` from the graph. It returns `rv node OK` on success or `rv node NOK` on failure.

- `ae from to`: inserts a new edge from vertex `from` to vertex `to`. It returns `ae from to OK` on success or `ae from to NOK` on failure.

- `re from to`: removes the edge from vertex `from` to vertex `to`. It returns `re from to OK` on success or `re from to NOK` on failure.

- `dot file`: prints the content of the graph in dot format in the file `file`. It returns `dot file OK` on success or `dot file NOK` on failure.

- `dfs node`: prints the depth first search traversal of the graph from the vertex `node`.

- `bfs node`: prints the breadth first search traversal of the graph from vertex `node`. 

- `dijkstra from to`: prints the path from `from` to `to` using the dijkstra algorithm.

- `mst`: prints the min spanning tree of the graph.

- `q`: terminates the program.

#### Built With 
- C++

#### Contains
- Graph.hpp
- GraphInteger.cpp
- GraphString.cpp
- GraphStudent.cpp
- GraphUI.hpp
- Makefile
- Student.hpp
- UnionFind.hpp

#### Execution
Write the following commands in the terminal:
```
$ make build
$ make run
```

### Dependencies
- Java environment should be installed in order to run the java assignments 
- C++ compiler should be installed in order to run c++ assignemnts.

## Authors

### Dimitrios-Eleftherios Diannelos
