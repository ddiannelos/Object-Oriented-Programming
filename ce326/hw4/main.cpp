#include <iostream>
#include <fstream>
#include <string>

#include "HashTable.hpp"
#include "HashTableException.hpp"
#include <stdlib.h>

using namespace std;

int main(int argc, char *argv[]) {
    HashTable h;

    h.add("i");
    h.add("like");
    h.add("playing");
    h.add("league");
    cout << h << endl;

    for (HashTable::Iterator it = h.begin(); it != h.end(); it++)
        cout << it.pos() << endl;
}