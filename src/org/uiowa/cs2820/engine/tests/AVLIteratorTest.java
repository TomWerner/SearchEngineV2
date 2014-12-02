package org.uiowa.cs2820.engine.tests;

import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;

public class AVLIteratorTest extends BinaryTreeIteratorTest
{
    protected FieldDatabase getDatabase()
    {
        return new AVLFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
    }
}
