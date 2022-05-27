#include <iostream>
#include <fstream>
#include <string>

#include "HashTable.hpp"
#include "HashTableException.hpp"
#include <stdlib.h>

using namespace std;

int main(int argc, char *argv[]) {
    HashTable h(12);
    h.add("coffee");
    h.add("sugar");
    h.add("orangejuice");
    h.add("tea");
    h.add("lemonade");
    cout << h << endl;
}