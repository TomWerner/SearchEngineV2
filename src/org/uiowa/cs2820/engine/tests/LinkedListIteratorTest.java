package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.databases.ValueFileNode;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;

public class LinkedListIteratorTest
{
    @Test(expected = NoSuchElementException.class)
    public void testErrorThrownOnEmptyList()
    {
        IdentifierDatabase database = getDatabase();
        Iterator<String> iter = database.iterator(0);
        iter.next();
    }
    
    @Test
    public void testSimpleList()
    {
        IdentifierDatabase database = getDatabase();
        int headPosition = database.addIdentifier("Identifier1");
        headPosition = database.addIdentifier(headPosition, "Identifier2");
        
        Iterator<String> iter = database.iterator(headPosition);
        
        assertTrue(iter.hasNext());
        assertEquals("Identifier2", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier1", iter.next());
        assertFalse(iter.hasNext());
    }
    
    @Test
    public void testLongList()
    {
        IdentifierDatabase database = getDatabase();
        int headPosition = database.addIdentifier("Identifier1");
        headPosition = database.addIdentifier(headPosition, "Identifier2");
        headPosition = database.addIdentifier(headPosition, "Identifier3");
        headPosition = database.addIdentifier(headPosition, "Identifier4");
        headPosition = database.addIdentifier(headPosition, "Identifier5");
        headPosition = database.addIdentifier(headPosition, "Identifier6");
        headPosition = database.addIdentifier(headPosition, "Identifier7");
        
        Iterator<String> iter = database.iterator(headPosition);

        assertTrue(iter.hasNext());
        assertEquals("Identifier7", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier6", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier5", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier4", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier3", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier2", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier1", iter.next());
        assertFalse(iter.hasNext());
    }
    
    @Test
    public void testRemoveHead()
    {
        IdentifierDatabase database = getDatabase();
        int headPosition = database.addIdentifier("Identifier1");
        headPosition = database.addIdentifier(headPosition, "Identifier2");
        headPosition = database.addIdentifier(headPosition, "Identifier3");
        headPosition = database.addIdentifier(headPosition, "Identifier4");
        headPosition = database.addIdentifier(headPosition, "Identifier5");
        headPosition = database.addIdentifier(headPosition, "Identifier6");
        headPosition = database.addIdentifier(headPosition, "Identifier7");
        
        Iterator<String> iter = database.iterator(headPosition);

        assertTrue(iter.hasNext());
        assertEquals("Identifier7", iter.next());
        
        iter.remove();        
        
        assertTrue(iter.hasNext());
        assertEquals("Identifier6", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier5", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier4", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier3", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier2", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier1", iter.next());
        assertFalse(iter.hasNext());
        
        // Now check that the whole list looks right, but without 7
        iter = database.iterator(headPosition);

        assertTrue(iter.hasNext());
        assertEquals("Identifier6", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier5", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier4", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier3", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier2", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier1", iter.next());
        assertFalse(iter.hasNext());
    }
    
    @Test
    public void testRemoveEnd()
    {
        IdentifierDatabase database = getDatabase();
        int headPosition = database.addIdentifier("Identifier1");
        headPosition = database.addIdentifier(headPosition, "Identifier2");
        headPosition = database.addIdentifier(headPosition, "Identifier3");
        
        Iterator<String> iter = database.iterator(headPosition);

        assertEquals("Identifier3", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier2", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier1", iter.next());
        assertFalse(iter.hasNext());
        
        // Remove Identifier1, the end of the list
        iter.remove();
        
        // Now check that the whole list looks right, but without 7
        iter = database.iterator(headPosition);

        assertEquals("Identifier3", iter.next());
        assertTrue(iter.hasNext());
        assertEquals("Identifier2", iter.next());
        assertFalse(iter.hasNext());
    }
    
    protected IdentifierDatabase getDatabase()
    {
        return new IdentifierDatabase(new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE));
    }
}
