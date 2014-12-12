package org.uiowa.cs2820.engine.queries;

import java.util.HashSet;

public interface QueryOperator
{
    public HashSet<String> evaluate(HashSet<String> query1Results, HashSet<String> query2Results);
}
