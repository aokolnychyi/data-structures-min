package com.aokolnychyi.ds.graph;

import java.util.ArrayList;
import java.util.List;

import com.aokolnychyi.ds.graph.NonUniqueVerticesALGraph.Node;

public class NonUniqueVerticesALGraphExamples {

  public static void main(String[] args) {
    Node<Integer> node0 = Node.newNode(0);
    Node<Integer> node1 = Node.newNode(1);
    Node<Integer> node2 = Node.newNode(2);
    Node<Integer> node3 = Node.newNode(3);
    Node<Integer> node4 = Node.newNode(4);
    Node<Integer> node5 = Node.newNode(5);

    node0.addChild(node1);
    node0.addChild(node4);
    node0.addChild(node5);

    node1.addChild(node4);
    node1.addChild(node3);

    node2.addChild(node1);

    node3.addChild(node2);
    node3.addChild(node4);

    final List<Node<Integer>> nodes = new ArrayList<>();
    nodes.add(node0);
    nodes.add(node1);
    nodes.add(node2);
    nodes.add(node3);
    nodes.add(node4);
    nodes.add(node5);

    final NonUniqueVerticesALGraph<Integer> graph = new NonUniqueVerticesALGraph<>(nodes);

    System.out.println("DFS");
    graph.performDFS();

    System.out.println("BFS");
    graph.performBFS();

    graph.removeNode(node4);
    System.out.println("DFS after the removal of 4");
    graph.performDFS();

    System.out.println("BFS after the removal of 4");
    graph.performBFS();
  }

}
