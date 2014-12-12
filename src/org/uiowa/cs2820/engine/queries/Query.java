package org.uiowa.cs2820.engine.queries;

import java.util.HashSet;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;

public class Query implements Queryable
{

    private Field field;
    private FieldOperator operator;
    private HashSet<String> identifierSet;

    public Query(Field field, FieldOperator fieldOp)
    {
        this.field = field;
        this.operator = fieldOp;
        this.identifierSet = new HashSet<String>();
    }
    
    public void resetQuery()
    {
        identifierSet = new HashSet<String>();
    }
    
    @Override
    public void isSatisfiedBy(FieldFileNode node, IdentifierDatabase identDB)
    {
        if (operator.compare(field, node.getField()))
            identifierSet.addAll(identDB.getAllIdentifiers(node.getHeadOfLinkedListPosition()));
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
            return field.equals(((Query) other).field) && operator.getClass().equals(((Query)other).operator.getClass());
        }
        return false;
    }

    @Override
    public HashSet<String> evaluate()
    {
        return identifierSet;
    }
    
    
}
