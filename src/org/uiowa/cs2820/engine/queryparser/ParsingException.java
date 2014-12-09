package org.uiowa.cs2820.engine.queryparser;


public class ParsingException extends Exception
{
    public ParsingException(Exception e)
    {
        super(e);
    }
    
    public ParsingException(String message)
    {
        super(message);
    }

    private static final long serialVersionUID = 885105809984857452L;
}
