package com.aokolnychyi.ds.graph;

import java.util.ArrayList;
import java.util.List;

import com.aokolnychyi.ds.graph.AdjacencyMatrixGraph.Vertex;

public class AdjacencyMatrixGraphExamples {

  public static void main(String[] args) {
    final List<Vertex<Integer>> vertices = new ArrayList<>();
    final Vertex<Integer> vertex0 = new Vertex<>(0);
    final Vertex<Integer> vertex1 = new Vertex<>(1);
    final Vertex<Integer> vertex2 = new Vertex<>(2);
    final Vertex<Integer> vertex3 = new Vertex<>(3);
    final Vertex<Integer> vertex4 = new Vertex<>(4);
    final Vertex<Integer> vertex5 = new Vertex<>(5);
    vertices.add(vertex0);
    vertices.add(vertex1);
    vertices.add(vertex2);
    vertices.add(vertex3);
    vertices.add(vertex4);
    vertices.add(vertex5);

    final AdjacencyMatrixGraph<Integer> graph = new AdjacencyMatrixGraph<>(vertices);
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
