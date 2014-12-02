package org.uiowa.cs2820.engine.databases.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;

public abstract class FieldDatabaseTest
{
    protected FieldDatabase fieldDB;
    public abstract FieldDatabase getDatabase(ChunkedAccess file);
    
    @Test
    public void testInitialAddGetCycle()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE);

        fieldDB = getDatabase(file);
        FieldFileNode node = new FieldFileNode(new Field("name", "value"), 11);
        fieldDB.add(node);

        assertEquals(11, fieldDB.getIdentifierPosition(new Field("name", "value")));
    }

    @Test
    public void testAddRootAndLeft()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE);

        fieldDB = getDatabase(file);
        FieldFileNode node1 = new FieldFileNode(new Field("name", "a"), 0);
        FieldFileNode node2 = new FieldFileNode(new Field("name", "b"), 1);
        fieldDB.add(node1);
        fieldDB.add(node2);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "a")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "b")));
    }

    @Test
    public void testAddRootAndRight()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE);

        fieldDB = getDatabase(file);
        FieldFileNode node1 = new FieldFileNode(new Field("name", "b"), 0);
        FieldFileNode node2 = new FieldFileNode(new Field("name", "a"), 1);
        fieldDB.add(node1);
        fieldDB.add(node2);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "b")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "a")));
    }

    @Test
    public void testAddingADuplicate()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE);

        fieldDB = getDatabase(file);
        FieldFileNode node1 = new FieldFileNode(new Field("name", "a"), 11);
        FieldFileNode node2 = new FieldFileNode(new Field("name", "a"), 1);
        fieldDB.add(node1);
        fieldDB.add(node2);

        assertEquals(11, fieldDB.getIdentifierPosition(new Field("name", "a")));
        // We won't overwrite data
        assertEquals(11, fieldDB.getIdentifierPosition(new Field("name", "a")));
    }

    @Test
    public void testAddingMultipleValuesWorstCase()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(6, FieldFileNode.MAX_SIZE);

        fieldDB = getDatabase(file);
        assertEquals(8, file.getNumberOfChunks());

        FieldFileNode node1 = new FieldFileNode(new Field("name", "a"), 0);
        FieldFileNode node2 = new FieldFileNode(new Field("name", "b"), 1);
        FieldFileNode node3 = new FieldFileNode(new Field("name", "c"), 2);
        FieldFileNode node4 = new FieldFileNode(new Field("name", "d"), 3);
        FieldFileNode node5 = new FieldFileNode(new Field("name", "e"), 4);
        FieldFileNode node6 = new FieldFileNode(new Field("name", "f"), 5);
        FieldFileNode node7 = new FieldFileNode(new Field("name", "g"), 6);
        FieldFileNode node8 = new FieldFileNode(new Field("name", "h"), 7);
        FieldFileNode node9 = new FieldFileNode(new Field("name", "i"), 8);
        FieldFileNode node10 = new FieldFileNode(new Field("name", "j"), 9);
        fieldDB.add(node1);
        fieldDB.add(node2);
        fieldDB.add(node3);
        fieldDB.add(node4);
        fieldDB.add(node5);
        fieldDB.add(node6);
        fieldDB.add(node7);
        fieldDB.add(node8);
        fieldDB.add(node9);
        fieldDB.add(node10);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "a")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "b")));
        assertEquals(2, fieldDB.getIdentifierPosition(new Field("name", "c")));
        assertEquals(3, fieldDB.getIdentifierPosition(new Field("name", "d")));
        assertEquals(4, fieldDB.getIdentifierPosition(new Field("name", "e")));
        assertEquals(5, fieldDB.getIdentifierPosition(new Field("name", "f")));
        assertEquals(6, fieldDB.getIdentifierPosition(new Field("name", "g")));
        assertEquals(7, fieldDB.getIdentifierPosition(new Field("name", "h")));
        assertEquals(8, fieldDB.getIdentifierPosition(new Field("name", "i")));
        assertEquals(9, fieldDB.getIdentifierPosition(new Field("name", "j")));
    }
    
    @Test
    public void testAddingMultipleValuesWorstCaseReversed()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(6, FieldFileNode.MAX_SIZE);

        fieldDB = getDatabase(file);
        assertEquals(8, file.getNumberOfChunks());

        FieldFileNode node1 = new FieldFileNode(new Field("name", "j"), 0);
        FieldFileNode node2 = new FieldFileNode(new Field("name", "i"), 1);
        FieldFileNode node3 = new FieldFileNode(new Field("name", "h"), 2);
        FieldFileNode node4 = new FieldFileNode(new Field("name", "g"), 3);
        FieldFileNode node5 = new FieldFileNode(new Field("name", "f"), 4);
        FieldFileNode node6 = new FieldFileNode(new Field("name", "e"), 5);
        FieldFileNode node7 = new FieldFileNode(new Field("name", "d"), 6);
        FieldFileNode node8 = new FieldFileNode(new Field("name", "c"), 7);
        FieldFileNode node9 = new FieldFileNode(new Field("name", "b"), 8);
        FieldFileNode node10 = new FieldFileNode(new Field("name", "a"), 9);
        fieldDB.add(node1);
        fieldDB.add(node2);
        fieldDB.add(node3);
        fieldDB.add(node4);
        fieldDB.add(node5);
        fieldDB.add(node6);
        fieldDB.add(node7);
        fieldDB.add(node8);
        fieldDB.add(node9);
        fieldDB.add(node10);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "j")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "i")));
        assertEquals(2, fieldDB.getIdentifierPosition(new Field("name", "h")));
        assertEquals(3, fieldDB.getIdentifierPosition(new Field("name", "g")));
        assertEquals(4, fieldDB.getIdentifierPosition(new Field("name", "f")));
        assertEquals(5, fieldDB.getIdentifierPosition(new Field("name", "e")));
        assertEquals(6, fieldDB.getIdentifierPosition(new Field("name", "d")));
        assertEquals(7, fieldDB.getIdentifierPosition(new Field("name", "c")));
        assertEquals(8, fieldDB.getIdentifierPosition(new Field("name", "b")));
        assertEquals(9, fieldDB.getIdentifierPosition(new Field("name", "a")));
    }

    @Test
    public void testAddingMultipleValuesNormalCase()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(6, FieldFileNode.MAX_SIZE);

        fieldDB = getDatabase(file);
        FieldFileNode node1 = new FieldFileNode(new Field("name", "e"), 0);
        FieldFileNode node2 = new FieldFileNode(new Field("name", "b"), 1);
        FieldFileNode node3 = new FieldFileNode(new Field("name", "j"), 2);
        FieldFileNode node4 = new FieldFileNode(new Field("name", "d"), 3);
        FieldFileNode node5 = new FieldFileNode(new Field("name", "c"), 4);
        FieldFileNode node6 = new FieldFileNode(new Field("name", "i"), 5);
        FieldFileNode node7 = new FieldFileNode(new Field("name", "a"), 6);
        FieldFileNode node8 = new FieldFileNode(new Field("name", "h"), 7);
        FieldFileNode node9 = new FieldFileNode(new Field("name", "g"), 8);
        FieldFileNode node10 = new FieldFileNode(new Field("name", "f"), 9);
        fieldDB.add(node1);
        fieldDB.add(node2);
        fieldDB.add(node3);
        fieldDB.add(node4);
        fieldDB.add(node5);
        fieldDB.add(node6);
        fieldDB.add(node7);
        fieldDB.add(node8);
        fieldDB.add(node9);
        fieldDB.add(node10);

        assertEquals(0, fieldDB.getIdentifierPosition(new Field("name", "e")));
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "b")));
        assertEquals(2, fieldDB.getIdentifierPosition(new Field("name", "j")));
        assertEquals(3, fieldDB.getIdentifierPosition(new Field("name", "d")));
        assertEquals(4, fieldDB.getIdentifierPosition(new Field("name", "c")));
        assertEquals(5, fieldDB.getIdentifierPosition(new Field("name", "i")));
        assertEquals(6, fieldDB.getIdentifierPosition(new Field("name", "a")));
        assertEquals(7, fieldDB.getIdentifierPosition(new Field("name", "h")));
        assertEquals(8, fieldDB.getIdentifierPosition(new Field("name", "g")));
        assertEquals(9, fieldDB.getIdentifierPosition(new Field("name", "f")));
    }
    
    @Test
    public void testEdgeCasesOnGetIdentifier()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(6, FieldFileNode.MAX_SIZE);
        fieldDB = getDatabase(file);
        
        // Check if nothing is in the database
        int number = fieldDB.getIdentifierPosition(new Field("name", "value"));
        assertEquals(-1, number);
        
        // Check for a value not there
        fieldDB.add(new FieldFileNode(new Field("name", "value"), 1));
        number = fieldDB.getIdentifierPosition(new Field("name", "not there"));
        assertEquals(-1, number);
    }
    
    @Test
    public void testSetIdentifier()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(6, FieldFileNode.MAX_SIZE);

        fieldDB = getDatabase(file);
        FieldFileNode node1 = new FieldFileNode(new Field("name", "e"), 0);
        FieldFileNode node2 = new FieldFileNode(new Field("name", "b"), 1);
        FieldFileNode node3 = new FieldFileNode(new Field("name", "j"), 2);
        FieldFileNode node4 = new FieldFileNode(new Field("name", "d"), 3);
        FieldFileNode node5 = new FieldFileNode(new Field("name", "c"), 4);
        FieldFileNode node6 = new FieldFileNode(new Field("name", "i"), 5);
        FieldFileNode node7 = new FieldFileNode(new Field("name", "a"), 6);
        FieldFileNode node8 = new FieldFileNode(new Field("name", "h"), 7);
        FieldFileNode node9 = new FieldFileNode(new Field("name", "g"), 8);
        FieldFileNode node10 = new FieldFileNode(new Field("name", "f"), 9);
        fieldDB.add(node1);
        fieldDB.add(node2);
        fieldDB.add(node3);
        fieldDB.add(node4);
        fieldDB.add(node5);
        fieldDB.add(node6);
        fieldDB.add(node7);
        fieldDB.add(node8);
        fieldDB.add(node9);
        fieldDB.add(node10);

        assertEquals(0, fieldDB.getIdentifierPosition(node1.getField()));
        assertEquals(1, fieldDB.getIdentifierPosition(node2.getField()));
        assertEquals(2, fieldDB.getIdentifierPosition(node3.getField()));
        assertEquals(3, fieldDB.getIdentifierPosition(node4.getField()));
        assertEquals(4, fieldDB.getIdentifierPosition(node5.getField()));
        assertEquals(5, fieldDB.getIdentifierPosition(node6.getField()));
        assertEquals(6, fieldDB.getIdentifierPosition(node7.getField()));
        assertEquals(7, fieldDB.getIdentifierPosition(node8.getField()));
        assertEquals(8, fieldDB.getIdentifierPosition(node9.getField()));
        assertEquals(9, fieldDB.getIdentifierPosition(node10.getField()));
        
        // Now change all the values
        fieldDB.setIdentifierPosition(node1.getField(), 9);
        fieldDB.setIdentifierPosition(node2.getField(), 8);
        fieldDB.setIdentifierPosition(node3.getField(), 7);
        fieldDB.setIdentifierPosition(node4.getField(), 6);
        fieldDB.setIdentifierPosition(node5.getField(), 5);
        fieldDB.setIdentifierPosition(node6.getField(), 4);
        fieldDB.setIdentifierPosition(node7.getField(), 3);
        fieldDB.setIdentifierPosition(node8.getField(), 2);
        fieldDB.setIdentifierPosition(node9.getField(), 1);
        fieldDB.setIdentifierPosition(node10.getField(), 0);
        
        
        // And check that the new values are correct
        assertEquals(9, fieldDB.getIdentifierPosition(node1.getField()));
        assertEquals(8, fieldDB.getIdentifierPosition(node2.getField()));
        assertEquals(7, fieldDB.getIdentifierPosition(node3.getField()));
        assertEquals(6, fieldDB.getIdentifierPosition(node4.getField()));
        assertEquals(5, fieldDB.getIdentifierPosition(node5.getField()));
        assertEquals(4, fieldDB.getIdentifierPosition(node6.getField()));
        assertEquals(3, fieldDB.getIdentifierPosition(node7.getField()));
        assertEquals(2, fieldDB.getIdentifierPosition(node8.getField()));
        assertEquals(1, fieldDB.getIdentifierPosition(node9.getField()));
        assertEquals(0, fieldDB.getIdentifierPosition(node10.getField()));
    }
    
    @Test
    public void testEdgeCasesOnSetIdentifier()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(6, FieldFileNode.MAX_SIZE);
        fieldDB = getDatabase(file);
        
        // Check if nothing is in the database
        fieldDB.setIdentifierPosition(new Field("name", "value"), 0);
                
        // Check for a value not there, no infinite loop, etc
        fieldDB.add(new FieldFileNode(new Field("name", "value"), 1));
        fieldDB.setIdentifierPosition(new Field("name", "not there"), 0);
        
        // Make sure that the value wasn't changed
        assertEquals(1, fieldDB.getIdentifierPosition(new Field("name", "value")));
    }
    
    @Test
    public void testRemovalFromSingleElementDatabase()
    {
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", "a"), 0));

        database.removeElement(0);
        assertEquals(null, database.getElementAt(0));
        
        Iterator<Field> iterator = database.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemovingRootFromTwoElementDatabase()
    {
        // This test case is special because we need to ensure
        // that the root is always at 0
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));

        database.removeElement(0);
        assertEquals(new FieldFileNode(new Field("b", "a"), 0), database.getElementAt(0));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemovingMiddleFromtThreeElementDatabase()
    {
        // This test case is special because we need to ensure
        // that the root is always at 0
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));
        database.add(new FieldFileNode(new Field("c", "a"), 0));

        // Remove "b"
        database.removeElement(1);
        
        FieldFileNode root = database.getElementAt(0);
        assertEquals(new FieldFileNode(new Field("a", "a"), 0), root);
        assertEquals(new FieldFileNode(new Field("c", "a"), 0), database.getElementAt(root.getRightPosition()));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("c", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemovingChildFromTwoElementDatabase()
    {
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));

        database.removeElement(1);
        assertEquals(new FieldFileNode(new Field("a", "a"), 0), database.getElementAt(0));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testLeftChildFromtThreeElementDatabase()
    {
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("m", "a"), 0)); // Root
        database.add(new FieldFileNode(new Field("n", "a"), 0)); // right child
        database.add(new FieldFileNode(new Field("l", "a"), 0)); // left child

        // Remove "l"
        database.removeElement(2);
        
        FieldFileNode root = database.getElementAt(0);
        assertEquals(new FieldFileNode(new Field("m", "a"), 0), root);
        assertEquals(new FieldFileNode(new Field("n", "a"), 0), database.getElementAt(root.getRightPosition()));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("m", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("n", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }
    
    @Test
    public void testRightChildFromtThreeElementDatabase()
    {
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("m", "a"), 0)); // Root
        database.add(new FieldFileNode(new Field("n", "a"), 0)); // right child
        database.add(new FieldFileNode(new Field("l", "a"), 0)); // left child

        // Remove "n"
        database.removeElement(1);
        
        FieldFileNode root = database.getElementAt(0);
        assertEquals(new FieldFileNode(new Field("m", "a"), 0), root);
        assertEquals(new FieldFileNode(new Field("l", "a"), 0), database.getElementAt(root.getLeftPosition()));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("l", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("m", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemoveRootFromtThreeElementDatabase()
    {
        // This test case is special because we need to ensure
        // that the root is always at 0
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("m", "a"), 0)); // Root
        database.add(new FieldFileNode(new Field("n", "a"), 0)); // right child
        database.add(new FieldFileNode(new Field("l", "a"), 0)); // left child

        // Remove "m"
        database.removeElement(0);
        
        FieldFileNode root = database.getElementAt(0);
        assertEquals(new FieldFileNode(new Field("n", "a"), 0), root);
        assertEquals(new FieldFileNode(new Field("l", "a"), 0), database.getElementAt(root.getLeftPosition()));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("l", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("n", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testMultiElementDatabaseRemoval()
    {
        // binary tree from http://www.algolist.net/img/bst-remove-case-3-4.png
        FieldDatabase database = getDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        database.add(new FieldFileNode(new Field("a", 5), 0));
        database.add(new FieldFileNode(new Field("a", 2), 0));
        database.add(new FieldFileNode(new Field("a", 12), 0));
        database.add(new FieldFileNode(new Field("a", -4), 0));
        database.add(new FieldFileNode(new Field("a", 3), 0));
        database.add(new FieldFileNode(new Field("a", 9), 0));
        database.add(new FieldFileNode(new Field("a", 12), 0));
        database.add(new FieldFileNode(new Field("a", 19), 0));
        database.add(new FieldFileNode(new Field("a", 21), 0));
        database.add(new FieldFileNode(new Field("a", 25), 0));
        
        // Remove "12"
        database.removeElement(2);
        
        assertEquals(new FieldFileNode(new Field("a", 5), 0),  database.getElementAt(0));
        assertEquals(new FieldFileNode(new Field("a", 2), 0),  database.getElementAt(1));
        assertEquals(new FieldFileNode(new Field("a", 19), 0), database.getElementAt(2));
        assertEquals(new FieldFileNode(new Field("a", -4), 0),  database.getElementAt(3));
        assertEquals(new FieldFileNode(new Field("a", 3), 0),  database.getElementAt(4));
        assertEquals(new FieldFileNode(new Field("a", 9), 0),  database.getElementAt(5));
        assertEquals(null,                                     database.getElementAt(6));
        assertEquals(new FieldFileNode(new Field("a", 21), 0), database.getElementAt(7));
        assertEquals(new FieldFileNode(new Field("a", 25), 0), database.getElementAt(8));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", -4), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 2), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 3), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 5), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 9), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 19), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 21), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", 25), iterator.next());
        assertFalse(iterator.hasNext());
    }
}
