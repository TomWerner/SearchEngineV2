package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.BinaryTreeFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;

public class BinaryTreeIteratorTest
{
    @Test
    public void testSingleElement()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("z", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("z", "a"), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testElementToTheRight()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("m", "a"), 0));
        database.add(new FieldFileNode(new Field("n", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("m", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("n", "a"), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testElementToTheLeft()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("m", "a"), 0));
        database.add(new FieldFileNode(new Field("l", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("l", "a"), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(new Field("m", "a"), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testSortedTree()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));
        database.add(new FieldFileNode(new Field("c", "a"), 0));
        database.add(new FieldFileNode(new Field("d", "a"), 0));
        database.add(new FieldFileNode(new Field("e", "a"), 0));
        database.add(new FieldFileNode(new Field("f", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("c", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("d", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("e", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("f", "a"), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testUnsortedTree()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("a", "a"), 0));
        database.add(new FieldFileNode(new Field("c", "a"), 0));
        database.add(new FieldFileNode(new Field("e", "a"), 0));
        database.add(new FieldFileNode(new Field("f", "a"), 0));
        database.add(new FieldFileNode(new Field("d", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("c", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("d", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("e", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("f", "a"), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testReverseSortedTree()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("f", "a"), 0));
        database.add(new FieldFileNode(new Field("e", "a"), 0));
        database.add(new FieldFileNode(new Field("d", "a"), 0));
        database.add(new FieldFileNode(new Field("c", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("c", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("d", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("e", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("f", "a"), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemoveInMiddle()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("f", "a"), 0));
        database.add(new FieldFileNode(new Field("e", "a"), 0));
        database.add(new FieldFileNode(new Field("d", "a"), 0));
        database.add(new FieldFileNode(new Field("c", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());

        iterator.remove();
        // Check that its gone
        assertEquals(-1, database.getIdentifierPosition(new Field("b", "a")));

        assertTrue(iterator.hasNext());
        assertEquals(new Field("c", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("d", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("e", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("f", "a"), iterator.next());

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testRemoveAtEnd()
    {
        FieldDatabase database = getDatabase();

        database.add(new FieldFileNode(new Field("f", "a"), 0));
        database.add(new FieldFileNode(new Field("e", "a"), 0));
        database.add(new FieldFileNode(new Field("d", "a"), 0));
        database.add(new FieldFileNode(new Field("c", "a"), 0));
        database.add(new FieldFileNode(new Field("b", "a"), 0));
        database.add(new FieldFileNode(new Field("a", "a"), 0));
        Iterator<Field> iterator = database.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(new Field("a", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("b", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("c", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("d", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("e", "a"), iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(new Field("f", "a"), iterator.next());

        assertFalse(iterator.hasNext());
        
        iterator.remove();
        // Check that its gone
        assertEquals(-1, database.getIdentifierPosition(new Field("f", "a")));

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testIteratorRemove()
    {
        FieldDatabase database = getDatabase();
        ArrayList<Field> mockDatabase = new ArrayList<Field>();

        int numElements = 5;
        for (int i = 0; i < numElements; i++)
        {
            int num = i;
            database.add(new FieldFileNode(new Field("a", num), 0));
            mockDatabase.add(new Field("a", num));
        }
        Collections.sort(mockDatabase);

        Iterator<Field> dbIter = database.iterator();
        Iterator<Field> mockDbIter = mockDatabase.iterator();

        int counter = 0;
        while (dbIter.hasNext() && mockDbIter.hasNext())
        {
            int num = (Integer) dbIter.next().getFieldValue();
            assertEquals(counter, num);
            assertEquals(counter, mockDbIter.next().getFieldValue());
            dbIter.remove();
            mockDbIter.remove();
            counter++;
        }
        assertEquals(numElements, counter);
        assertFalse(mockDbIter.hasNext());
        assertFalse(dbIter.hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testIllegalRemoveThrowsError()
    {
        FieldDatabase database = getDatabase();
        Iterator<Field> iter = database.iterator();
        iter.remove();
    }

    protected FieldDatabase getDatabase()
    {
        return new BinaryTreeFieldDatabase(new MockChunkRandomAccessFile(4, FieldFileNode.MAX_SIZE));
    }
}
