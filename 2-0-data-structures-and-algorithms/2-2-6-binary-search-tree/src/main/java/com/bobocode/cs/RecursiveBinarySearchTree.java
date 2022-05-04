package com.bobocode.cs;

import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * {@link RecursiveBinarySearchTree} is an implementation of a {@link BinarySearchTree} that is based on a linked nodes
 * and recursion. A tree node is represented as a nested class {@link Node}. It holds an element (a value) and
 * two references to the left and right child nodes.
 *
 * @param <T> a type of elements that are stored in the tree
 * @author Taras Boychuk
 * @author Maksym Stasiuk
 */
public class RecursiveBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

    private static class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        public Node(T value) {
            this.value = value;
        }

        public static <T> Node<T> of(T value) {
            return new Node<>(value);
        }
    }

    private Node<T> root;
    private int size;

    public static <T extends Comparable<T>> RecursiveBinarySearchTree<T> of(T... elements) {
        RecursiveBinarySearchTree<T> tree = new RecursiveBinarySearchTree<>();
        Arrays.stream(elements).forEach(tree::insert);
        return tree;
    }

    @Override
    public boolean insert(T element) {
        Objects.requireNonNull(element);
        boolean isInserted = insertElement(element);
        if (isInserted) {
            size++;
        }
        return isInserted;
    }

    private boolean insertElement(T element) {
        if (root == null) {
            root = Node.of(element);
            return true;
        }
        return insertIntoSubTree(root, element);
    }

    private boolean insertIntoSubTree(Node<T> current, T element) {
        if (current.value.compareTo(element) < 0) {
            if (current.right == null) {
                current.right = Node.of(element);
                return true;
            } else {
                return insertIntoSubTree(current.right, element);
            }
        }

        if (current.value.compareTo(element) > 0) {
            if (current.left == null) {
                current.left = Node.of(element);
                return true;
            } else {
                return insertIntoSubTree(current.left, element);
            }
        }

        return false;
    }

    @Override
    public boolean contains(T element) {
        Objects.requireNonNull(element);
        return contains(root, element);
    }

    private boolean contains(Node<T> current, T element) {
        if (current == null) {
            return false;
        }
        if (current.value.equals(element)) {
            return true;
        }
        if (current.value.compareTo(element) > 0) {
            return contains(current.left, element);
        }
        if (current.value.compareTo(element) < 0) {
            return contains(current.right, element);
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int depth() {
        return depth(root);
    }

    private int depth(Node<T> current) {
        if (current == null || (current.left == null && current.right == null)) {
            return 0;
        }
        int rightDepth = depth(current.right);
        int leftDepth = depth(current.left);
        if (rightDepth > leftDepth) {
            return rightDepth + 1;
        }
        return leftDepth + 1;
    }

    // iterative implementation of depth method
    public int depthIterative() {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = root;
        int depth = 0;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            Node<T> node = stack.pop();
            if (stack.size() > depth) {
                depth = stack.size();
            }
            current = node.right;
            if (current != null) {
                stack.push(node.right);
            }
        }

        return depth;
    }

    @Override
    public void inOrderTraversal(Consumer<T> consumer) {
        if (root != null) {
            inOrderTraversal(root, consumer);
        }
    }

    private void inOrderTraversal(Node<T> current, Consumer<T> consumer) {
        if (current.left != null) {
            inOrderTraversal(current.left, consumer);
        }
        consumer.accept(current.value);
        if (current.right != null) {
            inOrderTraversal(current.right, consumer);
        }
    }

    // iterative implementation of inOrderTraversal method
    public void inOrderTraversalIterative(Consumer<T> consumer) {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            Node<T> node = stack.pop();
            consumer.accept(node.value);
            current = node.right;
        }
    }
}
