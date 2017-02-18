package com.aokolnychyi.data_structure.examples;

import java.util.ArrayList;
import java.util.List;

import com.aokolnychyi.data_structure.graph.NonUniqueVerticesALGraph;

public class NonUniqueVerticesALGraphExamples {

  public static void main(String[] args) {
    NonUniqueVerticesALGraph.Node<Integer> node0 = NonUniqueVerticesALGraph.Node.newNode(0);
    NonUniqueVerticesALGraph.Node<Integer> node1 = NonUniqueVerticesALGraph.Node.newNode(1);
    NonUniqueVerticesALGraph.Node<Integer> node2 = NonUniqueVerticesALGraph.Node.newNode(2);
    NonUniqueVerticesALGraph.Node<Integer> node3 = NonUniqueVerticesALGraph.Node.newNode(3);
    NonUniqueVerticesALGraph.Node<Integer> node4 = NonUniqueVerticesALGraph.Node.newNode(4);
    NonUniqueVerticesALGraph.Node<Integer> node5 = NonUniqueVerticesALGraph.Node.newNode(5);

    node0.addChild(node1);
    node0.addChild(node4);
    node0.addChild(node5);

    node1.addChild(node4);
    node1.addChild(node3);

    node2.addChild(node1);

    node3.addChild(node2);
    node3.addChild(node4);

    final List<NonUniqueVerticesALGraph.Node<Integer>> nodes = new ArrayList<>();
    nodes.add(node0);
    nodes.add(node1);
    nodes.add(node2);
    nodes.add(node3);
    nodes.add(node4);
    nodes.add(node5);

    final NonUniqueVerticesALGraph<Integer> nonUniqueVerticesALGraph = new NonUniqueVerticesALGraph<>(nodes);

    System.out.println("DFS");
    nonUniqueVerticesALGraph.performDFS();

    System.out.println("BFS");
    nonUniqueVerticesALGraph.performBFS();

    nonUniqueVerticesALGraph.removeNode(node4);
    System.out.println("DFS after the removal of 4");
    nonUniqueVerticesALGraph.performDFS();

    System.out.println("BFS after the removal of 4");
    nonUniqueVerticesALGraph.performBFS();
  }

}
