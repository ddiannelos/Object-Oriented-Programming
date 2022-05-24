CFLAGS= -Wall -g -std=c++11 -fsanitize=address

all: libHashTable

libHashTable: HashTable.o HashTableIterator.o ExtHashTable.o
	ar cr $@.a $^

HashTableIterator.o: HashTableIterator.cpp
	g++ ${CFLAGS} $< -c

HashTable.o: HashTable.cpp
	g++ ${CFLAGS} $< -c

ExtHashTable.o:: ExtHashTable.cpp
	g++ ${CFLAGS} $< -c

clean:
	rm *.o *.a