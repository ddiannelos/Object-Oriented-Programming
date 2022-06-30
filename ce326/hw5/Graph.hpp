
#ifndef _GRAPH_HPP_ 
#define _GRAPH_HPP_

#include <list>
#include <vector>
#include <iterator>
#include <typeinfo>

using namespace std;

template<typename T>
struct Edge {
    T from;
    T to;
    int dist;
    Edge(T f, T t, int d) : from(f), to(t), dist(d) {}
    bool operator<(const Edge<T>& e) const {
        return (dist < e.dist);
    }
    bool operator>(const Edge<T>& e) const {
        return (dist > e.dist);
    }
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
    
    void dfs(const T& info, bool *visited, list<T> dfs);

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
        void print2DotFile(const char *filename) const;
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
    // the vertices
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
    
    // Insert the new vertices
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
        if (vit.front()->from == info)
            break;
    
    edges.erase(vit);
    edges.resize(--size);

    // Remove the edges that connect
    // to the removed vertices
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
                if (it.to == to)
                    return false;
            
            if (edges[i].size() == 1) {
                edges[i].begin()->to = to;
                edges[i].begin()->dest = cost;
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
                if (it.to == to)
                    break;
            
            if (it == edges[i].end())
                return false;

            if (edges[i].size() == 1) {
                edges[i].begin()->to = edges[i].begin()->from;
                edges[i].begin()->dest = -1;
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
void Graph<T>::dfs(const T& info, bool visited[], list<T> dfs) {
    // Find info, check if info
    // is already visited and if 
    // it is not, insert it in the list
    // and dfs every neighbor
    for (int i = 0; i < size; i++)
        if (edges[i].begin()->from == info) {
            if (visited[i] == true)
                return;
            
            visited[i] = true;
            dfs.push_back(edges[i].begin()->from);

            for (typename list<Edge<T>>::iterator it = edges[i].begin(); it != edges[i].end(); it++)
                dfs(it->to, visited, dfs);
        }
}

// **dfs***
template <typename T>
list<T> Graph<T>::dfs(const T& info) const {
    bool visited[size];
    list<T> dfs;

    for (int i = 0; i < size; i++)
        visited[i] = false;

    dfs(info, visited, dfs);
    
    return dfs;
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
        if (edges[i].begin->from == info) {
            visited[i] = true;
            queue.push_back(info);
        }
    
    // While queue is not empty take the
    // first vertice of the queue, add it to the
    // bfs list and then for every neighbor of
    // the vertice check if it is visited. If
    // it isn't then mark it as visited and add
    // it in the queue
    while (!queue.empty()) {
        T vtx = queue.front();
        queue.pop_front();
        bfs.push_back(vtx);
        
        for (int i = 0; i < size; i++)
            if (edges[i].begin->from == vtx) {
                for (typename list<Edge<T>>::iterator it = edges[i].begin(); it != edges[i].end(); it++) {
                    int j = 0;
                    for (; j < size; j++)
                        if (edges[j].begin()->front == it->to)
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

#endif
