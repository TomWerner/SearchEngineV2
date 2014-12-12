package org.uiowa.cs2820.engine.queries;

import org.uiowa.cs2820.engine.Field;

public class FieldEquals implements FieldOperator
{
    public String toString()
    {
        return "equals";
    }

    @Override
    public Boolean compare(Field field, Field testField)
    {
        return field.equals(testField);
    }
    
    
}
