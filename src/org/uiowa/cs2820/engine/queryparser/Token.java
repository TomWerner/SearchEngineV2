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
        return string + " - " + type;
    }
}
