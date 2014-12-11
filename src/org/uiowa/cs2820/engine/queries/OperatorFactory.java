package org.uiowa.cs2820.engine.queries;

public class OperatorFactory
{
    public static FieldOperator getFieldOperator(String operator)
    { 
        if (operator.equalsIgnoreCase("equals") || operator.equalsIgnoreCase("equal"))
            return new FieldEquals();
        else if (operator.equalsIgnoreCase("greater than") || operator.equals(">"))
        	return new FieldGreaterThan();
        else if (operator.equalsIgnoreCase("lesser than") || operator.equals("<"))
        	return new FieldLessThan();
        else if (operator.equalsIgnoreCase("includes"))
        	return new FieldIncludes();
        else if (operator.equalsIgnoreCase("starts with") || operator.equalsIgnoreCase("prefix"))
        	return new FieldPrefix();
        else if (operator.equalsIgnoreCase("ends with") || operator.equalsIgnoreCase("postfix"))
        	return new FieldPostfix();
        
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
