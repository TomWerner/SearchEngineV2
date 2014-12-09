package org.uiowa.cs2820.engine.queries;

import java.util.HashSet;

public class QueryAnd implements QueryOperator
{
    public String toString()
    {
        return "and";
    }

    @Override
    public HashSet<String> evaluate(HashSet<String> query1Results, HashSet<String> query2Results)
    {
        boolean set1IsLarger = query1Results.size() > query2Results.size();
        HashSet<String> cloneSet = new HashSet<String>(set1IsLarger ? query2Results : query1Results);
        cloneSet.retainAll(set1IsLarger ? query1Results : query2Results);
        return cloneSet;
    }
    
}
