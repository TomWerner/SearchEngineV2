package org.uiowa.cs2820.engine;

import java.util.ArrayList;

public class FieldSearch
{

    private Database database;

    public FieldSearch(Database database)
    {
        this.database = database;
    }
 
    public String[] findEquals(Field searchField)
    {
        ArrayList<String> identifiers = database.fetch(searchField);
        if (identifiers == null)
            return new String[0];
        String[] results = new String[identifiers.size()];
        results = identifiers.toArray(results);
        return results;
    }
}