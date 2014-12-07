package org.uiowa.cs2820.engine.queries;


public class MockDoubleQuery implements Queryable
{
    private Queryable query1, query2;
    private QueryOperator operator;
    
    public MockDoubleQuery(Queryable query1, Queryable query2, QueryOperator operator)
    {
        this.query1 = query1;
        this.query2 = query2;
        this.operator = operator;
    }
    
    public Queryable getQuery1()
    {
        return query1;
    }
    
    public Queryable getQuery2()
    {
        return query2;
    }
    
    public QueryOperator getOperator()
    {
        return operator;
    }
    
    public String toString()
    {
        return "( " + query1 + " " + operator + " " + query2 + " )";
    }
}
