package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.fileoperations.RAFile;

public class RAFileTest
{
    @Test
    public void testSimpleReadWriteCycle()
    {
    	File fileTest = doesFileExist();
        RAFile file = new RAFile(fileTest, 16, FieldFileNode.MAX_SIZE);
        FieldFileNode testObject = new FieldFileNode(new Field("name", "value"), 0);

        file.set(testObject.convert(), 1);
        FieldFileNode result = (FieldFileNode) file.get(1);

        assertEquals(testObject, result);
    }
    
    @Test 
    public void testChunkDeletion()
    {
    	File fileTest = doesFileExist();
    	RAFile file = new RAFile(fileTest, 16, FieldFileNode.MAX_SIZE);
    	FieldFileNode testObject = new FieldFileNode(new Field("name", "value"),1);

    	file.set(testObject.convert(), 1);
    	file.free(1);
    	FieldFileNode result = (FieldFileNode) file.get(1);
    	assertEquals(null, result);
    }

    @Test
    public void readLargeChunkTest()
    {
    	File fileTest = doesFileExist();
        RAFile file = new RAFile(fileTest, 16, FieldFileNode.MAX_SIZE);
        //BinaryFileNode testObject = new BinaryFileNode(new Field("name", "value"), 0);
        // ^^ shouldn't need this line since you aren't actually adding the testObject

        assertEquals(null,file.get(20));

    }

    @Test
    public void writeLargeChunkTest()
    {
    	File fileTest = doesFileExist();
        RAFile file = new RAFile(fileTest, 16, FieldFileNode.MAX_SIZE);
        FieldFileNode testObject = new FieldFileNode(new Field("name", "value"), 0);

        file.set(testObject.convert(), 25);
        FieldFileNode result = (FieldFileNode) file.get(25);

        assertEquals(result, file.get(25));
    }
    
    @Test
    public void testOverwriteOccupiedChunk()
    {
    	File fileTest = doesFileExist();
    	RAFile file = new RAFile(fileTest, 16, FieldFileNode.MAX_SIZE);
    	FieldFileNode testObject = new FieldFileNode(new Field("name","value"),1);
    	file.set(testObject.convert(), 1);
    	
    	FieldFileNode overwriteObject = new FieldFileNode(new Field("number","value"),1);
    	file.set(overwriteObject.convert(), 1);

    	FieldFileNode result = (FieldFileNode) file.get(1);
    	assertEquals(overwriteObject, result);
    }
    
    @Test
    public void testNextAvailableChunk()
    {
    	File fileTest = doesFileExist();
    	RAFile file = new RAFile(fileTest, 4, FieldFileNode.MAX_SIZE);
    	FieldFileNode testObject = new FieldFileNode(new Field("name","value"),1);
    	file.set(testObject.convert(), 0);
    	assertEquals(1, file.nextAvailableChunk());
    	
    }
    
    @Test
    public void testIsFileSizeDoubled()
    {
    	File fileTest = doesFileExist();
    	RAFile file = new RAFile(fileTest, 1, FieldFileNode.MAX_SIZE);
    	FieldFileNode testObject = new FieldFileNode(new Field("name","value"),1);
    	file.set(testObject.convert(), 0);
    	
    	FieldFileNode testObject2 = new FieldFileNode(new Field("number","variable"),2);
    	file.set(testObject2.convert(), 1);
    	FieldFileNode result = (FieldFileNode) file.get(1);
    	
    	assertEquals(2, file.nextAvailableChunk());	
    	assertEquals(testObject2, result);
    }
    
    public File doesFileExist()
    {
    	File fileToDelete = new File("test filename.dat");
    	if (fileToDelete.exists())
    		fileToDelete.delete();
    	return fileToDelete;
    }
     
}

	