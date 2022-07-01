
build: Graph.hpp GraphUI.hpp
	g++ -Wall -g -std=c++11 GraphString.cpp -o gstr
	g++ -Wall -g -std=c++11 GraphStudent.cpp -o gstudent
	g++ -Wall -g -std=c++11 GraphInteger.cpp -o gint
	
bfs1:
	./gstr < ../../hw5/tests/bfs1.in > bfs1.out
	diff -urN ../../hw5/tests/bfs1.std bfs1.out
bfs2:
	./gstr < ../../hw5/tests/bfs2.in > bfs2.out 
	diff -urN ../../hw5/tests/bfs2.std bfs2.out
bfs3:
	./gstr < ../../hw5/tests/bfs3.in > bfs3.out 
	diff -urN ../../hw5/tests/bfs3.std bfs3.out
dfs1:
	./gstr < ../../hw5/tests/dfs1.in > dfs1.out
	diff -urN ../../hw5/tests/dfs1.std dfs1.out
dfs2:
	./gstr < ../../hw5/tests/dfs2.in > dfs2.out 
	diff -urN ../../hw5/tests/dfs2.std dfs2.out
dfs3:
	./gstr < ../../hw5/tests/dfs3.in > dfs3.out 
	diff -urN ../../hw5/tests/dfs3.std dfs3.out
mst1:
	./gstr < ../../hw5/tests/mst1.in > mst1.out
	diff -urN ../../hw5/tests/mst1.std mst1.out
mst2:
	./gstr < ../../hw5/tests/mst2.in > mst2.out
	diff -urN ../../hw5/tests/mst2.std mst2.out
mst3:
	./gstudent < ../../hw5/tests/mst3.in > mst3.out
	diff -urN ../../hw5/tests/mst3.std mst3.out
dijkstra1:
	./gstr < ../../hw5/tests/dijkstra1.in > dijkstra1.out
	diff -urN ../../hw5/tests/dijkstra1.std dijkstra1.out
dijkstra2:
	./gstr < ../../hw5/tests/dijkstra2.in > dijkstra2.out
	diff -urN ../../hw5/tests/dijkstra2.std dijkstra2.out
dijkstra3:
	./gstr < ../../hw5/tests/dijkstra3.in > dijkstra3.out
	diff -urN ../../hw5/tests/dijkstra3.std dijkstra3.out
dijkstra4:
	./gstudent < ../../hw5/tests/dijkstra4.in > dijkstra4.out
	diff -urN ../../hw5/tests/dijkstra4.std dijkstra4.out
	
run: bfs1 bfs2 bfs3 dfs1 dfs2 dfs3 mst1 mst2 mst3 dijkstra1 dijkstra2 dijkstra3 dijkstra4

clean:
	rm -rf bfs1 bfs1.out graph.ps gint gstr gstudent