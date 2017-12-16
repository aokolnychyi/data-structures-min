package com.aokolnychyi.ds.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A directed graph via Adjacent Lists (non-efficient BFS and DFS).
 *
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

  private List<Edge> edges = new ArrayList<>();
  private Set<E> vertices = new HashSet<>();

  public void addEdge(E firstVertex, E secondVertex) {
    final Edge newEdge = new Edge(firstVertex, secondVertex);
    edges.add(newEdge);
    vertices.add(firstVertex);
    vertices.add(secondVertex);
  }

  public void performDFS() {
    final Optional<E> startVertexOp = vertices.stream().findFirst();
    startVertexOp.ifPresent(vertex -> {
      final Set<E> visitedVertices = new HashSet<>();
      performDFS(vertex, visitedVertices);
    });
  }

  // O (numberOfVertices * numberOfEdges + numberOfEdges) = O (numberOfVertices * numberOfEdges)
  // #edges for v1 + incident edges for v1 + #edges for v2 + incident edges for v2 + ...
  // which is equivalent to O(V * E + E) = O(V * E)
  private void performDFS(E vertex, Set<E> visitedVertices) {
    final Stream<E> incidentVertices = findIncidentVertices(vertex);
    System.out.println(vertex);
    visitedVertices.add(vertex);

    incidentVertices
        .filter(incidentVertex -> !visitedVertices.contains(incidentVertex))
        .forEach(nonVisitedIncidentVertex -> performDFS(nonVisitedIncidentVertex, visitedVertices));
  }

  // O(numberOfEdges)
  private Stream<E> findIncidentVertices(E vertex) {
    return edges.stream()
        .filter(edge -> edge.firstVertex.equals(vertex))
        .map(edge -> edge.secondVertex);
  }

  // O (numberOfVertices * numberOfEdges + numberOfEdges) = O (numberOfVertices * numberOfEdges)
  public void performBFS() {
    final Optional<E> startVertexOp = vertices.stream().findFirst();
    startVertexOp.ifPresent(startVertex -> {
      final Set<E> visitedVertices = new HashSet<>();
      final Queue<E> queue = new ArrayDeque<>();
      queue.add(startVertex);

      while (!queue.isEmpty()) {
        final E currentVertex = queue.remove();
        // you can have a case when you add an element to the queue and it is already
        // there but not in the visited set
        // as a result, there will be duplicated results without this check
        if (!visitedVertices.contains(currentVertex)) {
          System.out.println(currentVertex);
          visitedVertices.add(currentVertex);
        }

        final Stream<E> incidentVertices = findIncidentVertices(currentVertex);
        incidentVertices
            .filter(vertex -> !visitedVertices.contains(vertex))
            .forEach(queue::add);
      }
    });
  }

  public void removeVertex(E vertexToDelete) {
    vertices.remove(vertexToDelete);
    edges.removeIf(edge ->
        edge.firstVertex.equals(vertexToDelete) || edge.secondVertex.equals(vertexToDelete));
  }

  public void removeEdge(E sourceVertex, E destinationVertex) {
    edges.removeIf(edge ->
        edge.firstVertex.equals(sourceVertex) || edge.secondVertex.equals(destinationVertex));
  }

  private class Edge {
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
}
