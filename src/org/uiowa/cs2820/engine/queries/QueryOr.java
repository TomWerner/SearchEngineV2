package org.uiowa.cs2820.engine.queries;

public class QueryOr implements QueryOperator
{
    public String toString()
    {
        return "or";
    }

    @Override
    public boolean isSatisfiedBy(boolean operand1, boolean operand2)
    {
        return operand1 || operand2;
    }
    
}
