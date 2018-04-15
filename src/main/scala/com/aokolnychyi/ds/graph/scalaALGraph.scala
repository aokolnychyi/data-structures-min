package com.aokolnychyi.ds.graph

case class ScalaALGraph[Vertex](isDirected: Boolean, adjacencyMap: Map[Vertex, Set[Vertex]]) {

  // O(1) time
  def addVertex(vertex: Vertex): ScalaALGraph[Vertex] = {
    copy(adjacencyMap = adjacencyMap + (vertex -> Set.empty))
  }

  def addVertices(vertices: Vertex*): ScalaALGraph[Vertex] = {
    val newEntries = vertices.map(vertex => vertex -> Set.empty[Vertex])
    copy(adjacencyMap = adjacencyMap ++ newEntries)
  }

  // O(1) time
  def addEdge(firstVertex: Vertex, secondVertex: Vertex): ScalaALGraph[Vertex] = {
    val updatedAdjacencyMap = connect(firstVertex, secondVertex, adjacencyMap)
    if (isDirected) {
      copy(adjacencyMap = updatedAdjacencyMap)
    } else {
      // for non-directed graphs, we also add an edge from the second node towards the first one
      copy(adjacencyMap = connect(secondVertex, firstVertex, updatedAdjacencyMap))
    }
  }

  // O(1) time
  private def connect(
      firstVertex: Vertex,
      secondVertex: Vertex,
      adjacencyMap: Map[Vertex, Set[Vertex]]): Map[Vertex, Set[Vertex]] = {

    val firstVertexNeighbors = adjacencyMap.getOrElse(firstVertex, Set.empty)
    adjacencyMap + (firstVertex -> (firstVertexNeighbors + secondVertex))
  }

  def findPath(srcVertex: Vertex, dstVertex: Vertex): List[Vertex] = {
    // search for a path from dst -> src since we prepend (i.e., not append) each new node
    findPath(dstVertex, srcVertex, List.empty, Set.empty)
  }

  // instead of having a list and a set, one could use mutable.LinkedHashSet (but it is mutable)
  // another approach is to rely on path.contains() but the time complexity will be worse
  // uses depth-first approach, should be close to O(V + E)
  private def findPath(
      currentVertex: Vertex,
      destinationVertex: Vertex,
      path: List[Vertex],
      seenVertices: Set[Vertex]): List[Vertex] = {

    if (currentVertex == destinationVertex) {
      destinationVertex :: path
    } else if (seenVertices.contains(currentVertex)) {
      List.empty
    } else {
      val neighbors = adjacencyMap(currentVertex)
      // convert to a stream since we do want to compute all possible paths
      val paths = neighbors.toStream.map { neighbor =>
        findPath(neighbor, destinationVertex, currentVertex :: path, seenVertices + currentVertex)
      }
      // find any non-empty path
      paths.find(_.nonEmpty).getOrElse(List.empty)
    }
  }

  // O(V + E) time
  def doDepthFirstTraversal(operation: Vertex => Unit): Unit = {
    traverseGraph(operation, doDepthFirstTraversal)
  }

  private def doDepthFirstTraversal(
      vertex: Vertex,
      operation: Vertex => Unit,
      seenVertices: Set[Vertex]): Set[Vertex] = {

    operation(vertex)

    val seenByNowVertices = seenVertices + vertex
    val neighbors = adjacencyMap(vertex)
    val newNeighbors = neighbors -- seenByNowVertices
    newNeighbors.foldLeft(seenByNowVertices) { case (currentlySeenVertices, neighbor) =>
      if (currentlySeenVertices.contains(neighbor)) {
        currentlySeenVertices
      } else {
        doDepthFirstTraversal(neighbor, operation, currentlySeenVertices + neighbor)
      }
    }
  }

  // seems like O(E + V) but hard to tell
  def doBreadthFirstTraversal(operation: Vertex => Unit): Unit = {
    traverseGraph(operation, doBreadthFirstTraversal)
  }

  private def doBreadthFirstTraversal(
      vertex: Vertex,
      operation: Vertex => Unit,
      seenVertices: Set[Vertex]): Set[Vertex] = {

    // returns seen vertices
    def inner(currentVertices: Set[Vertex], currentlySeenVertices: Set[Vertex]): Set[Vertex] = {

      currentVertices.foreach(operation)

      val newNeighbors = currentVertices.flatMap(adjacencyMap(_)) -- currentlySeenVertices
      if (newNeighbors.isEmpty) {
        currentlySeenVertices ++ currentVertices
      } else {
        currentlySeenVertices ++ inner(newNeighbors, currentlySeenVertices ++ currentVertices)
      }
    }

    inner(Set(vertex), seenVertices)
  }

  // handles disconnected graphs
  private def traverseGraph(
      elementOp: Vertex => Unit,
      traversalFunc: (Vertex, Vertex => Unit, Set[Vertex]) => Set[Vertex]): Set[Vertex] = {

    adjacencyMap.foldLeft(Set.empty[Vertex]) { case (seenVertices, (vertex, _)) =>
      if (seenVertices.contains(vertex)) {
        seenVertices
      } else {
        seenVertices ++ traversalFunc(vertex, elementOp, seenVertices)
      }
    }
  }

}

object Test extends App {

  val vertices = (1 to 8).toList
  val entries = vertices.map(vertex => vertex -> Set.empty[Int])
  var graph = ScalaALGraph[Int](isDirected = false, entries.toMap)

  graph = graph.addEdge(1, 2)
  graph = graph.addEdge(1, 3)
  graph = graph.addEdge(1, 4)

  graph = graph.addEdge(2, 8)
  graph = graph.addEdge(4, 5)

  graph = graph.addEdge(3, 5)

  graph = graph.addEdge(6, 7)

  println("DF Traversal")
  graph.doDepthFirstTraversal(println(_))
  println("BF Traversal")
  graph.doBreadthFirstTraversal(println(_))

  println(graph.findPath(4, 2))
  println(graph.findPath(4, 8))
  println(graph.findPath(3, 8))
  println(graph.findPath(1, 7))
  println(graph.findPath(1, 1))
}
