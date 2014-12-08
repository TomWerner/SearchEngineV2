package org.uiowa.cs2820.engine.queries;

import org.uiowa.cs2820.engine.Field;

public class FieldIdentifierPair
{
    private Field field;
    private String identifier;

    public FieldIdentifierPair(Field field, String identifier)
    {
        this.field = field;
        this.identifier = identifier;
    }
    
    public String toString()
    {
        return field + " - " + identifier;
    }
}
