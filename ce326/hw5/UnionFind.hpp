#ifndef _UNION_FIND_HPP_
#define _UNION_FIND_HPP_

#include <list>
#include <iterator>

using namespace std;

template <typename T>
class UnionFind {
    int *array;
    int size;
    list<T> items;

    int getPos(T item);

    public:
        UnionFind(int size, list<T> items);
        ~UnionFind();
        int _find(int item);
        int _find(T item);
        void _union(T fitem, T sitem);
};

// ***Constructor***
template <typename T>
UnionFind<T>::UnionFind(int size, list<T> items) : size(size), items(items) {
    array = new int[size];
   
    for (int i = 0; i < size; i++)
        array[i] = i;
}

// ***Deconstructor***
template <typename T>
UnionFind<T>::~UnionFind() {
    delete[] array;
}

// ***getPos***
template <typename T>
int UnionFind<T>::getPos(T item) {
    int pos = 0;
    typename list<T>::iterator it = items.begin();
    
    for (; it != items.end(); it++, pos++)
        if (*it == item)
            break;
    
    if (it == items.end())
        return -1;

    return pos;
}

// ***_find***
template <typename T>
int UnionFind<T>::_find(int item) {
    if (array[item] == item)
        return item;
    
    return _find(array[item]);
}

// ***_find***
template <typename T>
int UnionFind<T>::_find(T item) {
    return _find(getPos(item));
}

// ***_union***
template <typename T>
void UnionFind<T>::_union(T fitem, T sitem) {
    int pos = _find(fitem);
    int value = _find(sitem);

    array[pos] = value;
}

#endif
