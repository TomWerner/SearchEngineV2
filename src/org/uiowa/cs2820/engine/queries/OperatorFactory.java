package org.uiowa.cs2820.engine.queries;

public class OperatorFactory
{
    public static FieldOperator getFieldOperator(String operator)
    {
        if (operator.equals("equals") || operator.equals("equal"))
            return new FieldEquals();
        
        return null;
    }
}
