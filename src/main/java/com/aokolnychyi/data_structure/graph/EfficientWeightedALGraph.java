package com.aokolnychyi.data_structure.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.aokolnychyi.data_structure.graph.EfficientWeightedALGraph.Vertex.State.UNVISITED;
import static com.aokolnychyi.data_structure.graph.EfficientWeightedALGraph.Vertex.State.VISITED;

/**
 * Both directed and undirected weighted graph.
 * <p>
 * - Supports non-unique vertices
 * - Supports disconnected graphs
 * - O(V + E) space complexity to store the graph
 * - O(1) to add a vertex or an edge
 * - O(E) to remove a vertex
 * - O(numberOfIncidenceVertices) to remove an edge
 * - BFS and DFS take O(E + V) time
 * - The main benefit - efficient
 */
public class EfficientWeightedALGraph<E> {

  public static class Vertex<E> {

    public enum State {UNVISITED, VISITED, VISITING}

    private E element;
    private State state;

    public static <E> Vertex<E> newVertex(final E element) {
      final Vertex<E> newVertex = new Vertex<>();
      newVertex.element = element;
      newVertex.state = UNVISITED;
      return newVertex;
    }
  }

  private static class Edge<E> {
    private final Vertex<E> sourceVertex;
    private final Vertex<E> destinationVertex;
    private final int weight;

    private Edge(Vertex<E> sourceVertex, Vertex<E> destinationVertex, int weight) {
      this.sourceVertex = sourceVertex;
      this.destinationVertex = destinationVertex;
      this.weight = weight;
    }
  }

  private Map<Vertex<E>, List<Edge<E>>> adjacencyMap = new HashMap<>();
  private final boolean isDirected;

  public EfficientWeightedALGraph(boolean isDirected) {
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
    final Collection<List<Edge<E>>> edgeLists = adjacencyMap.values();
    edgeLists.forEach(edgeList -> removeEdgesWithVertex(edgeList, vertex));
  }

  private void removeEdgesWithVertex(List<Edge<E>> edges, Vertex<E> vertexToDelete) {
    final Iterator<Edge<E>> edgesIterator = edges.iterator();
    while (edgesIterator.hasNext()) {
      final Edge edge = edgesIterator.next();
      if (edge.destinationVertex == vertexToDelete) {
        edgesIterator.remove();
      }
    }
  }

  public void addEdge(Vertex<E> firstVertex, Vertex<E> secondVertex, int weight) {
    connect(firstVertex, secondVertex, weight);
    if (!isDirected) connect(secondVertex, firstVertex, weight);
  }

  private void connect(Vertex<E> firstVertex, Vertex<E> secondVertex, int weight) {
    adjacencyMap.putIfAbsent(firstVertex, new ArrayList<>());
    final List<Edge<E>> firstVertexEdges = adjacencyMap.get(firstVertex);
    final Edge<E> newEdge = new Edge<>(firstVertex, secondVertex, weight);
    firstVertexEdges.add(newEdge);
  }

  public void removeEdge(Vertex<E> firstVertex, Vertex<E> secondVertex) {
    disconnect(firstVertex, secondVertex);
    if (!isDirected) disconnect(secondVertex, firstVertex);
  }

  private void disconnect(Vertex<E> sourceVertex, Vertex<E> destinationVertex) {
    final List<Edge<E>> incidentEdges = adjacencyMap.get(sourceVertex);
    final Iterator<Edge<E>> edgesIterator = incidentEdges.iterator();

    while (edgesIterator.hasNext()) {
      final Edge<E> edge = edgesIterator.next();
      if (edge.destinationVertex == destinationVertex) {
        edgesIterator.remove();
        break;
      }
    }
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

    final List<Edge<E>> incidentEdges = adjacencyMap.get(vertex);
    incidentEdges.stream()
        .filter(incidentEdge -> incidentEdge.destinationVertex.state == UNVISITED)
        .forEach(incidentEdge -> performDFS(incidentEdge.destinationVertex));
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
      final java.util.Queue<Vertex<E>> queue = new ArrayDeque<>();
      queue.add(unvisitedVertex.get());

      while (!queue.isEmpty()) {
        final Vertex<E> currentVertex = queue.remove();
        System.out.println(currentVertex.element);
        currentVertex.state = VISITED;
        final List<Edge<E>> incidentEdges = adjacencyMap.get(currentVertex);

        incidentEdges.stream()
            .filter(incidentEdge -> incidentEdge.destinationVertex.state == UNVISITED)
            .forEach(incidentEdge -> queue.add(incidentEdge.destinationVertex));
      }

      unvisitedVertex = getUnvisitedVertex(vertices);
    }

    vertices.forEach(vertex -> vertex.state = UNVISITED);
  }
}
