package org.uiowa.cs2820.engine.databases.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;

public class AVLFieldDatabaseTest extends FieldDatabaseTest
{
    @Override
    public FieldDatabase getDatabase(ChunkedAccess file)
    {
        return new AVLFieldDatabase(file);
    }

    @Override
    @Test
    public void testAddingMultipleValuesWorstCase()
    {
        super.testAddingMultipleValuesWorstCase();
        
        // BENEFIT OF THE AVL TREE. With the standard tree it'd be a depth of 10
        assertTrue(((AVLFieldDatabase) fieldDB).depth(0) <= 5);
    }

    @Override
    @Test
    public void testAddingMultipleValuesWorstCaseReversed()
    {
        super.testAddingMultipleValuesWorstCaseReversed();
        
        // BENEFIT OF THE AVL TREE. With the standard tree it'd be a depth of 10
        assertTrue(((AVLFieldDatabase) fieldDB).depth(0) <= 5);
    }

    @Override
    @Test
    public void testAddingMultipleValuesNormalCase()
    {
        super.testAddingMultipleValuesNormalCase();
        
        // BENEFIT OF THE AVL TREE. With the standard tree it'd be a depth of 10
        assertTrue(((AVLFieldDatabase) fieldDB).depth(0) <= 5);
    }


    @Test
    public void testHeightOfTreeStructureRandomData()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(6, FieldFileNode.MAX_SIZE);

        FieldDatabase fieldDB = getDatabase(file);

        int number = 100;
        for (int i = 0; i < number; i++)
            fieldDB.add(new FieldFileNode(new Field("name", Math.random()), 0));

        int theoretical = (int) (Math.log(number) / Math.log(2) + .5);
        int actual = ((AVLFieldDatabase) fieldDB).depth(0);
        assertTrue(Math.abs(theoretical - actual) <= 2);
    }
    
    @Test
    public void testHeightOfTreeStructureSortedData()
    {
        MockChunkRandomAccessFile file = new MockChunkRandomAccessFile(6, FieldFileNode.MAX_SIZE);

        FieldDatabase fieldDB = getDatabase(file);

        int number = 100;
        for (int i = 0; i < number; i++)
            fieldDB.add(new FieldFileNode(new Field("name", i), 0));

        int theoretical = (int) (Math.log(number) / Math.log(2) + .5);
        int actual = ((AVLFieldDatabase) fieldDB).depth(0);
        assertTrue(Math.abs(theoretical - actual) <= 2);
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
        database.add(new FieldFileNode(new Field("d", "a"), 0));

        //      b
        //     / \
        //    a   b
        //         \
        //          d
        // Remove "c"
        database.removeElement(2);

        assertEquals(new FieldFileNode(new Field("b", "a"), 0), database.getElementAt(0));
        assertEquals(new FieldFileNode(new Field("a", "a"), 0), database.getElementAt(1));
        assertEquals(new FieldFileNode(new Field("d", "a"), 0), database.getElementAt(3));
        
        Iterator<Field> iterator = database.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("d", "a"), iterator.next());
        assertFalse(iterator.hasNext());
    }@Test
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
        assertEquals(new FieldFileNode(new Field("a", 21), 0), database.getElementAt(6));
        assertEquals(null,                                     database.getElementAt(7));
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
