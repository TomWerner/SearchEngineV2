package org.uiowa.cs2820.engine.queryparser;

public class Token
{
    public static final int TERM = 0;
    public static final int FIELD_START = 1;
    public static final int FIELD_END = 2;
    public static final int QUERY_START = 3;
    public static final int QUERY_END = 4;
    public static final int FIELD_OPERATOR = 5;
    public static final int QUERY_OPERATOR = 6;
    public static final int PAREN_START = 7;
    public static final int PAREN_END = 8;
    
    
    private String string;
    private int type;
    
    public Token(String string, int type)
    {
        this.string = string;
        this.type = type;
    }

    public String getString()
    {
        return string;
    }

    public int getType()
    {
        return type;
    }
    
    public String toString()
    {
        return string;
    }
    
    public static String getTypeName(int type)
    {
    	switch (type)
    	{
    		case TERM:
    			return "Term";
    		case FIELD_START:
    			return "Field Start";
    		case FIELD_END:
    			return "Field End";
    		case QUERY_START:
    			return "Query Start";
    		case QUERY_END:
    			return "Query End";
    		case FIELD_OPERATOR:
    			return "Field Operator";
    		case QUERY_OPERATOR:
    			return "Query Operator";
    		case PAREN_START:
    		    return "Parenthesis Start";
    		case PAREN_END:
    		    return "Parenthesis End";
    		default:
    			return "Unknown type";
    	}
    }
}
