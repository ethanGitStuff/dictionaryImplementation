import java.util.Iterator;

public class BinarySearchTreeDict<K extends Comparable<K>,V> implements ProjOneDictionary<K,V> {
    // Fields //
    private class Node{
        K key;
        V value;
        Node left;
        Node right;
        Node parent;

        Node(K key, V value) {
            super();
            this.key = key;
            this.value = value;
        }
    }

    private class BSTIterator implements Iterator<K>{
        Node cursor;

        private BSTIterator() {
            if (root == null) { return; }
            cursor = root;
            while (cursor.left != null) { cursor = cursor.left; }
        }

        @Override
        public boolean hasNext() {
            return cursor != null;
        }

        @Override
        public K next() {
            if (cursor == null) { return null; }
            K toReturn = cursor.key;

            if (cursor.right != null) {
                cursor = cursor.right;
                while (cursor.left != null) { cursor = cursor.left; }
            }
            else {
                while (cursor.parent != null && cursor == cursor.parent.right) {
                    cursor = cursor.parent;
                }
                cursor = cursor.parent;
            }

            return toReturn;
        }
    }

    Node root;
    int size;

    // Methods //
    private Node getNextNode(Node current) {
        if (current == null) { throw new NullPointerException(); }

        Node temp = current;
        while (temp.left != null) {
            temp = temp.left;
        }

        if (temp.parent != null) {
            if (temp.parent.left == temp) {
                temp.parent.left = temp.right;
            }
            else {
                temp.parent.right = temp.right;
            }
        }
        if (temp.right != null) {
            temp.right.parent = temp.parent;
        }

        return temp;
    }

    private void removeNode(Node thisNode) {
        Node parentNode = thisNode.parent;
        if (thisNode.left == null && thisNode.right == null && parentNode != null) {
            if (thisNode == parentNode.left) {
                parentNode.left = null;
            }
            else {
                parentNode.right = null;
            }
        }
        else if (thisNode.left == null && parentNode != null) {
            if (thisNode == parentNode.left) {
                parentNode.left = thisNode.right;
            }
            else {
                parentNode.right = thisNode.right;
            }
        }
        else if (thisNode.right == null && parentNode != null) {
            if (thisNode == parentNode.left) {
                parentNode.left = thisNode.left;
            }
            else {
                parentNode.right = thisNode.left;
            }
        }
        else {
            Node temp = getNextNode(thisNode);
            thisNode.key = temp.key;
            thisNode.value = temp.value;
        }
    }


    @Override
    public boolean insert(K key, V value) throws NullKeyException, NullValueException {
        if (key == null) { throw new NullKeyException(); }
        if (value == null) { throw new NullValueException(); }
        if (root == null) {
            root = new Node(key, value);
            size = 1;
            return false;
        }

        Node current = root;
        Node previous = null;
        int comparison = 0;

        while (current != null) {
            previous = current;
            comparison = key.compareTo(current.key);
            if (comparison < 0) {
                current = current.left;
            }
            else if (comparison > 0) {
                current = current.right;
            }
            else {
                current.value = value;
                return true;
            }
        }
        current = new Node(key, value);
        current.parent = previous;
        if (comparison > 0) {
            previous.right = current;
        }
        else {
            previous.left = current;
        }
        size++;

        return false;
    }

    @Override
    public V find(K key) throws NullKeyException {
        if (key == null) { throw new NullKeyException(); }
        if (root == null) { return null; }
        Node current = root;

        while (current != null) {
            if (key.compareTo(current.key) < 0) {
                current = current.left;
            }
            else if (key.compareTo(current.key) > 0) {
                current = current.right;
            }
            else if (key.compareTo(current.key) == 0) {
                return current.value;
            }
        }

        return null;
    }

    @Override
    public boolean delete(K key) throws NullKeyException {
        if (key == null) { throw new NullKeyException(); }
        if (root == null) {
            return false;
        }

        Node current = root;
        int comparison = 0;
        while (current != null) {
            comparison = key.compareTo(current.key);
            if (comparison < 0) {
                current = current.left;
            }
            else if (comparison > 0) {
                current = current.right;
            }
            else {
                removeNode(current);
                size--;
                return true;
            }
        }

        return false;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTIterator();
    }
}
