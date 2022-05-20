#include "HashTable.hpp"

// ***Constructor***
HashTable::HashTable(int capacity) {
    if (capacity < 0) {
        bad_alloc exception;
        throw exception;
    }

    size = 0;
    this->capacity = capacity;
    table = new string*[capacity];
}

// ***CopyConstructor***
HashTable::HashTable(const HashTable& ht) {
    capacity = ht.capacity;
    size = ht.size;

    if (ht.table == nullptr)
        table = nullptr;
    else {
        table = new string*[capacity];
        for (int i = 0; i < capacity; i++)
            *table[i] = *ht.table[i];
    }
}

// ***Deconstructor***
HashTable::~HashTable() {
    for (int i = 0; i < capacity; i++)
        delete table[i];
    delete[] table;
}

// ***getHashCode***
unsigned long HashTable::getHashCode(const char* str) {
    unsigned long hash = 97;
    int c;

    while ((c = *(str++)) != '\0') 
        hash = ((hash << 5) + hash) + c;
    
    return hash;
}

// ***getSize***
int HashTable::getSize() const {
    return size;
}

// ***getCapacity***
int HashTable::getCapacity() const {
    return capacity;
}

// ***isEmpty***
bool HashTable::isEmpty(int pos) const {
    if (pos >= capacity || table[pos] != nullptr)
        return false;
    return true;
}

// ***isTomb***
bool HashTable::isTomb(int pos) const {
    if (pos >= capacity || table[pos] != (string*) TOMB)
        return false;
    return true;
}

// ***isAvailable***
bool HashTable::isAvailable(int pos) const {
    return (isTomb(pos) || isEmpty(pos));
}

// ***add***
bool HashTable::add(const string& s) {
    // Get the hash value for the string
    unsigned long pos = getHashCode(s.c_str());
    int i;

    // Check if an available position
    // exists in the hashTable and 
    // the current string doesn't exist
    // in it
    for (i = pos; i < capacity; i++) 
        if (isAvailable(i))
            break;
        else if (*table[i] == s)
            return false;
    
    // If there is no space for the
    // string throw exception
    if (i == capacity) {
        HashTableException exception;
        throw exception;
    }

    // Insert the string to
    // the hashTable
    if (isEmpty(pos))
        table[i] = new string;
    
    *table[i] = s;
    size++;

    return true;
}

// ***add***
bool HashTable::add(const char* s) {
    return (add(string(s)));
}

// ***remove***
bool HashTable::remove(const string& s) {
    // Get the hashValue of the string
    unsigned long pos = getHashCode(s.c_str());
    int i;

    // Try to find the string in
    // the hashTable
    for (i = pos; i < capacity; i++)
        if (isEmpty(i))
            return false;
        else if (!isTomb(i) && *table[i] == s)
            break;
    
    // If the string doesn't exist
    // in the hashTable return, else
    // remove it
    if (i == capacity)
        return false;

    table[i] = (string *) TOMB;
    size--;

    return true;
}

// ***remove***
bool HashTable::remove(const char* s) {
    return (remove(string(s)));
}

// ***contains***
bool HashTable::contains(const string& s) const {
    // Get the hashValue of the string
    unsigned long pos = getHashCode(s.c_str());

    // Try to find the string inside
    // the hashTable
    for (int i = pos; i < capacity; i++)
        if (isEmpty(i))
            return false;
        else if (!isTomb(i) && *table[i] == s)
            return true;
    return false;
}

// ***contains***
bool HashTable::contains(const char* s) const {
    return (contains(string(s)));
}

string HashTable::print() const {
    string str;
    char buf[128];

    for (int i = 0; i < capacity; i++) {
        if (!isAvailable(i)) {
            sprintf(buf, "%2d. -%s-\n", i, (*table[i]).c_str());
            str.append(buf);
        }
    }

    sprintf(buf, " --- CAPACITY: %d, SIZE: %d ---\n", capacity, size);
    str.append(buf);

    return str;
}

HashTable& HashTable::operator=(const HashTable& t) {

}

HashTable& HashTable::operator+=(const string& str) {

}

HashTable& HashTable::operator+=(const char* s) {

}

HashTable& HashTable::operator-=(const string& str) {

}

HashTable& HashTable::operator-=(const char* s) {

}

HashTable HashTable::operator+(const string& str) const {

}

HashTable HashTable::operator+(const char* s) const {

}

HashTable HashTable::operator-(const string& str) const {

}

HashTable HashTable::operator-(const char* s) const {

}

ostream& operator<<(ostream& stream, const HashTable& t) {

}

HashTable::Iterator HashTable::begin() const {

}

HashTable::Iterator HashTable::end() const {

}
