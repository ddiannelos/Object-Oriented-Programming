#include "HashTable.hpp"

// ***Constructor***
HashTable::Iterator::Iterator(const HashTable* t, bool start) {
    ht = t;

    if (start) {
        for (int i = 0; i < ht->capacity; i++)
            if (!ht->isAvailable(i)) {
                position = i;
                curr = (ht->table)+i;

                return;
            }
    }
    
    position = ht->capacity;
    curr = nullptr; 
}

// ***CopyConstructor***
HashTable::Iterator::Iterator(const Iterator& it){
    position = it.position;
    ht = it.ht;
    if (it.curr == nullptr)
        curr = nullptr;
    else
        curr = it.curr;
}

// ***operator=***
HashTable::Iterator& HashTable::Iterator::operator=(const Iterator& it) {
    position = it.position;
    ht = it.ht;
    curr = it.curr;

    return (*this);
}

// ***operator++***
HashTable::Iterator HashTable::Iterator::operator++() {
    if (curr == nullptr)
        return nullptr;
    
    // Find the next element in the 
    // hashTable
    for (position += 1; position < ht->capacity; position++)
        if (!ht->isAvailable(position)) {
            curr = (ht->table) + position;
            break;
        }
    // If the end of the hashTable is
    // reached delete curr
    if (position == ht->capacity) {
        curr = nullptr;
    }

    return (*this);
}

// ***operator++***
HashTable::Iterator HashTable::Iterator::operator++(int a) {
    if (curr == nullptr)
        return nullptr;
    
    // Save the iterator
    Iterator oldIt(*this);

    // Find the next element in the 
    // hashTable
    for (position += 1; position < ht->capacity; position++)
        if (!ht->isAvailable(position)) {
            curr = (ht->table) + position;
            break;
        }
    // If the end of the hashTable is
    // reached delete curr
    if (position == ht->capacity) {
        curr = nullptr;
    }

    // Return the Iterator before
    // the addition
    return oldIt;
}

// ***operator==***
bool HashTable::Iterator::operator==(const Iterator& it) const {
    return (position == it.position && curr == it.curr && ht == it.ht);
}

// ***operator!=***
bool HashTable::Iterator::operator!=(const Iterator& it) const {
    return (!operator==(it));
}

// ***operator* ***
const string& HashTable::Iterator::operator*() {
    return **curr;
}

// ***operator->***
const string* HashTable::Iterator::operator->() {
    return *curr;
}

// ***pos***
int HashTable::Iterator::pos() const {
    return position;
}

