#include "ExtHashTable.hpp"

// ***Constructor***
ExtHashTable::ExtHashTable(double upper_bound_ratio, 
                           double lower_bound_ratio,
                           int size) : HashTable(size) {
    this->upper_bound_ratio = upper_bound_ratio;
    this->lower_bound_ratio = lower_bound_ratio;
}

// ***CopyConstructor***
ExtHashTable::ExtHashTable(const ExtHashTable& t) : HashTable(t) {
    upper_bound_ratio = t.upper_bound_ratio;
    lower_bound_ratio = t.lower_bound_ratio;
}

// ***rehash***
void ExtHashTable::rehash(char option) {
    // Calculate the rehashValue
    double rehashValue = (double) size/capacity;
    int newCapacity = -1;

    // Depending on the option, find
    // the new capacity
    if (option == 'a') {
        if (rehashValue > upper_bound_ratio)
            newCapacity = capacity*2;
    }
    else if (option == 'r')  {
        if (rehashValue < lower_bound_ratio)
            newCapacity = capacity/2;
    }
    
    // If there is no need for rehash
    // return
    if (newCapacity == -1)
        return;
    
    // Copy the words inside the
    // hashTable in a buffer
    int bufferSize = size;
    string buffer[bufferSize];

    for (int i = 0, j = 0; i < capacity; i++)
        if (!isAvailable(i))
            buffer[j++] = *table[i];

    // Deallocate hashTable memory
    for (int i = 0; i < capacity; i++)
        if (!isAvailable(i))
            delete table[i];
    delete[] table;
    
    // Make a new hashTable with
    // new capacity and copy the
    // words inside the buffer to
    // the new hashTable
    capacity = newCapacity;
    size = 0;
    table = new string*[capacity];

    for (int i = 0; i < capacity; i++)
        table[i] = nullptr;

    for (int i = 0, j = 0; i < bufferSize; i++)
        HashTable::add(buffer[j++]);

    cout << "--> Size: " << size; 
    cout << ", New capacity: " << capacity << endl;  
}

// ***add***
bool ExtHashTable::add(const string& str) {
    rehash('a');
    if (HashTable::add(str)) {
        return true;
    }
    return false;
}

// ***add***
bool ExtHashTable::add(const char* s) {
    rehash('a');
    if (HashTable::add(s)) {
        return true;
    }
    return false;
}

// ***remove***
bool ExtHashTable::remove(const string& str) {
    if (HashTable::remove(str)) {
        rehash('r');
        return true;
    }
    return false;
}

// ***remove***
bool ExtHashTable::remove(const char* s) {
    if (HashTable::remove(s)) {
        rehash('r');
        return true;
    }
    return false;
}

// ***operator=***
ExtHashTable& ExtHashTable::operator=(const ExtHashTable& t) {
    for (int i = 0; i < capacity; i++)
        if (!isAvailable(i))
            delete table[i];
    delete[] table;
    
    size = t.size;
    capacity = t.capacity;
    upper_bound_ratio = t.upper_bound_ratio;
    lower_bound_ratio = t.lower_bound_ratio;
    table = new string*[capacity];

    for (int i = 0; i < capacity; i++)
        if (!t.isAvailable(i)) {
            table[i] = new string;
            *table[i] = *t.table[i];
        }
        else
            table[i] = t.table[i];

    return *this;
}

// ***operator+***
ExtHashTable ExtHashTable::operator+(const string& str) const {
    ExtHashTable newht(*this);
    newht.add(str);
    return newht;
}

// ***operator+***
ExtHashTable ExtHashTable::operator+(const char* s) const {
    ExtHashTable newht(*this);
    newht.add(s);
    return newht;
}

// ***operator-***
ExtHashTable ExtHashTable::operator-(const string& str) const {
    ExtHashTable newht(*this);
    newht.remove(str);
    return newht;
}

// ***operator-***
ExtHashTable ExtHashTable::operator-(const char* s) const {
    ExtHashTable newht(*this);
    newht.remove(s);
    return newht;
}

// ***operator+=***
ExtHashTable& ExtHashTable::operator+=(const string& str) {
    add(str);
    return *this;
}

// ***operator+=***
ExtHashTable& ExtHashTable::operator+=(const char* s) {
    add(s);
    return *this;
}

// ***operetor-=***
ExtHashTable& ExtHashTable::operator-=(const string& str) {
    remove(str);
    return *this;
}

// ***operator-=***
ExtHashTable& ExtHashTable::operator-=(const char* s) {
    remove(s);
    return *this;
}

// ***operator+***
ExtHashTable ExtHashTable::operator+(const ExtHashTable& t) const {
    ExtHashTable newht(*this);
    for (int i = 0; i < t.getCapacity(); i++)
        if (!t.isAvailable(i))
            newht.add(*t.table[i]);
    
    return newht;
}

// ***operator+=***
ExtHashTable& ExtHashTable::operator+=(const ExtHashTable& t) {
    for (int i = 0; i < t.getCapacity(); i++)
        if (!t.isAvailable(i))
            add(*t.table[i]);
    
    return *this;
}
