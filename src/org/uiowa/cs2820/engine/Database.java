package org.uiowa.cs2820.engine;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.queries.Queryable;

public interface Database
{
    public abstract ArrayList<String> fetch(Field field); // fetch a Node by key

    public abstract void delete(Field field, String identifier); // delete an id

    public abstract void store(Field field, String identifier); // store an id
    
    public abstract ArrayList<String> matchQuery(Queryable query);
}
