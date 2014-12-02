package org.uiowa.cs2820.engine.databases.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.databases.ValueFileNode;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;

public class IdentifierDatabaseTest
{
    @Test
    public void testSingleAddGetCycle()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);

        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename");

        ArrayList<String> results = identDB.getAllIdentifiers(location);
        assertEquals(1, results.size());
        assertEquals("filename", results.get(0));
    }

    @Test
    public void testTwoIdentifiers()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);

        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename1");
        location = identDB.addIdentifier(location, "filename2");

        ArrayList<String> results = identDB.getAllIdentifiers(location);
        assertEquals(2, results.size());
        assertEquals("filename2", results.get(0));
        assertEquals("filename1", results.get(1));
    }

    @Test
    public void testIdentifierExpandingFile()
    {
        // We set the size to 2, put 3 elements in it
        ChunkedAccess file = new MockChunkRandomAccessFile(2, ValueFileNode.MAX_SIZE);

        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename1");
        location = identDB.addIdentifier(location, "filename2");
        location = identDB.addIdentifier(location, "filename3");

        ArrayList<String> results = identDB.getAllIdentifiers(location);
        assertEquals("filename3", results.get(0));
        assertEquals("filename2", results.get(1));
        assertEquals("filename1", results.get(2));
    }

    @Test
    public void testRemovingSingleIdentifier()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);

        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename1");

        ArrayList<String> results = identDB.getAllIdentifiers(location);
        assertEquals(1, results.size());
        assertEquals("filename1", results.get(0));

        // Now we know that we have two identifiers in there. Lets take it out
        identDB.removeIdentifier(location, "filename1");

        results = identDB.getAllIdentifiers(location);
        assertEquals(0, results.size());
    }

    @Test
    public void testRemovingFirstIdentifier()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);

        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename1");
        location = identDB.addIdentifier(location, "filename2");
        location = identDB.addIdentifier(location, "filename3");

        ArrayList<String> results = identDB.getAllIdentifiers(location);

        assertEquals("filename3", results.get(0));
        assertEquals("filename2", results.get(1));
        assertEquals("filename1", results.get(2));

        int result = identDB.removeIdentifier(location, "filename3");
        if (result != ValueFileNode.NULL_ADDRESS)
            location = result;

        results = identDB.getAllIdentifiers(location);
        assertEquals("filename2", results.get(0));
        assertEquals("filename1", results.get(1));
    }

    @Test
    public void testRemovingLastIdentifier()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);

        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename1");
        location = identDB.addIdentifier(location, "filename2");
        location = identDB.addIdentifier(location, "filename3");

        ArrayList<String> results = identDB.getAllIdentifiers(location);
        assertEquals("filename3", results.get(0));
        assertEquals("filename2", results.get(1));
        assertEquals("filename1", results.get(2));

        int result = identDB.removeIdentifier(location, "filename1");
        if (result != ValueFileNode.NULL_ADDRESS)
            location = result;

        results = identDB.getAllIdentifiers(location);
        assertEquals("filename3", results.get(0));
        assertEquals("filename2", results.get(1));
    }

    @Test
    public void testRemovingMiddleIdentifier()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);

        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename1");
        location = identDB.addIdentifier(location, "filename2");
        location = identDB.addIdentifier(location, "filename3");

        ArrayList<String> results = identDB.getAllIdentifiers(location);
        assertEquals("filename3", results.get(0));
        assertEquals("filename2", results.get(1));
        assertEquals("filename1", results.get(2));

        int result = identDB.removeIdentifier(location, "filename2");
        if (result != ValueFileNode.NULL_ADDRESS)
            location = result;

        results = identDB.getAllIdentifiers(location);
        assertEquals("filename3", results.get(0));
        assertEquals("filename1", results.get(1));
    }

    @Test
    public void testRemovingThenAddingSeparateIdentifier()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);

        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename1");
        location = identDB.addIdentifier(location, "filename2");
        location = identDB.addIdentifier(location, "filename3");

        ArrayList<String> results = identDB.getAllIdentifiers(location);
        assertEquals("filename3", results.get(0));
        assertEquals("filename2", results.get(1));
        assertEquals("filename1", results.get(2));

        int result = identDB.removeIdentifier(location, "filename2");
        if (result != ValueFileNode.NULL_ADDRESS)
            location = result;

        results = identDB.getAllIdentifiers(location);
        assertEquals("filename3", results.get(0));
        assertEquals("filename1", results.get(1));

        location = identDB.addIdentifier("other thing");

        results = identDB.getAllIdentifiers(location);
        assertEquals("other thing", results.get(0));
    }

    @Test
    public void testRemovingIdentifierNotThere()
    {
        ChunkedAccess file = new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE);
        
        IdentifierDatabase identDB = new IdentifierDatabase(file);
        int location = identDB.addIdentifier("filename1");
        
        ArrayList<String> results = identDB.getAllIdentifiers(location);
        assertEquals(1, results.size());
        assertEquals("filename1", results.get(0));
        
        identDB.removeIdentifier(location, "filename3");
    }

}
