package org.uiowa.cs2820.engine.queries;

public class OperatorFactory
{
    public static FieldOperator getFieldOperator(String operator)
    {
        if (operator.equalsIgnoreCase("equals") || operator.equalsIgnoreCase("equal"))
            return new FieldEquals();
        
        return null;
    }

    public static QueryOperator getQueryOperator(String operator)
    {
        if (operator.equalsIgnoreCase("or"))
            return new QueryOr();
        else if (operator.equalsIgnoreCase("and"))
            return new QueryAnd();
        
        return null;
    }
}
