package org.uiowa.cs2820.mp3indexing.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.uiowa.cs2820.engine.Database;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.FieldSearch;
import org.uiowa.cs2820.engine.IntegratedFileDatabase;
import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.databases.ValueFileNode;
import org.uiowa.cs2820.engine.fileoperations.MockChunkRandomAccessFile;
import org.uiowa.cs2820.mp3indexing.Mp3Indexer;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Mp3IndexerTest
{
    @Test
    public void testId3v1Tags() throws UnsupportedTagException, InvalidDataException, IOException
    {
        Mp3File file = new Mp3File("data/test.mp3");
        Database database = getDatabase();
        Mp3Indexer indexer = new Mp3Indexer(database, file);
        indexer.addFields();
        
        
        ID3v2 tag = file.getId3v2Tag();
        ArrayList<Field> possibleFields = new ArrayList<Field>();

        possibleFields.add(new Field("Track", tag.getTrack()));
        possibleFields.add(new Field("Artist", tag.getArtist()));
        possibleFields.add(new Field("Title", tag.getTitle()));
        possibleFields.add(new Field("Album", tag.getAlbum()));
        possibleFields.add(new Field("Year", tag.getYear()));
        String genre = tag.getGenre() + " (" + tag.getGenreDescription() + ")";
        possibleFields.add(new Field("Genre", genre));
        possibleFields.add(new Field("Comment", tag.getComment()));
        possibleFields.add(new Field("Composer", tag.getComposer()));
        possibleFields.add(new Field("Publisher", tag.getPublisher()));
        possibleFields.add(new Field("Original artist", tag.getOriginalArtist()));
        possibleFields.add(new Field("Album artist", tag.getAlbumArtist()));
        possibleFields.add(new Field("Copyright", tag.getCopyright()));
        possibleFields.add(new Field("URL", tag.getUrl()));
        possibleFields.add(new Field("Encoder", tag.getEncoder()));

        FieldSearch fs = new FieldSearch(database);
        for (Field field : possibleFields)
            if (field.getFieldValue() != null)
                assertEquals(file.getFilename(), fs.findEquals(field)[0]);
    }
    
    protected Database getDatabase()
    {
        FieldDatabase fieldDB = new AVLFieldDatabase(new MockChunkRandomAccessFile(16, FieldFileNode.MAX_SIZE));
        IdentifierDatabase identDB = new IdentifierDatabase(new MockChunkRandomAccessFile(16, ValueFileNode.MAX_SIZE));
        return new IntegratedFileDatabase(fieldDB, identDB);
    }
}
