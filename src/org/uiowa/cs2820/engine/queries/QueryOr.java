package org.uiowa.cs2820.engine.queries;

import java.util.HashSet;

public class QueryOr implements QueryOperator
{
    public String toString()
    {
        return "or";
    }

    @Override
    public HashSet<String> evaluate(HashSet<String> query1Results, HashSet<String> query2Results)
    {
        HashSet<String> clone = new HashSet<String>(query1Results);
        clone.addAll(query2Results);
        return clone;
    }

    
}
