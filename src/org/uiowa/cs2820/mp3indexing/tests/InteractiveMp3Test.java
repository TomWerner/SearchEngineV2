package org.uiowa.cs2820.mp3indexing.tests;

import java.io.IOException;
import java.util.Scanner;

import org.uiowa.cs2820.engine.Database;
import org.uiowa.cs2820.engine.IntegratedFileDatabase;
import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.databases.ValueFileNode;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;
import org.uiowa.cs2820.mp3indexing.Mp3Indexer;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class InteractiveMp3Test
{
    public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException, ParsingException
    {
        Scanner scan = new Scanner(System.in);
        String input = "";
        Database database = getDatabase();
        QueryParser parser = new QueryParser(new ComplexTokenizer(), new ComplexTokenParser(), true);
        do
        {
            System.out.print("Enter a file: ");
            input = scan.nextLine();
            if (!input.equals("done"))
            {
                Mp3File mp3File = new Mp3File(input);
                new Mp3Indexer(database, mp3File).addFields();
            }
        }
        while (!input.equals("done"));
        
        do
        {
            System.out.print("Enter a query: ");
            input = scan.nextLine();
            if (!input.equals("done"))
            {
                Queryable query = parser.parseQuery(input);
                System.out.println(query);
                System.out.println(database.matchQuery(query));
            }
        }
        while (!input.equals("done"));
        scan.close();
    }

    public static Database getDatabase()
    {
        FieldDatabase fieldDB = new AVLFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        IdentifierDatabase identDB = new IdentifierDatabase(new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE));
        return new IntegratedFileDatabase(fieldDB, identDB);
    }
}
