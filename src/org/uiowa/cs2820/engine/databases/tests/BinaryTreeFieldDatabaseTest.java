package org.uiowa.cs2820.engine.databases.tests;

import org.uiowa.cs2820.engine.databases.BinaryTreeFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;

public class BinaryTreeFieldDatabaseTest extends FieldDatabaseTest
{
    @Override
    public FieldDatabase getDatabase(ChunkedAccess file)
    {
        return new BinaryTreeFieldDatabase(file);
    }
}
