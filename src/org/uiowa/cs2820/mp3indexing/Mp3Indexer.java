package org.uiowa.cs2820.mp3indexing;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.Database;
import org.uiowa.cs2820.engine.Field;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.Mp3File;

public class Mp3Indexer
{
    private Database database;
    private Mp3File mp3File;

    public Mp3Indexer(Database database, Mp3File mp3File)
    {
        this.database = database;
        this.mp3File = mp3File;
    }

    public void addFields()
    {
        if (mp3File.hasId3v1Tag())
            addId3v1Tags();

    }

    private void addId3v2Tags()
    {
        ID3v2 tag = mp3File.getId3v2Tag();
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

        for (Field field : possibleFields)
            if (field.getFieldValue() != null)
                database.store(field, mp3File.getFilename());
    }

    private void addId3v1Tags()
    {
        ID3v1 tag = mp3File.getId3v1Tag();
        ArrayList<Field> possibleFields = new ArrayList<Field>();

        possibleFields.add(new Field("Track", tag.getTrack()));
        possibleFields.add(new Field("Artist", tag.getArtist()));
        possibleFields.add(new Field("Title", tag.getTitle()));
        possibleFields.add(new Field("Album", tag.getAlbum()));
        possibleFields.add(new Field("Year", tag.getYear()));
        String genre = tag.getGenre() + " (" + tag.getGenreDescription() + ")";
        possibleFields.add(new Field("Genre", genre));
        possibleFields.add(new Field("Comment", tag.getComment()));

        for (Field field : possibleFields)
            if (field.getFieldValue() != null)
                database.store(field, mp3File.getFilename());
    }
}