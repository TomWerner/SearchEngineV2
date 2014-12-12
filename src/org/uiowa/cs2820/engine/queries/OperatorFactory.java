package org.uiowa.cs2820.engine.queries;

public class OperatorFactory
{
    public static FieldOperator getFieldOperator(String operator)
    { 
        if (operator.equalsIgnoreCase("equals") || operator.equalsIgnoreCase("equal"))
            return new FieldEquals();
        else if (operator.equalsIgnoreCase("greaterthan") || operator.equals(">"))
        	return new FieldGreaterThan();
        else if (operator.equalsIgnoreCase("lesserthan") || operator.equals("<"))
        	return new FieldLessThan();
        else if (operator.equalsIgnoreCase("includes"))
        	return new FieldIncludes();
        else if (operator.equalsIgnoreCase("startswith") || operator.equalsIgnoreCase("prefix"))
        	return new FieldPrefix();
        else if (operator.equalsIgnoreCase("endswith") || operator.equalsIgnoreCase("postfix"))
        	return new FieldPostfix();
        else if (operator.contains("~"))
        {
        	int fuzzLevel = Integer.parseInt(operator.split("~")[1]);
        	FieldFuzzySearch FFS = new FieldFuzzySearch();
        	FFS.setFuzz(fuzzLevel);
        	return FFS;
        }
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
