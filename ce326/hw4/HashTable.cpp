#include "HashTable.hpp"

HashTable::HashTable(int capacity) {

}

HashTable::HashTable(const HashTable& ht) {

}

HashTable::~HashTable() {

}

unsigned long HashTable::getHashCode(const char* str) {
    unsigned long hash = 97;
    int c;

    while ((c = *(str++)) != '\0') 
        hash = ((hash << 5) + hash) + c;
    
    return hash;
}

int HashTable::getSize() const {

}

int HashTable::getCapacity() const {

}

bool HashTable::isEmpty(int pos) const {

}

bool HashTable::isTomb(int pos) const {

}

bool HashTable::isAvailable(int pos) const {

}

bool HashTable::add(const string& s) {

}

bool HashTable::add(const char* s) {

}

bool HashTable::remove(const string& s) {

}

bool HashTable::remove(const char* s) {

}

bool HashTable::contains(const string& s) const {

}

bool HashTable::contains(const char* s) const {

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
