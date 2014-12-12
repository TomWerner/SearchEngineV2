package org.uiowa.cs2820.engine.queries;

import java.util.HashSet;

import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;

public interface Queryable
{
    public void isSatisfiedBy(FieldFileNode node, IdentifierDatabase identDB);
    public void resetQuery();
    public HashSet<String> evaluate();
}
