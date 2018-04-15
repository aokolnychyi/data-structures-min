package com.aokolnychyi.ds.graph.mutable

import scala.collection.mutable

class Vertex[T](val element: T)

class ScalaGraph[T](vertices: Vertex[T]*) {

  private val adjacencyMap: mutable.HashMap[Vertex[T], mutable.HashSet[Vertex[T]]] =
    mutable.HashMap.empty

  for (vertex <- vertices) {
    adjacencyMap(vertex) = mutable.HashSet.empty
  }

  def addEdge(firstVertex: Vertex[T], secondVertex: Vertex[T]): Unit = {
    adjacencyMap(firstVertex) += secondVertex
    adjacencyMap(secondVertex) += firstVertex
  }

  def printBFS(): Unit = {
    val visitedVertices = mutable.HashSet.empty[Vertex[T]]
    var unvisitedVertex = getUnvisitedVertex(visitedVertices)

    while (unvisitedVertex.isDefined) {
      var currentVertices = mutable.Queue.empty[Vertex[T]]
      currentVertices.enqueue(unvisitedVertex.get)

      while (currentVertices.nonEmpty) {
        val previousVertices = currentVertices
        currentVertices = mutable.Queue.empty[Vertex[T]]

        previousVertices.foreach { vertex =>
          if (!visitedVertices.contains(vertex)) {
            println(vertex.element)
            visitedVertices += vertex
          }
          val unvisitedVertices = adjacencyMap(vertex).diff(visitedVertices)
          unvisitedVertices.foreach { unvisitedVertex =>
            currentVertices.enqueue(unvisitedVertex)
          }
        }

      }
      unvisitedVertex = getUnvisitedVertex(visitedVertices)
    }
  }

  private def getUnvisitedVertex(visitedVertices: mutable.HashSet[Vertex[T]]): Option[Vertex[T]] = {
    adjacencyMap.keys.find(key => !visitedVertices.contains(key))
  }
}

object Examples {

  def main(args: Array[String]): Unit = {
    val vertexA = new Vertex('A')
    val vertexB = new Vertex('B')
    val vertexC = new Vertex('C')
    val vertexD = new Vertex('D')
    val vertexE = new Vertex('E')
    val vertexF = new Vertex('F')

    val vertexW = new Vertex('W')
    val vertexZ = new Vertex('Z')

    val graph = new ScalaGraph(vertexA, vertexB, vertexC, vertexD, vertexE,
      vertexF, vertexW, vertexZ)

    graph.addEdge(vertexA, vertexB)
    graph.addEdge(vertexA, vertexC)
    graph.addEdge(vertexB, vertexD)
    graph.addEdge(vertexC, vertexE)
    graph.addEdge(vertexD, vertexF)
    graph.addEdge(vertexE, vertexF)

    graph.addEdge(vertexW, vertexZ)

    graph.printBFS()
  }
}
