package com.aokolnychyi.ds.graph;

public class UniqueVerticesALGraphExamples {

  public static void main(String[] args) {
    final UniqueVerticesALGraph<Integer> uniqueVerticesALGraph = new UniqueVerticesALGraph<>();

    uniqueVerticesALGraph.addEdge(0, 1);
    uniqueVerticesALGraph.addEdge(0, 4);
    uniqueVerticesALGraph.addEdge(0, 5);

    uniqueVerticesALGraph.addEdge(1, 4);
    uniqueVerticesALGraph.addEdge(1, 3);

    uniqueVerticesALGraph.addEdge(2, 1);

    uniqueVerticesALGraph.addEdge(3, 2);
    uniqueVerticesALGraph.addEdge(3, 4);

    System.out.println("DFS");
    uniqueVerticesALGraph.performDFS();

    System.out.println("BFS");
    uniqueVerticesALGraph.performBFS();

    uniqueVerticesALGraph.removeVertex(4);

    System.out.println("DFS after the removal of 4");
    uniqueVerticesALGraph.performDFS();

    System.out.println("BFS after the removal of 4");
    uniqueVerticesALGraph.performBFS();

  }
}
