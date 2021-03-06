package com.aokolnychyi.ds.graph;

import com.aokolnychyi.ds.graph.ALGraph.Vertex;

public class ALGraphExamples {

  public static void main(String[] args) {
    final Vertex<Integer> vertex0 = Vertex.newVertex(0);
    final Vertex<Integer> vertex1 = Vertex.newVertex(1);
    final Vertex<Integer> vertex2 = Vertex.newVertex(2);
    final Vertex<Integer> vertex3 = Vertex.newVertex(3);
    final Vertex<Integer> vertex4 = Vertex.newVertex(4);
    final Vertex<Integer> vertex5 = Vertex.newVertex(5);

    final ALGraph<Integer> graph = new ALGraph<>(true);
    graph.addVertex(vertex0);
    graph.addVertex(vertex1);
    graph.addVertex(vertex2);
    graph.addVertex(vertex3);
    graph.addVertex(vertex4);
    graph.addVertex(vertex5);

    graph.addEdge(vertex0, vertex1);
    graph.addEdge(vertex0, vertex4);
    graph.addEdge(vertex0, vertex5);

    graph.addEdge(vertex1, vertex3);
    graph.addEdge(vertex1, vertex4);

    graph.addEdge(vertex2, vertex1);

    graph.addEdge(vertex3, vertex2);
    graph.addEdge(vertex3, vertex4);

    System.out.println("Contains Path");

    System.out.println(graph.containsPath(vertex0, vertex1));
    System.out.println(graph.containsPath(vertex1, vertex3));
    System.out.println(graph.containsPath(vertex1, vertex4));
    System.out.println(graph.containsPath(vertex4, vertex0));

    System.out.println("DFS");
    graph.performDFS();

    System.out.println("BFS");
    graph.performBFS();

    final ALGraph<String> disconnectedGraph = new ALGraph<>(true);
    final Vertex<String> vertexA = Vertex.newVertex("a");
    final Vertex<String> vertexB = Vertex.newVertex("b");
    final Vertex<String> vertexC = Vertex.newVertex("c");
    final Vertex<String> vertexD = Vertex.newVertex("d");
    disconnectedGraph.addVertex(vertexA);
    disconnectedGraph.addVertex(vertexB);
    disconnectedGraph.addVertex(vertexC);
    disconnectedGraph.addVertex(vertexD);

    disconnectedGraph.addEdge(vertexA, vertexB);
    disconnectedGraph.addEdge(vertexC, vertexD);

    System.out.println("Disconnected DFS");
    disconnectedGraph.performDFS();

    System.out.println("Disconnected BFS");
    disconnectedGraph.performBFS();
  }
}
