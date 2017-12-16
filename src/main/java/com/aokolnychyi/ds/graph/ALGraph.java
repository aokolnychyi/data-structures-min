package com.aokolnychyi.ds.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;

import static com.aokolnychyi.ds.graph.ALGraph.Vertex.State.UNVISITED;
import static com.aokolnychyi.ds.graph.ALGraph.Vertex.State.VISITED;

/**
 * Both directed and undirected graph.
 *
 * - Supports non-unique vertices
 * - Supports disconnected graphs
 * - O(V + E) space complexity to store the graph.
 * Actually, it will be O(V + 2E) for undirected graphs since edges appear twice
 * - O(1) to add a vertex or an edge
 * - O(E) to remove a vertex
 * - O(numberOfIncidenceVertices) to remove an edge
 * - BFS and DFS take O(E + V) time
 * - The main benefit - efficient
 * - The main drawback - non-weighted
 */
public class ALGraph<E> {

  public static class Vertex<E> {

    public enum State {UNVISITED, VISITED}

    private E element;
    private State state;

    public static <E> Vertex<E> newVertex(final E element) {
      final Vertex<E> newVertex = new Vertex<>();
      newVertex.element = element;
      newVertex.state = UNVISITED;
      return newVertex;
    }
  }

  private Map<Vertex<E>, List<Vertex<E>>> adjacencyMap = new HashMap<>();
  private final boolean isDirected;

  public ALGraph(boolean isDirected) {
    this.isDirected = isDirected;
  }

  public void addVertex(Vertex<E> vertex) {
    adjacencyMap.put(vertex, new ArrayList<>());
  }

  public void removeVertex(Vertex<E> vertex) {
    // Alternatively, can get the list of incident vertices from this node
    // and then query the map for each vertex and delete the corresponding vertex.
    // That should be a bit more efficient since we do not have to iterate through all edges
    adjacencyMap.remove(vertex);
    final Collection<List<Vertex<E>>> incidentVertexLists = adjacencyMap.values();
    incidentVertexLists.forEach(list -> list.remove(vertex));
  }

  public void addEdge(Vertex<E> firstVertex, Vertex<E> secondVertex) {
    connect(firstVertex, secondVertex);
    if (!isDirected) connect(secondVertex, firstVertex);
  }

  private void connect(Vertex<E> firstVertex, Vertex<E> secondVertex) {
    adjacencyMap.putIfAbsent(firstVertex, new ArrayList<>());
    final List<Vertex<E>> firstVertexNeighbors = adjacencyMap.get(firstVertex);
    firstVertexNeighbors.add(secondVertex);
  }

  public void removeEdge(Vertex<E> firstVertex, Vertex<E> secondVertex) {
    disconnect(firstVertex, secondVertex);
    if (!isDirected) disconnect(secondVertex, firstVertex);
  }

  private void disconnect(Vertex<E> firstVertex, Vertex<E> secondVertex) {
    final List<Vertex<E>> incidentVertices = adjacencyMap.get(firstVertex);
    incidentVertices.remove(secondVertex);
  }

  public void performDFS() {
    final Set<Vertex<E>> vertices = adjacencyMap.keySet();
    Optional<Vertex<E>> unvisitedVertex = getUnvisitedVertex(vertices);

    while (unvisitedVertex.isPresent()) {
      performDFS(unvisitedVertex.get());
      unvisitedVertex = getUnvisitedVertex(vertices);
    }

    vertices.forEach(vertex -> vertex.state = UNVISITED);
  }

  private void performDFS(Vertex<E> vertex) {
    System.out.println(vertex.element);
    vertex.state = VISITED;

    final List<Vertex<E>> incidentVertices = adjacencyMap.get(vertex);
    incidentVertices.stream()
        .filter(incidentVertex -> incidentVertex.state == UNVISITED)
        .forEach(this::performDFS);
  }

  private Optional<Vertex<E>> getUnvisitedVertex(Set<Vertex<E>> vertices) {
    return vertices.stream()
        .filter(vertex -> vertex.state == UNVISITED)
        .findAny();
  }

  public void performBFS() {
    final Set<Vertex<E>> vertices = adjacencyMap.keySet();
    Optional<Vertex<E>> unvisitedVertex = getUnvisitedVertex(vertices);

    while (unvisitedVertex.isPresent()) {
      final Queue<Vertex<E>> queue = new ArrayDeque<>();
      queue.add(unvisitedVertex.get());

      while (!queue.isEmpty()) {
        final Vertex<E> currentVertex = queue.remove();
        if (currentVertex.state != VISITED) {
          System.out.println(currentVertex.element);
          currentVertex.state = VISITED;
        }
        final List<Vertex<E>> incidentVertices = adjacencyMap.get(currentVertex);
        incidentVertices.stream()
            .filter(incidentVertex -> incidentVertex.state == UNVISITED)
            .forEach(queue::add);
      }

      unvisitedVertex = getUnvisitedVertex(vertices);
    }

    vertices.forEach(vertex -> vertex.state = UNVISITED);
  }
}
