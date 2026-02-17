import java.util.Iterator;
import java.lang.reflect.Array;

public class HashMapDict<K,V> implements ProjOneDictionary<K,V> {
    // Fields //
    private class Node{
        K key;
        V value;
        Node(K newkey, V newvalue){
            key = newkey;
            value = newvalue;
        }
    }

    private class HashMapIterator implements Iterator<K>{
        int cursor;
        int lastIndex;

        private HashMapIterator() {
            if (currentArray == null) { return; }
            cursor = 0;
            lastIndex = arraySize - 1;
            while (currentArray[lastIndex] == null || currentArray[lastIndex].key == null) {
                lastIndex--;
            }
        }

        @Override
        public boolean hasNext() {
            if (numElements == 0) { return false; }
            return cursor <= lastIndex;
        }

        @Override
        public K next() {
            K toReturn;
            while (currentArray[cursor] == null || currentArray[cursor].key == null) {
                cursor++;
            }
            toReturn = currentArray[cursor].key;
            cursor++;
            return toReturn;
        }
    }

    private int numElements = 0;
    private int arraySize = 20;
    private Node[] currentArray;

    // Methods //
    private Node[] getArray(int size){
        Node[] array = (Node[]) Array.newInstance(Node.class,size);
        return array;
    }

    private void resizeArray() {
        Node[] newArray = getArray(arraySize * 2);
        int hashIndex;
        for (int i = 0; i < arraySize; ++i) {
            if (currentArray[i] != null && currentArray[i].key != null) {

                hashIndex = Math.abs(currentArray[i].key.hashCode() % (arraySize*2));
                while (newArray[hashIndex] != null && newArray[hashIndex].key != null) {
                    hashIndex++;
                    if (hashIndex == (arraySize*2)) {
                        hashIndex = 0;
                    }
                }

                newArray[hashIndex] = new Node(currentArray[i].key, currentArray[i].value);
            }
        }
        currentArray = newArray;
        arraySize *= 2;
    }

    @Override
    public boolean insert(K key, V value) throws NullKeyException, NullValueException {
        if (key == null) { throw new NullKeyException(); }
        if (value == null) { throw new NullValueException(); }
        if (currentArray == null) { currentArray = getArray(arraySize); }

        if ((double)numElements/arraySize >= 0.5) {
            resizeArray();
        }

        int hashIndex = Math.abs(key.hashCode() % arraySize);

        while (currentArray[hashIndex] != null && currentArray[hashIndex].key != null) {
            if (currentArray[hashIndex].key.equals(key)) {
                currentArray[hashIndex].value = value;
                return true;
            }

            hashIndex++;
            if (hashIndex == arraySize) {
                hashIndex = 0;
            }
        }

        currentArray[hashIndex] = new Node(key, value);
        numElements++;
        return false;
    }

    @Override
    public V find(K key) throws NullKeyException {
        if (key == null) { throw new NullKeyException(); }
        if (currentArray == null) { return null; }
        int hashIndex = Math.abs(key.hashCode() % arraySize);

        while (currentArray[hashIndex] != null) {
            if (currentArray[hashIndex].key != null && currentArray[hashIndex].key.equals(key)) {
                return currentArray[hashIndex].value;
            }
            hashIndex++;
            if (hashIndex == arraySize) {
                hashIndex = 0;
            }
        }

        return null;
    }

    @Override
    public boolean delete(K key) throws NullKeyException {
        if (key == null) { throw new NullKeyException(); }
        if (currentArray == null) { return false; }

        int hashIndex = Math.abs(key.hashCode() % arraySize);
        while (currentArray[hashIndex] != null) {
            if (currentArray[hashIndex].key != null && currentArray[hashIndex].key.equals(key)) {
                currentArray[hashIndex] = new Node(null, null);
                numElements--;
                return true;
            }
            hashIndex++;
            if (hashIndex == arraySize) {
                hashIndex = 0;
            }
        }

        return false;
    }

    @Override
    public int getSize() {
        return numElements;
    }

    @Override
    public Iterator<K> iterator() {
        return new HashMapIterator();
    }
}
