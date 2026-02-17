import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.*;

public abstract class DictionaryTest {
    public abstract ProjOneDictionary<String,String> newDictionary();

    @Test
    void testEmptyFind() throws NullKeyException{
        ProjOneDictionary<String,String> dict = newDictionary();
        assertNull(dict.find("A"),"Incorrect empty find behavior");
    }
    @Test
    void testSingleFind() throws NullKeyException{
        ProjOneDictionary<String,String> dict = newDictionary();
        try{
            dict.insert("3","three");
        } catch (NullValueException e){
            e.printStackTrace();
        }
        assertEquals("three",dict.find("3"),"Incorrect single element find behavior");
    }
    @Test
    void testManyFind() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        Random newRandInt = new Random();
        int numToFind = -1;
        int newNum;

        for (int i = 0; i < 1048576; ++i) {
            do {
                newNum = newRandInt.nextInt(1048576);
            } while (newNum == numToFind);

            dict.insert(Integer.toString(newNum), Integer.toString(i));
            if (i == 1024) {
                numToFind = newNum;
            }
        }
        String toFind = Integer.toString(numToFind);
        assertEquals(Integer.toString(1024), dict.find(toFind), "Incorrect find behavior for many elements");
        assertNull(dict.find("2000000"), "Incorrect find behavior for element not in the dictionary");
    }
    @Test
    void testIteratorEmpty() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        Iterator<String> iter = dict.iterator();
        assertFalse(iter.hasNext(), "Incorrect empty dictionary Iterator behavior");
    }
    @Test
    void testIteratorSingle() throws NullKeyException{
        ProjOneDictionary<String,String> dict = newDictionary();
        try{
            dict.insert("3","three");
        } catch (NullValueException e){
            e.printStackTrace();
        }
        Iterator<String> iter = dict.iterator();
        assertTrue(iter.hasNext(), "Incorrect single element hasNext Iterator behavior");
        assertEquals("3",iter.next(), "Incorrect single element iterator behavior");
        assertFalse(iter.hasNext(), "Incorrect emptied hasNext Iterator behavior");
    }
    @Test
    void testIteratorMany() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        Set<String> exampleSet = new LinkedHashSet<>();
        Random newRandInt = new Random();
        int newNum;

        for (int i = 0; i < 1048576; ++i) {
            newNum = newRandInt.nextInt(1048576);
            dict.insert(Integer.toString(newNum), Integer.toString(i));
            exampleSet.add(Integer.toString(newNum));
        }

        Iterator<String> iterD = dict.iterator();
        Iterator<String> iterE = exampleSet.iterator();
        while (iterE.hasNext()) {
            assertTrue(iterD.hasNext());
            iterE.next();
            iterD.next();
        }
        assertFalse(iterD.hasNext(), "Has excess iterator");
    }
    @Test
    void testNullInsert(){
        ProjOneDictionary<String, String> dict = newDictionary();
        assertThrowsExactly(NullKeyException.class, () -> {dict.insert(null, "three");} );
        assertThrowsExactly(NullValueException.class, () -> {dict.insert("3", null);} );
    }
    @Test
    void testNullFind() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        dict.insert("3", "three");

        assertThrowsExactly(NullKeyException.class, () -> {dict.find(null);} );
    }
    @Test
    void testNullDelete() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        dict.insert("3", "three");

        assertThrowsExactly(NullKeyException.class, () -> {dict.delete(null);} );
    }
    @Test
    void testEmptyDelete() throws NullKeyException{
        ProjOneDictionary<String, String> dict = newDictionary();
        assertFalse(dict.delete("3"), "Incorrect deletion on empty dictionary");
    }
    @Test
    void testSingleDelete() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        dict.insert("3", "three");
        assertTrue(dict.delete("3"), "Incorrect deletion of single element");
    }
    @Test
    void testManyDelete() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        Random newRandInt = new Random();
        String newNum;

        for (int i = 0; i < 1048576; ++i) {
            dict.insert(Integer.toString(newRandInt.nextInt(1048576)), Integer.toString(i));
        }

        int finalSize = dict.getSize();

        for (int i = 0; i < 2048; ++i) {
            newNum = Integer.toString(newRandInt.nextInt(1048576));
            if (dict.find(newNum) != null) { finalSize--; }
            dict.delete(newNum);
        }

        assertEquals(finalSize, dict.getSize(), "Incorrect deletion of many elements");
    }
    @Test
    void testEmptyGetSize(){
        ProjOneDictionary<String, String> dict = newDictionary();
        assertEquals(0, dict.getSize(), "Incorrect size of empty dictionary");
    }
    @Test
    void testSingleGetSize() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        dict.insert("3", "three");
        assertEquals(1, dict.getSize(), "Incorrect size of single element");
    }
    @Test
    void testManyGetSize() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        for (int i = 0; i < 1048576; ++i) {
            dict.insert(Integer.toString(i), Integer.toString(i));
        }
        assertEquals(1048576, dict.getSize(), "Incorrect size of many elements");
    }
    @Test
    void testDuplicateInsert() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        dict.insert("3", "three");
        assertTrue(dict.insert("3", "THREE!"), "Incorrect return on existing key");
        assertEquals("THREE!", dict.find("3"), "Incorrect duplicate key overwrite behavior");
    }
    @Test
    void testManyDuplicateInsert() throws NullKeyException, NullValueException{
        ProjOneDictionary<String, String> dict = newDictionary();
        Random newRandInt = new Random();

        for (int i = 0; i < 1048576; ++i) {
            dict.insert(Integer.toString(newRandInt.nextInt(4)), Integer.toString(i));
        }
        assertEquals(4, dict.getSize(), "Incorrect size after many duplicates");
    }
}



