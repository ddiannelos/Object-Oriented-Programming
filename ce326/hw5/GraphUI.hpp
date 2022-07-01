
#ifndef _GRAPH_UI_
#define _GRAPH_UI_

#include <string>
#include "Graph.hpp"

template <typename T>
int graphUI() {  
    string option, line;
    bool digraph = false;
  
    cin >> option;
    if(!option.compare("digraph"))
        digraph = true;
    Graph<T> g(digraph);
  
    while(true) {
        std::stringstream stream;
        cin >> option;
    
        if(!option.compare("av")) {
            getline(std::cin, line);
            stream << line;
            
            T vtx(stream);
            
            if(g.addVtx(vtx))
                cout << "av " << vtx << " OK\n";
            else
                cout << "av " << vtx << " NOK\n";
        }
        else if(!option.compare("rv")) {
            getline(std::cin, line);
            stream << line;
            
            T vtx(stream);
            
            if (g.rmvVtx(vtx))
                cout << "rv " << vtx << " OK\n";
            else
                cout << "rv " << vtx << " NOK\n";
        }
        else if(!option.compare("ae")) {
            getline(std::cin, line);
            stream << line;
            
            T from(stream);
            T to(stream);
            int cost;
            stream >> cost;
            
            if (g.addEdg(from, to, cost))
                cout << "ae " << from << " " << to << " OK\n";
            else
                cout << "ae " << from << " " << to << " NOK\n";
        }
        else if(!option.compare("re")) {
            getline(std::cin, line);
            stream << line;
            
            T from(stream);
            T to(stream);
            
            if (g.rmvEdg(from, to))
                cout << "re " << from << " " << to << " OK\n";
            else
                cout << "re " << from << " " << to << " NOK\n";
        }
        else if(!option.compare("dot")) {
            getline(std::cin, line);
            stream << line;
            
            string filename;
            stream >> filename;
            
            if (g.print2DotFile(filename.c_str()))
                cout << "dot " << filename << " OK\n";
            else
                cout << "dot " << filename << " NOK\n";
        }
        else if(!option.compare("bfs")) {
            getline(std::cin, line);
            stream << line;
            
            T vtx(stream);
            list<T> bfs = g.bfs(vtx);
            typename list<T>::iterator it = bfs.begin();
            
            cout << "\n----- BFS Traversal -----\n";
            cout << *(it++);
            
            for (; it != bfs.end(); it++)
                cout << " -> " << *it;
            
            cout << "\n-------------------------\n";
        }
        else if(!option.compare("dfs")) {
            getline(std::cin, line);
            stream << line;

            T vtx(stream);
            list<T> dfs = g.dfs(vtx);
            typename list<T>::iterator it = dfs.begin();
            
            cout << "\n----- DFS Traversal -----\n";
            cout << *(it++);
            
            for (; it != dfs.end(); it++)
                cout << " -> " << *it;
            
            cout << "\n-------------------------\n";
        }
        else if(!option.compare("dijkstra")) {
            getline(std::cin, line);
            stream << line;
            
            T from(stream);
            T to(stream);
            list<T> dijkstra = g.dijkstra(from, to);
            typename list<T>::iterator it = dijkstra.begin();
            
            cout << "Dijkstra (" << from << " - " << to <<"): ";
            cout << *(it++);
            
            for (; it != dijkstra.end(); it++)
                cout << ", " << *it;
            
            cout << "\n-------------------------\n";
        }
        else if(!option.compare("mst")) {
            int sum = 0;
            list<Edge<T>> mst = g.mst();
            typename list<Edge<T>>::iterator it = mst.begin();
            
            cout << "\n--- Min Spanning Tree ---\n";
            sum += it->dist;
            cout << *(it++);

            for (; it != mst.end(); it++) {
                cout << endl << *it;
                sum += it->dist;
            }
            
            cout << "\nMST Cost: " << sum << endl;
        }
        else if(!option.compare("q")) {
            cerr << "bye bye...\n";
            return 0;
        }
        else if(!option.compare("#")) {
            string line;
            getline(cin,line);
            cerr << "Skipping line: " << line << endl;
        }
        else {
            cout << "INPUT ERROR\n";
            return -1;
        }
    }
    return -1;  
}

#endif
