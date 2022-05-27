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

    for (int i = 0; i < capacity; i++)
        table[i] = nullptr;
}

// ***CopyConstructor***
HashTable::HashTable(const HashTable& ht) {
    capacity = ht.capacity;
    size = ht.size;

    table = new string*[capacity];
    for (int i = 0; i < capacity; i++)
        if (ht.table[i] != nullptr) {
            table[i] = new string;
            *table[i] = *ht.table[i];
        }
        else
            table[i] = nullptr;
}

// ***Deconstructor***
HashTable::~HashTable() {
    for (int i = 0; i < capacity; i++)
        if (table[i] != nullptr)
            delete table[i];
    delete[] table;
}

// ***getHashCode***
unsigned long HashTable::getHashCode(const char* str) {
    unsigned long hash = 97;
    int c;

    while ((c = *(str++)) != '\0') {
        hash = ((hash << 5) + hash) + c; /* hash * 33 + c */
    }

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
    if (pos >= capacity || table[pos] != TOMB)
        return false;
    return true;
} 

// ***isAvailable***
bool HashTable::isAvailable(int pos) const {
    return (isEmpty(pos) || isTomb(pos));
}

// ***add***
bool HashTable::add(const string& s) {
    if (s == "")
        return false;
    
    // // Get the hashCode for the string
    const unsigned long hashCode = getHashCode(s.c_str());
    int i, pos;

    // // Check if the string already
    // // exsists in the hashTable
    if (contains(s)) {
        return false;
    }

    // // Check if an available position
    // // exists in the hashTable
    for (i = 0; i < capacity; i++) {
        pos = (i + hashCode) % capacity;
        
        if (isAvailable(pos))
            break;
    }
    // If there is no space for the
    // string throw exception
    if (i == capacity) {
        HashTableException exception;
        throw exception;
    }
    
    table[pos] = new string;
    *table[pos] = s;
    size++;

    return true;
}

// ***add***
bool HashTable::add(const char* s) {
    return (add(string(s)));
}

// ***remove***
bool HashTable::remove(const string& s) {
    if (s == "")
        return false;
    
    // Get the hashCode of the string
    const unsigned long hashCode = getHashCode(s.c_str());
    int i, pos;

    // Try to find the string in
    // the hashTable
    for (i = 0; i < capacity; i++) {
        pos = (i + hashCode) % capacity;
        
        if (isEmpty(pos))
            return false;
        else if (*table[pos] == s)
            break;
    }
    
    // If the string doesn't exist
    // in the hashTable return, else
    // remove it
    if (i == capacity)
        return false;

    delete table[pos];
    table[pos] = TOMB;
    size--;

    return true;
}

// ***remove***
bool HashTable::remove(const char* s) {
    return (remove(string(s)));
}

// ***contains***
bool HashTable::contains(const string& s) const {
    if (s == "")
        return false;
    
    // Get the hashCode of the string
    const unsigned long hashCode = getHashCode(s.c_str());

    // Try to find the string inside
    // the hashTable
    for (int i = 0; i < capacity; i++) {
        int pos = (i + hashCode) % capacity;
        
        if (isEmpty(pos))
            return false;
        else if (!isTomb(pos) && *table[pos] == s)
            return true;
    }
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

// ***operator=***
HashTable& HashTable::operator=(const HashTable& t) {
    // Delete the hashTable that was
    // previously allocated
    for (int i = 0; i < capacity; i++)
            if (table[i] != nullptr)
                delete table[i];
    delete[] table;
    
    // Copy the contents of t
    size = t.size;
    capacity = t.capacity;
    table = new string*[capacity];

    for (int i = 0; i < capacity; i++)
        if (t.table[i] != nullptr) {
            table[i] = new string;
            *table[i] = *t.table[i];
        }
        else
            table[i] = nullptr;
    
    return *this;
}

// ***operator+=***
HashTable& HashTable::operator+=(const string& str) {
    add(str);
    return *this;
}

// ***operator+=***
HashTable& HashTable::operator+=(const char* s) {
    add(s);
    return *this;
}

// ***operator-=***
HashTable& HashTable::operator-=(const string& str) {
    remove(str);
    return *this;
}

// ***operator-=***
HashTable& HashTable::operator-=(const char* s) {
    remove(s);
    return *this;
}

// ***operator+***
HashTable HashTable::operator+(const string& str) const {
    HashTable new_table(*this);
    new_table.add(str);
    return new_table;
}

// ***operator+***
HashTable HashTable::operator+(const char* s) const {
    HashTable new_table(*this);
    new_table.add(s);
    return new_table;
}

// ***operator-***
HashTable HashTable::operator-(const string& str) const {
    HashTable new_table(*this);
    new_table.remove(str);
    return new_table;
}

// ***operator-***
HashTable HashTable::operator-(const char* s) const {
    HashTable new_table(*this);
    new_table.remove(s);
    return new_table;
}

// ***operator<<***
ostream& operator<<(ostream& stream, const HashTable& t) {
    stream << t.print();
    return stream;
}

// ***begin***
HashTable::Iterator HashTable::begin() const {
    return Iterator(this);
}

// ***end***
HashTable::Iterator HashTable::end() const {
    return Iterator(this, false);
}
