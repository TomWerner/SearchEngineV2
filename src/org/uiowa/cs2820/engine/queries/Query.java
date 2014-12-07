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

    public String toString()
    {
        return operator.toString() + field.toString();
    }

    public boolean equals(Object other)
    {
        if (other instanceof Query)
        {
            return field.equals(((Query) other).field) && operator.getClass().equals(other.getClass());
        }
        return false;
    }
}
