#include "ExtHashTable.hpp"
#include "HashTable.hpp"
#include <iostream>

using namespace std;

int main() {
    HashTable h;

    cout << h.add("mitsos") << endl;
    cout << h.add("kostas") << endl;;
    cout << h.add("afroditi") << endl;
    cout << h.add("petros") << endl;
    cout << h.add("mitsos")<< endl;
    cout << h.add("panagiotis") << endl;
    cout << h.add("giorgos") << endl;

    cout << h;

    cout << h.remove("fuckkkk") << endl;
    cout << h.remove("panagiotis") << endl;

    cout << h;
    HashTable newht;
    newht.add("panagiotis");
    newht.add("fuckkk");
    cout << newht;

    cout << newht;
}