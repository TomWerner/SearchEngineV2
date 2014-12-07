package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.queries.Queryable;

public abstract class QueryParser
{
    private Tokenizer tokenizer;
    private TokenParser tokenParser;
    
    public QueryParser(Tokenizer tokenizer, TokenParser tokenParser)
    {
        this.tokenizer = tokenizer;
        this.tokenParser = tokenParser;
    }
    
    public Queryable parseQuery(String query) throws ParsingException
    {
        ArrayList<Token> tokens = tokenizer.tokenize(query);
        return tokenParser.parseTokens(tokens);
    }
}
