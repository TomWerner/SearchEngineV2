package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.queries.Queryable;

public interface TokenParser
{

    public abstract Queryable parseTokens(ArrayList<Token> tokens) throws ParsingException;

}