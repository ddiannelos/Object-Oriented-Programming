#include "ExtHashTable.hpp"

// ***Constructor***
ExtHashTable::ExtHashTable(double upper_bound_ratio, 
                           double lower_bound_ratio,
                           int size) {
    HashTable(size);
    this->upper_bound_ratio = upper_bound_ratio;
    this->lower_bound_ratio = lower_bound_ratio;
}

// ***CopyConstructor***
ExtHashTable::ExtHashTable(const ExtHashTable& t) {
    HashTable(t);
    upper_bound_ratio = t.upper_bound_ratio;
    lower_bound_ratio = t.lower_bound_ratio;
}

// ***rehash***
void ExtHashTable::rehash() {
    // Calculate the rehashValue
    double rehashValue = size/capacity;
    int newCapacity = -1;

    // If rehashValue is more than
    // upperboundratio double the capacity
    if (rehashValue > upper_bound_ratio)
        newCapacity = capacity*2;
    // Else if it is less than
    // lowerboundratio half the capacity
    else if (rehashValue < lower_bound_ratio)
        newCapacity = capacity/2;
    
    // If there is no need for rehash
    // return
    if (newCapacity == -1)
        return;

    // Make a new hashTable with the
    // new capacity
    HashTable newht(newCapacity);

    // Add the old hashTable elements
    // to the new one
    for (int i = 0; i < capacity; i++)
        if (!isAvailable(i))
            newht.add(*table[i]);

    // Deallocate memory of the previous
    // hashTable
    for (int i = 0; i < capacity; i++)
        if (table[i] != nullptr)
            delete table[i];
    delete[] table;

    // Copy the new hashtable to the 
    // old one
    HashTable(newht);  

    cout << "Size: " << size; 
    cout << ", New capacity: " << capacity << endl;  
}

// ***add***
bool ExtHashTable::add(const string& str) {
    if (HashTable::add(str)) {
        rehash();
        return true;
    }
    return false;
}

// ***add***
bool ExtHashTable::add(const char* s) {
    if (HashTable::add(s)) {
        rehash();
        return true;
    }
    return false;
}

// ***remove***
bool ExtHashTable::remove(const string& str) {
    if (HashTable::remove(str)) {
        rehash();
        return true;
    }
    return false;
}

// ***remove***
bool ExtHashTable::remove(const char* s) {
    if (HashTable::remove(s)) {
        rehash();
        return true;
    }
    return false;
}

ExtHashTable& ExtHashTable::operator=(const ExtHashTable& t) {

}

ExtHashTable ExtHashTable::operator+(const string& str) const {

}

ExtHashTable ExtHashTable::operator+(const char* s) const {

}

ExtHashTable ExtHashTable::operator-(const string& str) const {

}

ExtHashTable ExtHashTable::operator-(const char* s) const {

}

ExtHashTable& ExtHashTable::operator+=(const string& str) {

}

ExtHashTable& ExtHashTable::operator+=(const char* s) {

}

ExtHashTable& ExtHashTable::operator-=(const string& str) {

}

ExtHashTable& ExtHashTable::operator-=(const char* s) {

}

ExtHashTable ExtHashTable::operator+(const ExtHashTable& t) const {

}

ExtHashTable& ExtHashTable::operator+=(const ExtHashTable& t) {

}
