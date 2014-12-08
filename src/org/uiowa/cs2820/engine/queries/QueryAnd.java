package org.uiowa.cs2820.engine.queries;

public class QueryAnd implements QueryOperator
{
    public String toString()
    {
        return "and";
    }

    @Override
    public boolean isSatisfiedBy(boolean operand1, boolean operand2)
    {
        return operand1 && operand2;
    }
    
}
