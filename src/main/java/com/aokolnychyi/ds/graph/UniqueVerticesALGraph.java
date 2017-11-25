package com.aokolnychyi.ds.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

/**
 * A directed graph via Adjacent Lists.
 * <p>
 * - Supports only non-unique vertices
 * - Can be used as an undirected graph
 * - O(V + E) space complexity to store the graph
 * - O(1) to add a vertex and edge
 * - O(E) to remove a vertex
 * - O(E) to remove an edge
 * - DFS and BFS take O(numberOfVertices * numberOfEdges) time
 * - The main benefit - does not require working with Nodes
 * - The main drawback - time for BFS and DFS (no efficient way to find adjacent vertices)
 */
public class UniqueVerticesALGraph<E> {

  private static class Edge<E> {
    private E firstVertex;
    private E secondVertex;

    private Edge(E firstVertex, E secondVertex) {
      this.firstVertex = firstVertex;
      this.secondVertex = secondVertex;
    }

    @Override
    public String toString() {
      return "Edge{firstVertex=" + firstVertex +
          ", secondVertex=" + secondVertex +
          "}";
    }
  }

  private List<Edge<E>> edges = new ArrayList<>();
  private Set<E> vertices = new HashSet<>();

  public void addEdge(E firstVertex, E secondVertex) {
    connect(firstVertex, secondVertex);
    vertices.add(firstVertex);
    vertices.add(secondVertex);
  }

  private void connect(E sourceVertex, E destinationVertex) {
    final Edge<E> newEdge = new Edge<>(sourceVertex, destinationVertex);
    edges.add(newEdge);
  }

  public void performDFS() {
    // if (!vertices.isEmpty()) {
    //      final E startVertex = vertices.iterator().next();
    // }
    final Optional<E> startVertex = vertices.stream().findFirst();
    if (startVertex.isPresent()) {
      final Set<E> visitedVertices = new HashSet<>();
      performDFS(startVertex.get(), visitedVertices);
    }
  }

  // O (numberOfVertices * numberOfEdges + numberOfEdges) = O (numberOfVertices * numberOfEdges)
  private void performDFS(E vertex, Set<E> visitedVertices) {
    // O(numberOfEdges)
    final List<E> incidentVertices = findIncidentVertices(vertex);
    System.out.println(vertex);
    visitedVertices.add(vertex);

    // Some extra memory for recursion
    incidentVertices.stream()
        .filter(incidentVertex -> !visitedVertices.contains(incidentVertex))
        .forEach(nonVisitedIncidentVertex -> performDFS(nonVisitedIncidentVertex, visitedVertices));
  }

  private List<E> findIncidentVertices(E vertex) {
    final List<E> incidentVertices = new ArrayList<>();
    edges.forEach(edge -> {
      if (vertex.equals(edge.firstVertex)) {
        incidentVertices.add(edge.secondVertex);
      }
    });
    return incidentVertices;
  }

  // O (numberOfVertices * numberOfEdges + numberOfEdges) = O (numberOfVertices * numberOfEdges)
  public void performBFS() {
    final Optional<E> startVertex = vertices.stream().findFirst();
    if (startVertex.isPresent()) {
      final Set<E> visitedVertices = new HashSet<>();
      final Queue<E> queue = new ArrayDeque<>();
      queue.add(startVertex.get());

      while (!queue.isEmpty()) {
        final E currentVertex = queue.remove();
        System.out.println(currentVertex);
        visitedVertices.add(currentVertex);

        final List<E> incidentVertices = findIncidentVertices(currentVertex);
        incidentVertices.stream()
            .filter(vertex -> !visitedVertices.contains(vertex))
            .forEach(queue::add);

      }
    }
  }

  public void removeVertex(E vertexToDelete) {
    vertices.remove(vertexToDelete);
    final Iterator<Edge<E>> edgeIterator = edges.iterator();
    while (edgeIterator.hasNext()) {
      final Edge<E> edge = edgeIterator.next();
      if (edge.firstVertex.equals(vertexToDelete) || edge.secondVertex.equals(vertexToDelete)) {
        edgeIterator.remove();
      }
    }
  }

  public void removeEdge(E sourceVertex, E destinationVertex) {
    final Iterator<Edge<E>> edgeIterator = edges.iterator();
    while (edgeIterator.hasNext()) {
      final Edge<E> edge = edgeIterator.next();
      if (edge.firstVertex.equals(sourceVertex) || edge.secondVertex.equals(destinationVertex)) {
        edgeIterator.remove();
        break;
      }
    }
  }
}
