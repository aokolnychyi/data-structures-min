package com.aokolnychyi.ds.examples;

import com.aokolnychyi.ds.graph.EfficientWeightedALGraph;
import com.aokolnychyi.ds.graph.EfficientWeightedALGraph.Vertex;

public class EfficientWeightedALGraphExamples {

  public static void main(String[] args) {
    final EfficientWeightedALGraph<Integer> graph = new EfficientWeightedALGraph<>(true);
    final Vertex<Integer> vertex0 = Vertex.newVertex(0);
    final Vertex<Integer> vertex1 = Vertex.newVertex(1);
    final Vertex<Integer> vertex2 = Vertex.newVertex(2);
    final Vertex<Integer> vertex3 = Vertex.newVertex(3);
    final Vertex<Integer> vertex4 = Vertex.newVertex(4);
    final Vertex<Integer> vertex5 = Vertex.newVertex(5);

    graph.addVertex(vertex0);
    graph.addVertex(vertex1);
    graph.addVertex(vertex2);
    graph.addVertex(vertex3);
    graph.addVertex(vertex4);
    graph.addVertex(vertex5);

    graph.addEdge(vertex0, vertex1, 1);
    graph.addEdge(vertex0, vertex4, 1);
    graph.addEdge(vertex0, vertex5, 1);

    graph.addEdge(vertex1, vertex3, 1);
    graph.addEdge(vertex1, vertex4, 1);

    graph.addEdge(vertex2, vertex1, 1);

    graph.addEdge(vertex3, vertex2, 1);
    graph.addEdge(vertex3, vertex4, 1);

    System.out.println("DFS");
    graph.performDFS();

    System.out.println("BFS");
    graph.performBFS();
  }
}
