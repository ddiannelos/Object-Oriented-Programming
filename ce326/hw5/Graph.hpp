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
    
    void dfs(const T& info, bool visited[], list<T> dfs) const;
    void findMinDist(int distance[], int& min, int& minPos);
    list<T> getRoute(const T& from, const T& to, int distance[], int prev[]);

    public:
        Graph(bool isDirectedGraph = true);
        ~Graph();
        bool contains(const T& info);
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

// ***contains***
template <typename T>
bool Graph<T>::contains(const T& info) {
    // Search in the vector to find
    // the vertex
    for (int i = 0; i < size; i++)
        if (edges[i].begin()->from == info)
            return true;

    return false;
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
    edges[size-1].push_back(Edge<T>(info, info, -1));

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
    for (int i = 0; i < size; i++)
        if (edges[i].begin()->from == from) {
            // Check if the edge exists
            for (typename list<Edge<T>>::iterator it = edges[i].begin(); it != edges[i].end(); it++)
                if (it->to == to)
                    return false;
            
            if (edges[i].size() == 1) {
                edges[i].begin()->to = to;
                edges[i].begin()->dist = cost;
            }
            else 
                edges[i].push_back(Edge<T>(from, to, cost));
            
            // If the graph is not directed insert
            // the edge from "to" to "from"
            if (isDirected == false) {
                addEdg(to, from, cost);
            }
        
            return true;
        }

    return false;
}

// ***rmvEdg***
template <typename T>
bool Graph<T>::rmvEdg(const T& from, const T& to) {
    // Check if to exists in the graph
    if (contains(to) == false)
        return false;
    
    // Check if from exists in the graph
    // and if it does, try to find the edge
    // and remove it
    for (int i = 0; i < size; i++)
        if (edges[i].begin()->from == from) {
            typename list<Edge<T>>::iterator it = edges[i].begin();

            for (; it != edges[i].end(); it++)
                if (it->to == to)
                    break;
            
            if (it == edges[i].end())
                return false;

            if (edges[i].size() == 1) {
                edges[i].begin()->to = edges[i].begin()->from;
                edges[i].begin()->dist = -1;
            }
            else
                edges[i].erase(it);

            // If graph is not directed remove the
            // edge from "to" to "from"
            if (isDirected == false) {
                rmvEdg(edges[i].begin()->to, edges[i].begin()->from);
            }

            return true;
        }

    return false;
}

// ***dfs***
template <typename T>
void Graph<T>::dfs(const T& info, bool visited[], list<T> dfsl) const {
    // Find info, check if info
    // is already visited and if 
    // it is not, insert it in the list
    // and dfs every neighbor
    for (int i = 0; i < size; i++)
        if (edges[i].begin()->from == info) {
            if (visited[i] == true)
                return;
            
            visited[i] = true;
            dfsl.push_back(edges[i].begin()->from);

            for (typename list<Edge<T>>::const_iterator it = edges[i].begin(); it != edges[i].end(); it++)
                dfs(it->to, visited, dfsl);
        }
}

// **dfs***
template <typename T>
list<T> Graph<T>::dfs(const T& info) const {
    bool visited[size];
    list<T> dfsl;

    for (int i = 0; i < size; i++)
        visited[i] = false;

    dfs(info, visited, dfsl);
    
    return dfsl;
}

// ***bfs***
template <typename T>
list<T> Graph<T>::bfs(const T& info) const {
    bool visited[size];
    list<T> queue;
    list<T> bfs;

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
        
        for (int i = 0; i < size; i++)
            if (edges[i].begin()->from == vtx) {
                for (typename list<Edge<T>>::const_iterator it = edges[i].begin(); it != edges[i].end(); it++) {
                    int j = 0;
                    for (; j < size; j++)
                        if (edges[j].begin()->from == it->to)
                            break;
                    
                    if (visited[j] == false) {
                        visited[j] = true;
                        queue.push_back(it->to);
                    }
                }
            }
    }

    return bfs;
}

// ***findMinDist***
template <typename T>
void Graph<T>::findMinDist(int distance[], int& min, int& minPos) {
    min = distance[0];
    minPos = 0;

    for (int i = 1; i < size; i++)
        if (min > distance[i]) {
            min = distance[i];
            minPos = i;
        }
}

template <typename T>
list<T> Graph<T>::getRoute(const T& from, const T& to, int distance[], int prev[]) {
    list<T> dijkstra;

    for (int i = 0; i < size; i++)
        if (edges[i].begin()->from == to) {
            if (distance[i] == INT_MAX)
                return dijkstra;
            
            int pos = i;

            while (edges[pos].begin()->from != from) {
                dijkstra.push_front(edges[pos].begin()->from);
                pos = prev[pos];
            }
            dijkstra.push_front(edges[pos].begin()->from);
        }

    return dijkstra;
}
// ***dijkstra***
template <typename T>
list<T> Graph<T>::dijkstra(const T& from, const T& to) {
    int distance[size];
    int prev[size];
    list<T> queue;

    // Set the distance of every vertex
    // except "from" to INT_MAX and add
    // every vertex on the queue
    for (int i = 0; i < size; i++) {
        if (edges[i].begin()->from == from)
            distance[i] = 0;
        else
            distance[i] = INT_MAX;

        queue.push_back(edges[i].begin()->from);
    }

    // While the queue is not empty
    while (queue.empty() == false) {        
        // Find the vertex with the min
        // distance
        int min, minPos;
        findMinDist(distance, min, minPos);

        // Save the value of the vertex, check 
        // if it is the destination, if it is not
        // remove it from the queue        
        typename list<T>::iterator qit = queue.begin();

        for (int i = 0; qit != queue.end(); qit++, i++)
            if (i == minPos)
                break;
        
        T vtx = *qit;
        
        if (vtx == to)
            break;
        
        queue.erase(qit);
        
        // Find the vertex and for each neighbor
        // that it is not in the queue set the distance
        // accordingly
        for (int i = 0; i < size; i++)
            if (edges[i].begin()->from == vtx) {
                typename list<Edge<T>>::iterator lit = edges[i].begin();

                for (; lit != edges[i].end(); lit++) {
                    typename list<T>::iterator qit = queue.begin();

                    for (; qit != queue.end(); qit++)
                        if (lit->to == *qit)
                            break;
                    
                    if (qit == queue.end())
                        continue;
                    
                    int pos;

                    for (pos = 0; pos < size; pos++)
                        if (edges[pos].begin()->from == lit->to)
                            break;
                    
                    int dist = min + lit->dist;
                    
                    if (dist < distance[pos]) {
                        distance[pos] = dist;
                        prev[pos] = i;
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
            if (it->from != it->to)
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
    file.open(filename);

    if (isDirected == true)
        file << "digraph G {" << endl;
    else
        file << "graph G {" << endl;
    
    for (int i = 0; i < size; i++) {
        string symbol;
        
        if (isDirected == true)
            symbol = " -> ";
        else
            symbol = " -- ";
        
        typename list<Edge<T>>::const_iterator it = edges[i].begin();

        for (; it != edges[i].end(); it++) {
            file << "\t" << edges[i].begin()->from;
            file << symbol << it->to << ";" << endl;
        }
    }

    file << "}";

    file.close();

    return true;
}

#endif
