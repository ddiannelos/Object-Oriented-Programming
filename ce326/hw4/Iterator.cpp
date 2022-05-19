#include "HashTable.hpp"

class HashTable::Iterator {
    string** curr;
    const HashTable* ht;
    int position;

    public:
        Iterator(const HashTable* t);
        Iterator(const HashTable* t, bool end);
        Iterator(const Iterator& it);
        
        Iterator& operator=(const Iterator& it);

        Iterator operator++();
        Iterator operator++(int a);

        bool operator==(const Iterator& it) const;
        bool operator!=(const Iterator& it) const;

        const string& operator*();
        const string* operator->();

        int pos() const;
};

HashTable::Iterator::Iterator(const HashTable* t) {

}

HashTable::Iterator::Iterator(const HashTable* t, bool end) {

}

HashTable::Iterator::Iterator(const Iterator& it){

}

HashTable::Iterator& HashTable::Iterator::operator=(const Iterator& it) {
    
}

HashTable::Iterator HashTable::Iterator::operator++() {

}

HashTable::Iterator HashTable::Iterator::operator++(int a) {

}

bool HashTable::Iterator::operator==(const Iterator& it) const {

}

bool HashTable::Iterator::operator!=(const Iterator& it) const {

}

const string& HashTable::Iterator::operator*() {

}

const string* HashTable::Iterator::operator->() {

}

int HashTable::Iterator::pos() const {
    
}

