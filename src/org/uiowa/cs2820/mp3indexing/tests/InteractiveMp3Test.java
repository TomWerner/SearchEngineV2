package org.uiowa.cs2820.mp3indexing.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.uiowa.cs2820.engine.Database;
import org.uiowa.cs2820.engine.IntegratedFileDatabase;
import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.databases.ValueFileNode;
import org.uiowa.cs2820.engine.fileoperations.RAFile;
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
            System.out.print("Enter a folder: ");
            input = scan.nextLine();
            if (!input.equals("done"))
            {
                ArrayList<String> files = getFilesInFolder(input);
                for (int i = 0; i < files.size() && i < 1000; i++)
                {
                    String file = files.get(i);
                    System.out.println(i + " - " + file);
                    Mp3File mp3File = new Mp3File(file);
                    new Mp3Indexer(database, mp3File).addFields();
                }
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
                for (String string : database.matchQuery(query))
                    System.out.println(string);
            }
        }
        while (!input.equals("done"));
        scan.close();
    }

    private static ArrayList<String> getFilesInFolder(String folder)
    {
        ArrayList<String> files = new ArrayList<String>();
        File[] faFiles = new File(folder).listFiles();
        for (File file : faFiles)
        {
            if (file.isDirectory())
            {
                files.addAll(getFilesInFolder(file.getAbsolutePath()));
            }
            else if (file.getName().contains(".mp3"))
            {
                files.add(file.getAbsolutePath());
            }
        }
        return files;
    }

    public static Database getDatabase()
    {
        FieldDatabase fieldDB = new AVLFieldDatabase(new RAFile(new File("data/musicFieldDatabase.dat"), 128, FieldFileNode.MAX_SIZE));
        IdentifierDatabase identDB = new IdentifierDatabase(new RAFile(new File("data/musicIdentifierDatabase.dat"), 128, ValueFileNode.MAX_SIZE));
        return new IntegratedFileDatabase(fieldDB, identDB);
    }
}
