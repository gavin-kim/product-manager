package com.kwan.a4.annotation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Node class for Tree
 */
public abstract class Node {

    private String key = "";
    private String path = "";
    private Node parent = this;
    private Map<String, Node> childrenMap = new HashMap<>();

    public Node() {
    }

    public Node(Node parent, String key) {
        this.parent = parent;
        this.key = key;
        this.path = parent.getPath() + key;
    }

    public String getKey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void addChild(Node node) {
        childrenMap.put(node.getKey(), node);
    }

    public Node getChild(String key) {
        return childrenMap.get(key);
    }

    public Collection<Node> getChildren() {
        return childrenMap.values();
    }

    public boolean hasChild(String key) {
        return childrenMap.containsKey(key);
    }

    public void removeChild(String key) {
        childrenMap.remove(key);
    }

    public boolean isRoot() {
        return parent == this;
    }
}
