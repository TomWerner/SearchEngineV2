package org.uiowa.cs2820.engine.queryparser;

public class Token
{
    public static final int TERM = 0;
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
