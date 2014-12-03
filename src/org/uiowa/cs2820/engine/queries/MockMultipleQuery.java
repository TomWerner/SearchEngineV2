package org.uiowa.cs2820.engine.queries;

import java.util.ArrayList;

public class MockMultipleQuery implements Queryable
{
    private ArrayList<Query> queries;
    
    public MockMultipleQuery(ArrayList<Query> queries)
    {
        this.queries = queries;
    }
    
    public Query get(int index)
    {
        return queries.get(index);
    }
}
