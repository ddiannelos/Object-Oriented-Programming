CFLAGS= -Wall -g

all: main

main: HashTable.o HashTableIterator.o ExtHashTable.o
	g++ ${CFLAGS} $^ HashTableException.hpp main.cpp -o $@

HashTable.o: HashTable.cpp
	g++ ${CFLAGS} $^ -c

HashTableIterator.o: HashTableIterator.cpp
	g++ ${CFLAGS} $^ -c

ExtHashTable.o: ExtHashTable.cpp
	g++ ${CFLAGS} $^ -c

clean:
	rm *.o main