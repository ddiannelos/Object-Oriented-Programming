#ifndef __HASH_TABLE_EXCEPTION_HPP
#define __HASH_TABLE_EXCEPTION_HPP

#include <iostream>

class HashTableException : public std::exception {
    public:
        virtual const char* what() const noexcept {
            return " ----- HashTableException -----\n";
        }
};

#endif