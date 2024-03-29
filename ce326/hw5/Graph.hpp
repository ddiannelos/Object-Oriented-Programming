#ifndef _GRAPH_HPP_ 
#define _GRAPH_HPP_

#include <iostream>
#include <list>
#include <vector>
#include <iterator>
#include <climits>
#include <fstream>
#include <string>
#include "UnionFind.hpp"

using namespace std;

template<typename T>
struct Edge {
    T from;
    T to;
    int dist;
    
    Edge(T f, T t, int d) : from(f), to(t), dist(d) {}
    bool operator<(const Edge<T>& e) const { return (dist < e.dist); }
    bool operator>(const Edge<T>& e) const { return (dist > e.dist); }
    
    template<typename U>
    friend std::ostream& operator<<(std::ostream& out, const Edge<U>& e);
};

template<typename T>
std::ostream& operator<<(std::ostream& out, const Edge<T>& e) {
    out << e.from << " -- " << e.to << " (" << e.dist << ")";
    return out;
}

template <typename T>
class Graph {
    vector<list<Edge<T>>> edges;
    int size;
    bool isDirected;
    
    int findPos(const T& info) const;
    void dfs(const T& info, bool visited[], list<T> *dfsl) const;
    T findMinDistVtx(int distance[], list<T> *queue, int& minDist);
    bool inQueue(const T& info, list<T> queue);
    list<T> getRoute(const T& from, const T& to, int distance[], int prev[]);

    public:
        Graph(bool isDirectedGraph = true);
        ~Graph();
        bool contains(const T& info) const;
        bool addVtx(const T& info);
        bool rmvVtx(const T& info);
        bool addEdg(const T& from, const T& to, int distance);
        bool rmvEdg(const T& from, const T& to);
        list<T> dfs(const T& info) const;
        list<T> bfs(const T& info) const;
        list<T> dijkstra(const T& from, const T& to);
        list<Edge<T>> mst();
        bool print2DotFile(const char *filename) const;
};

//***Constructor***
template <typename T>
Graph<T>::Graph(bool isDirectedGraph) {
    size = 0;
    isDirected = isDirectedGraph;
}

// ***Deconstructor***
template <typename T>
Graph<T>::~Graph() {
}

// ***findPos***
template <typename T>
int Graph<T>::findPos(const T& info) const {
    // Try to find the position of 
    // and element in the vector
    for (int i = 0; i < size; i++)
        if (edges[i].begin()->from == info)
            return i;
    
    return -1;
}

// ***contains***
template <typename T>
bool Graph<T>::contains(const T& info) const {
    // Search in the vector to find
    // the vertex
    if (findPos(info) == -1)
        return false;
    
    return true;
}

// ***addVtx***
template <typename T>
bool Graph<T>::addVtx(const T& info) {
    // Check if info exists in
    // the vector
    if (contains(info))
        return false;
    
    // Insert the new vertex
    edges.resize(++size);
    edges[size-1].push_back(Edge<T>(info, info, INT_MAX));

    return true;
}

// ***rmvVtx***
template <typename T>
bool Graph<T>::rmvVtx(const T& info) {
    // Check if info exists in the
    // graph
    if (contains(info) == false)
        return false;
    
    // Find info in the vector and
    // remove it
    typename vector<list<Edge<T>>>::iterator vit = edges.begin();
    for (; vit != edges.end(); vit++)
        if (vit->front().from == info)
            break;
    
    edges.erase(vit);
    edges.resize(--size);

    // Remove the edges that connect
    // to the removed vertex
    for (int i = 0; i < size; i++) {
        rmvEdg(edges[i].begin()->from, info);
    }

    return true;
}

// ***addEdg***
template <typename T>
bool Graph<T>::addEdg(const T& from, const T& to, int cost) {
    // Check if to exists in the graph
    if (contains(to) == false)
        return false;

    // Check if from exists in the graph and
    // if it does, add an edge
    int pos = findPos(from);
    
    if (pos == -1)
        return false;
    
    // Check if the edge exists
    for (typename list<Edge<T>>::iterator it = edges[pos].begin(); it != edges[pos].end(); it++)
        if (it->to == to)
            return false;
    
    if (edges[pos].begin()->dist == INT_MAX) {
        edges[pos].begin()->to = to;
        edges[pos].begin()->dist = cost;
    }
    else {        
        typename list<Edge<T>>::iterator it = edges[pos].begin();
        for (; it != edges[pos].end(); it++)
            if (findPos(to) < findPos(it->to))
                break;

        if (it == edges[pos].end())  
            edges[pos].push_back(Edge<T>(from, to, cost));
        else
            edges[pos].insert(it, Edge<T>(from, to, cost));
    }
    
    // If the graph is not directed insert
    // the edge from "to" to "from"
    if (isDirected == false) {
        addEdg(to, from, cost);
    }

    return true;
}

// ***rmvEdg***
template <typename T>
bool Graph<T>::rmvEdg(const T& from, const T& to) {    
    // Check if from exists in the graph
    // and if it does, try to find the edge
    // and remove it
    int pos = findPos(from);
    if (pos == -1) 
        return false;
    
    typename list<Edge<T>>::iterator it = edges[pos].begin();
    for (; it != edges[pos].end(); it++)
        if (it->to == to)
            break;
    
    if (it == edges[pos].end())
        return false;

    if (edges[pos].size() == 1) {
        edges[pos].begin()->to = edges[pos].begin()->from;
        edges[pos].begin()->dist = INT_MAX;
    }
    else
        edges[pos].erase(it);

    // If graph is not directed remove the
    // edge from "to" to "from"
    if (isDirected == false) {
        rmvEdg(edges[pos].begin()->to, edges[pos].begin()->from);
    }

    return true;
}

// ***dfs***
template <typename T>
void Graph<T>::dfs(const T& info, bool visited[], list<T> *dfsl) const {
    // Find info, check if info
    // is already visited and if 
    // it is not, insert it in the list
    // and dfs every neighbor
    int pos = findPos(info);
    
    if (visited[pos] == true)
        return;
    
    visited[pos] = true;
    dfsl->push_back(edges[pos].begin()->from);

    for (typename list<Edge<T>>::const_iterator it = edges[pos].begin(); it != edges[pos].end(); it++)
        dfs(it->to, visited, dfsl);
}

// **dfs***
template <typename T>
list<T> Graph<T>::dfs(const T& info) const {
    bool visited[size];
    list<T> dfsl;

    if (contains(info) == false)
        return dfsl;

    for (int i = 0; i < size; i++)
        visited[i] = false;

    dfs(info, visited, &dfsl);
    
    return dfsl;
}

// ***bfs***
template <typename T>
list<T> Graph<T>::bfs(const T& info) const {
    bool visited[size];
    list<T> queue;
    list<T> bfs;

    if (contains(info) == false)
        return bfs;

    for (int i = 0; i < size; i++)
        visited[i] = false;

    // Insert info to the queue
    for (int i = 0; i < size; i++)
        if (edges[i].begin()->from == info) {
            visited[i] = true;
            queue.push_back(info);
        }
    
    // While queue is not empty take the
    // first vertex of the queue, add it to the
    // bfs list and then for every neighbor of
    // the vertex check if it is visited. If
    // it isn't then mark it as visited and add
    // it in the queue
    while (queue.empty() == false) {
        T vtx = queue.front();
        queue.pop_front();
        bfs.push_back(vtx);
        
        int pos = findPos(vtx);
        
        if (pos == -1)
            return bfs;

        for (typename list<Edge<T>>::const_iterator it = edges[pos].begin(); it != edges[pos].end(); it++) {
            int childPos = findPos(it->to);
            
            if (visited[childPos] == false) {
                visited[childPos] = true;
                queue.push_back(it->to);
            }
        }      
    }

    return bfs;
}

// ***findMinDistVtx***
template <typename T>
T Graph<T>::findMinDistVtx(int distance[], list<T> *queue, int& minDist) {
    typename list<T>::iterator qit = queue->begin();
    typename list<T>::iterator minqit;
    minqit = qit;
    minDist = distance[findPos(*qit)];

    for (; qit != queue->end(); qit++) {
        int pos = findPos(*qit);
        if (minDist > distance[pos]) {
            minDist = distance[pos];
            minqit = qit;
        }
    }
    T vtx = *minqit;
    queue->erase(minqit);
    
    return vtx;
}

// ***inQueue***
template <typename T>
bool Graph<T>::inQueue(const T& info, list<T> queue) {
    typename list<T>::iterator qit = queue.begin();

    for (qit = queue.begin(); qit != queue.end(); qit++)
        if (*qit == info)
            return true;
    
    return false;
}


// ***getRoute***
template <typename T>
list<T> Graph<T>::getRoute(const T& from, const T& to, int distance[], int prev[]) {
    list<T> dijkstra;

    int pos = findPos(to);

    if (prev[pos] == -1)
        return dijkstra;

    while (edges[pos].begin()->from != from) {
        dijkstra.push_front(edges[pos].begin()->from);
        pos = prev[pos];
    }
    dijkstra.push_front(edges[pos].begin()->from);

    return dijkstra;
}

// ***dijkstra***
template <typename T>
list<T> Graph<T>::dijkstra(const T& from, const T& to) {
    int distance[size];
    int prev[size];
    list<T> queue;

    // Set the distance of all vertexes to
    // infinity except the source and add
    // all the vertexes to the queue
    for (int i = 0; i < size; i++) {
        if (edges[i].begin()->from == from)
            distance[i] = 0;
        else
            distance[i] = INT_MAX;

        prev[i] = -1;
        queue.push_back(edges[i].begin()->from);
    }

    // While the queue is not empty
    while (queue.empty() == false) {
        typename list<T>::iterator minqit;
        int minDist;

        // Find the vertex in queue with the minimum
        // distance and remove it from the queue
        T vtx = findMinDistVtx(distance, &queue, minDist);
        
        if (vtx == to)
            break;
        
        // Find the neighbors of the vertex
        // that are still in the queue and
        // set their distance and prev 
        // accordingly
        int pos = findPos(vtx);

        typename list<Edge<T>>::iterator lit = edges[pos].begin();

        for (; lit != edges[pos].end(); lit++) {
            if (inQueue(lit->to, queue) == false)
                continue;
            
            if (minDist != INT_MAX) {
                int alt = minDist + lit->dist;
                int nextPos = findPos(lit->to);

                if (alt < distance[nextPos]) {
                    distance[nextPos] = alt;
                    prev[nextPos] = pos;
                }
            }
        }
    }

    return getRoute(from, to, distance, prev);
}

// ***mst***
template <typename T>
list<Edge<T>> Graph<T>::mst() {
    list<Edge<T>> mst;
    list<Edge<T>> edgs;
    list<T> vtx;

    // Check if the graph is directed
    if (isDirected == true)
        return mst;

    // Insert the vertexes and edges
    // to lists
    for (int i = 0; i < size; i++) {
        typename list<Edge<T>>::iterator it = edges[i].begin();

        for (; it != edges[i].end(); it++)
            if (it->dist != INT_MAX)
                edgs.push_back(*it);
        
        vtx.push_back(edges[i].begin()->from);
    }

    // Sort the edges and create
    // a unionfind object
    edgs.sort();
    UnionFind<T> unionfind = UnionFind<T>(size, vtx);

    // While the edges list is not empty
    // pick the lowest cost edge and check
    // if it creates a circle, if it is not
    // union the 2 vertexes and insert the edge
    // to the mst list
    while (edgs.empty() == false) {
        Edge<T> edg = edgs.front();
        edgs.pop_front();

        int from = unionfind._find(edg.from);
        int to = unionfind._find(edg.to);

        if (from != to) {
            mst.push_back(edg);
            unionfind._union(edg.from, edg.to);
        }
    }

    return mst;
}

// ***print2DotFile***
template <typename T>
bool Graph<T>::print2DotFile(const char *filename) const {
    ofstream file;
    string symbol;
    list<Edge<T>> queue;
    
    file.open(filename);

    if (isDirected == true) {
        file << "digraph G {" << endl;
        symbol = " -> ";
    }
    else {
        file << "graph G {" << endl;
        symbol = " -- ";
    }   
    
    for (int i = 0; i < size; i++) {        
        typename list<Edge<T>>::const_iterator it = edges[i].begin();

        for (; it != edges[i].end(); it++) {
            if (isDirected == false) {
                typename list<Edge<T>>::iterator qit = queue.begin();
                
                for (; qit != queue.end(); qit++)
                    if (qit->from == it->to && qit->to == it->from)
                        break;
                
                if (qit != queue.end())
                    continue;
            }
            
            if (it->dist != INT_MAX) {
                file << "\t" << edges[i].begin()->from;
                file << symbol << it->to;
                file << " [label=\"" << it->dist << "\"];" << endl;
                queue.push_back(*it);
            }
            else
                file << "\t" << edges[i].begin()->from << endl;
        }
    }

    file << "}";

    file.close();

    return true;
}

#endif
