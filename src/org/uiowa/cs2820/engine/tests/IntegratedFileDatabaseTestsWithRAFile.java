package org.uiowa.cs2820.engine.tests;

import java.io.File;

import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.BinaryTreeFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.HashmapFieldDatabase;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;
import org.uiowa.cs2820.engine.fileoperations.RAFile;

public class IntegratedFileDatabaseTestsWithRAFile extends IntegratedFileDatabaseTests
{
    public FieldDatabase[] getFieldDatabases()
    {
        ChunkedAccess avlFile = new RAFile(createTestFile("avlFile.dat"), 16, FieldFileNode.MAX_SIZE);
        ChunkedAccess binaryTreeFile = new RAFile(createTestFile("binaryTreeFile.dat"), 16, FieldFileNode.MAX_SIZE);
        ChunkedAccess hashMapFile = new RAFile(createTestFile("hashMapFile.dat"), 16, FieldFileNode.MAX_SIZE);

        return new FieldDatabase[] { new AVLFieldDatabase(avlFile), new BinaryTreeFieldDatabase(binaryTreeFile), new HashmapFieldDatabase(hashMapFile) };
    }

    public IdentifierDatabase[] getIdentifierDatabases()
    {
        ChunkedAccess identFile = new RAFile(createTestFile("identFile.dat"), 16, FieldFileNode.MAX_SIZE);
        return new IdentifierDatabase[] { new IdentifierDatabase(identFile) };
    }

    public File createTestFile(String filename)
    {
        File fileToDelete = new File(filename);
        if (fileToDelete.exists())
            fileToDelete.delete();
        return fileToDelete;
    }
}
