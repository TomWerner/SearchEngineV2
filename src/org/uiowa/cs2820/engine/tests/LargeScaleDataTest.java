package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;
import org.uiowa.cs2820.engine.Database;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.FieldSearch;
import org.uiowa.cs2820.engine.Indexer;
import org.uiowa.cs2820.engine.IntegratedFileDatabase;
import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;

public class LargeScaleDataTest
{
    @Test
    public void testSameFieldDifferentIdentifiersAVL() throws FileNotFoundException
    {
        Database database = new IntegratedFileDatabase(new AVLFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)),
                new IdentifierDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE)));

        Scanner scan = new Scanner(new File("fielddata.txt"));

        String currentIdentifier = "";
        String[] tokens = scan.nextLine().split("\\s+");
        Indexer indexer = null;
        long start = System.currentTimeMillis();
        while (scan.hasNextLine())
        {
            if (indexer == null || !tokens[0].equals(currentIdentifier))
            {
                indexer = new Indexer(database, tokens[0]);
                currentIdentifier = tokens[0];
            }
            Field field = new Field(tokens[1], tokens[2]);
            indexer.addField(field);
            tokens = scan.nextLine().split("\\s+");
        }
        System.out.println((System.currentTimeMillis()- start));

        Field f = new Field("many", "times");
        FieldSearch F = new FieldSearch(database);
        String[] S = F.findEquals(f);
        assertEquals(S.length, 30);
        for (String s : S)
            System.out.println(s);

        Field field = new Field("golden", "rumex");
        FieldSearch F2 = new FieldSearch(database);
        String[] S2 = F2.findEquals(field);
        assertEquals(S2.length, 1);
        assertTrue(Arrays.asList(S2).contains("2.txt"));
        field = new Field("not", "riparia");
        F2 = new FieldSearch(database);
        S2 = F2.findEquals(field);
        assertTrue(Arrays.asList(S2).contains("1.txt"));

        scan.close();
    }
}
