package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;
import org.uiowa.cs2820.engine.Database;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.FieldSearch;
import org.uiowa.cs2820.engine.Indexer;
import org.uiowa.cs2820.engine.IntegratedFileDatabase;
import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.BinaryTreeFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.HashmapFieldDatabase;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;
import org.uiowa.cs2820.engine.fileoperations.RAFile;

public class IntegrationTests
{

    @Test
    public void testEmptyDatabaseReturnsNothingAVL()
    {
        Database database = new IntegratedFileDatabase(new AVLFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)), new IdentifierDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)));
        FieldSearch fieldSearch = new FieldSearch(database);
        Field F1 = new Field("1", new Integer(45));
        assertEquals(fieldSearch.findEquals(F1).length, 0);
    }

    @Test
    public void testEmptyDatabaseReturnsNothingBinary()
    {
        Database database = new IntegratedFileDatabase(new BinaryTreeFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)), new IdentifierDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)));
        FieldSearch fieldSearch = new FieldSearch(database);
        Field F1 = new Field("1", new Integer(45));
        assertEquals(fieldSearch.findEquals(F1).length, 0);
    }
    
    @Test
    public void testEmptyDatabaseReturnsNothingHashmap()
    {
        Database database = new IntegratedFileDatabase(new HashmapFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)), new IdentifierDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)));
        FieldSearch fieldSearch = new FieldSearch(database);
        Field F1 = new Field("1", new Integer(45));
        assertEquals(fieldSearch.findEquals(F1).length, 0);
    }
    
    @Test
    public void testSameFieldDifferentIdentifiersAVL()
    {
        Database database = new IntegratedFileDatabase(new AVLFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)), new IdentifierDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)));
        FieldSearch search = new FieldSearch(database);
        Indexer indexer = new Indexer(database, "abc");
        
        Field F1 = new Field("1", new Integer(45));
        Field F2 = new Field("check", new Integer(30) );
        indexer.addField(F1);
        indexer.addField(F2); 
        
        indexer = new Indexer(database, "def");
        Field F3 = new Field("check", new Integer(30));
        indexer.addField(F3);
         
        String[] S = search.findEquals(F3);
        assertEquals(2, S.length);
        assertEquals(S[1], "abc");
        assertEquals(S[0], "def");
    }
    
    @Test
    public void testSameFieldDifferentIdentifiersHashmap()
    {
        Database database = new IntegratedFileDatabase(new HashmapFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)), new IdentifierDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)));
        FieldSearch search = new FieldSearch(database);
        Indexer indexer = new Indexer(database, "abc");
        
        Field F1 = new Field("1", new Integer(45));
        Field F2 = new Field("check", new Integer(30) );
        indexer.addField(F1);
        indexer.addField(F2); 
        
        indexer = new Indexer(database, "def");
        Field F3 = new Field("check", new Integer(30));
        indexer.addField(F3);
         
        String[] S = search.findEquals(F3);
        assertEquals(2, S.length);
        assertEquals(S[1], "abc");
        assertEquals(S[0], "def");
    }
    
    @Test
    public void testSameFieldDifferentIdentifiersBinary()
    {
        Database database = new IntegratedFileDatabase(new BinaryTreeFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)), new IdentifierDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)));
        FieldSearch search = new FieldSearch(database);
        Indexer indexer = new Indexer(database, "abc");
        
        Field F1 = new Field("1", new Integer(45));
        Field F2 = new Field("check", new Integer(30) );
        indexer.addField(F1);
        indexer.addField(F2); 
        
        indexer = new Indexer(database, "def");
        Field F3 = new Field("check", new Integer(30));
        indexer.addField(F3);
         
        String[] S = search.findEquals(F3);
        assertEquals(2, S.length);
        assertEquals(S[1], "abc");
        assertEquals(S[0], "def");
    }
    
    @Test
    public void testEmptyDatabaseRAFile(){
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);

        indexer.addField(field);

        ArrayList<String> results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));
    }
    
    @Test
    public void testEmptyDatabaseRAFileHashMap(){
    	
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new HashmapFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        Field field = new Field("name", "value");
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);

        indexer.addField(field);

        ArrayList<String> results = database.fetch(field);
        assertEquals(1, results.size());
        assertEquals(identifier, results.get(0));
    }
    
    @Test
    public void BinaryIntegrationTesting(){
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);

        
        Field field1 = new Field("name", "d");
        Field field2 = new Field("name", "a");
        Field field3 = new Field("name", "e");
        Field field4 = new Field("name", "b");
        Field field5 = new Field("name", "f");
        Field field6 = new Field("name", "c");
        Field field7 = new Field("name", "g");
        
        indexer.addField(field1);
        indexer.addField(field2);
        indexer.addField(field3);
        indexer.addField(field4);
        indexer.addField(field5);
        indexer.addField(field6);
        indexer.addField(field7);
        
        String[] results = search.findEquals(field1);
        
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
      
        results = search.findEquals(field2);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field3);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field4);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field5);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field6);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field7);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
    }
    
    @Test
    public void AVLIntegrationTesting(){
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);

        
        Field field1 = new Field("name", "d");
        Field field2 = new Field("name", "a");
        Field field3 = new Field("name", "e");
        Field field4 = new Field("name", "b");
        Field field5 = new Field("name", "f");
        Field field6 = new Field("name", "c");
        Field field7 = new Field("name", "g");
        
        indexer.addField(field1);
        indexer.addField(field2);
        indexer.addField(field3);
        indexer.addField(field4);
        indexer.addField(field5);
        indexer.addField(field6);
        indexer.addField(field7);
        
        String[] results = search.findEquals(field1);
        
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
      
        results = search.findEquals(field2);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field3);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field4);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field5);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field6);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field7);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
    }
    
    @Test
    public void HashmapIntegrationTesting(){
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new HashmapFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);

        
        Field field1 = new Field("name", "d");
        Field field2 = new Field("name", "a");
        Field field3 = new Field("name", "e");
        Field field4 = new Field("name", "b");
        Field field5 = new Field("name", "f");
        Field field6 = new Field("name", "c");
        Field field7 = new Field("name", "g");
        
        indexer.addField(field1);
        indexer.addField(field2);
        indexer.addField(field3);
        indexer.addField(field4);
        indexer.addField(field5);
        indexer.addField(field6);
        indexer.addField(field7);
        
        String[] results = search.findEquals(field1);
        
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
      
        results = search.findEquals(field2);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field3);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field4);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field5);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field6);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
        results = search.findEquals(field7);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        
    }
    
    @Test
    public void testRemovingIdentifierBasicCaseAVL()
    {
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);
        
        Field field = new Field("name","value");
        
        indexer.addField(field);

        String[] results = search.findEquals(field);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);

        database.delete(field, identifier);

        results = search.findEquals(field);
        assertEquals(0,results.length);
    }
    
    @Test
    public void testRemovingIdentifierBasicCaseBinary()
    {
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);
        
        Field field = new Field("name","value");
        
        indexer.addField(field);

        String[] results = search.findEquals(field);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);

        database.delete(field, identifier);

        results = search.findEquals(field);
        assertEquals(0,results.length);
    }
    
    @Test
    public void testRemovingIdentifierBasicCaseHashmap()
    {
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new HashmapFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);
        
        Field field = new Field("name","value");
        
        indexer.addField(field);

        String[] results = search.findEquals(field);
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);

        database.delete(field, identifier);

        results = search.findEquals(field);
        assertEquals(0,results.length);
    }

    @Test
    public void AVLIntegrationTesting1(){
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new AVLFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);
        
        Field field1 = new Field("Zackery", "Milder");
        indexer.addField(field1);
        
        String[] results = search.findEquals(field1);
        
        
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        assertEquals("Zackery", field1.getFieldName());
        assertEquals("Milder", field1.getFieldValue());
        
    }
    
    @Test
    public void BinaryIntegrationTesting1(){
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new BinaryTreeFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);
        
        Field field1 = new Field("Zackery", "Milder");
        indexer.addField(field1);
        
        String[] results = search.findEquals(field1);
        
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        assertEquals("Zackery", field1.getFieldName());
        assertEquals("Milder", field1.getFieldValue());
        
    }
    
    @Test
    public void HashMapIntegrationTesting1(){
		File testingFile = new File("testingFile.dat");
		if(testingFile.exists())
			testingFile.delete();
		
		File testing = new File("testing.dat");
		if(testing.exists())
			testing.delete();
		
		
        ChunkedAccess file1 = new RAFile(new File("testingFile.dat"),16, FieldFileNode.MAX_SIZE);
        FieldDatabase fieldDB = new HashmapFieldDatabase(file1);
        ChunkedAccess file2 = new RAFile(new File("testing.dat"), 16, FieldFileNode.MAX_SIZE);
        IdentifierDatabase identDB = new IdentifierDatabase(file2);
        
        IntegratedFileDatabase database = new IntegratedFileDatabase(fieldDB, identDB);
        String identifier = "filename1";
        Indexer indexer = new Indexer(database, identifier);
        FieldSearch search = new FieldSearch(database);
        
        Field field1 = new Field("Zackery", "Milder");
        indexer.addField(field1);
        
        String[] results = search.findEquals(field1);
        
        assertEquals(1, results.length);
        assertEquals(identifier, results[0]);
        assertEquals("Zackery", field1.getFieldName());
        assertEquals("Milder", field1.getFieldValue());
        
    }
}
