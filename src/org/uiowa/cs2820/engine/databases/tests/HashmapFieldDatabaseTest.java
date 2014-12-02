package org.uiowa.cs2820.engine.databases.tests;

import org.junit.Test;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.HashmapFieldDatabase;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;

public class HashmapFieldDatabaseTest extends FieldDatabaseTest
{

    @Override
    public FieldDatabase getDatabase(ChunkedAccess file)
    {
        return new HashmapFieldDatabase(file);
    }

    @Override
    @Test
    public void testRemovalFromSingleElementDatabase()
    {
    }

    @Override
    @Test
    public void testRemovingRootFromTwoElementDatabase()
    {
    }

    @Override
    @Test
    public void testRemovingMiddleFromtThreeElementDatabase()
    {
    }

    @Override
    @Test
    public void testRemovingChildFromTwoElementDatabase()
    {
    }

    @Override
    @Test
    public void testLeftChildFromtThreeElementDatabase()
    {
    }

    @Override
    @Test
    public void testRightChildFromtThreeElementDatabase()
    {
    }

    @Override
    @Test
    public void testRemoveRootFromtThreeElementDatabase()
    {
    }

    @Override
    @Test
    public void testMultiElementDatabaseRemoval()
    {
    }

    
}
