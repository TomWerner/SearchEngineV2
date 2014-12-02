package org.uiowa.cs2820.engine;

public class Indexer
{
    private Database database;
    private String identifier;

    public Indexer(Database database, String identifier)
    {
        // constructor does nothing now, but someday
        // may need to set up database for doing things
        this.database = database;
        this.identifier = identifier;
    } 

    public void addField(Field field)
    {
        // Field has (name,value) which is used as key for
        // the database operations
        database.store(field, identifier);
    }

}
