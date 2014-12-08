package org.uiowa.cs2820.mp3indexing;

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
        if (mp3File.hasId3v2Tag())
            addId3v2Tags();
    }

    private void addId3v2Tags()
    {
        ID3v2 tag = mp3File.getId3v2Tag();
        database.store(new Field("Track", tag.getTrack()), mp3File.getFilename());
        database.store(new Field("Artist", tag.getArtist()), mp3File.getFilename());
        database.store(new Field("Title", tag.getTitle()), mp3File.getFilename());
        database.store(new Field("Album", tag.getAlbum()), mp3File.getFilename());
        database.store(new Field("Year", tag.getYear()), mp3File.getFilename());
        String genre = tag.getGenre() + " (" + tag.getGenreDescription() + ")";
        database.store(new Field("Genre", genre), mp3File.getFilename());
        database.store(new Field("Comment", tag.getComment()), mp3File.getFilename());
        database.store(new Field("Composer", tag.getComposer()), mp3File.getFilename());
        database.store(new Field("Publisher", tag.getPublisher()), mp3File.getFilename());
        database.store(new Field("Original artist", tag.getOriginalArtist()), mp3File.getFilename());
        database.store(new Field("Album artist", tag.getAlbumArtist()), mp3File.getFilename());
        database.store(new Field("Copyright", tag.getCopyright()), mp3File.getFilename());
        database.store(new Field("URL", tag.getUrl()), mp3File.getFilename());
        database.store(new Field("Encoder", tag.getEncoder()), mp3File.getFilename());
    }

    private void addId3v1Tags()
    {
        ID3v1 tag = mp3File.getId3v1Tag();
        database.store(new Field("Track", tag.getTrack()), mp3File.getFilename());
        database.store(new Field("Artist", tag.getArtist()), mp3File.getFilename());
        database.store(new Field("Title", tag.getTitle()), mp3File.getFilename());
        database.store(new Field("Album", tag.getAlbum()), mp3File.getFilename());
        database.store(new Field("Year", tag.getYear()), mp3File.getFilename());
        String genre = tag.getGenre() + " (" + tag.getGenreDescription() + ")";
        database.store(new Field("Genre", genre), mp3File.getFilename());
        database.store(new Field("Comment", tag.getComment()), mp3File.getFilename());
    }
}