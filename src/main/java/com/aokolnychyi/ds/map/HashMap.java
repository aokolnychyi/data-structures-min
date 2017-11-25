package com.aokolnychyi.ds.map;

public class HashMap<K, V> {

  private TreeNode<K, V>[] buckets;

  public HashMap(int numberOfBuckets) {
    this.buckets = (TreeNode<K, V>[]) new TreeNode[numberOfBuckets];
  }

  public void put(K key, V value) {
    int bucketIndex = getBucketIndex(key);
    buckets[bucketIndex] = insert(buckets[bucketIndex], key, value);
  }

  private TreeNode<K, V> insert(TreeNode<K, V> node, K key, V value) {
    if (node == null) return new TreeNode<>(key.hashCode(), key, value);

    if (node.hash == key.hashCode() && node.key.equals(key)) {
      node.value = value;
    } else if (key.hashCode() <= node.hash) {
      node.leftChild = insert(node.leftChild, key, value);
    } else {
      node.rightChild = insert(node.rightChild, key, value);
    }
    return node;
  }

  public V get(K key) {
    int bucketIndex = getBucketIndex(key);
    return get(buckets[bucketIndex], key);
  }

  private V get(TreeNode<K, V> node, K key) {
    if (node == null) return null;
    if (node.hash == key.hashCode() && node.key.equals(key)) {
      return node.value;
    } else if (key.hashCode() <= node.hash) {
      return get(node.leftChild, key);
    } else {
      return get(node.rightChild, key);
    }
  }

  private int getBucketIndex(K key) {
    return key.hashCode() % buckets.length;
  }

  private static class TreeNode<K, V> {
    private int hash;
    private K key;
    private V value;
    private TreeNode<K, V> leftChild;
    private TreeNode<K, V> rightChild;

    public TreeNode(int hash, K key, V value) {
      this.hash = hash;
      this.key = key;
      this.value = value;
    }
  }

}
