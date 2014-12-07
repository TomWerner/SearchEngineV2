package org.uiowa.cs2820.engine.queries;

public class DoubleQuery implements Queryable
{
    private Queryable query1, query2;
    private QueryOperator operator;

    public DoubleQuery(Queryable query1, Queryable query2, QueryOperator operator)
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

    public boolean equals(Object other)
    {
        if (other instanceof DoubleQuery)
        {
            boolean q1Match = query1.equals(((DoubleQuery) other).query1);
            boolean q2Match = query2.equals(((DoubleQuery) other).query2);
            boolean opMatch = operator.getClass().equals(((DoubleQuery) other).operator.getClass());
            
            return q1Match && q2Match && opMatch;
        }
        return false;
    }
}
