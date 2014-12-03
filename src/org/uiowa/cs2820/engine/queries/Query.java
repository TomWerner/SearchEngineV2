package org.uiowa.cs2820.engine.queries;

import org.uiowa.cs2820.engine.Field;

public class Query implements Queryable
{

    private Field field;
    private FieldOperator operator;

    public Query(Field field, FieldOperator fieldOp)
    {
        this.field = field;
        this.operator = fieldOp;
    }

    public Field getField()
    {
        return field;
    }

    public FieldOperator getOperator()
    {
        return operator;
    }
    
    
}
