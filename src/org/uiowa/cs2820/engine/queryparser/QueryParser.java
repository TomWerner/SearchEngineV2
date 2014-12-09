package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.queries.Queryable;

public class QueryParser
{
    private Tokenizer tokenizer;
    private TokenParser tokenParser;
    
    public QueryParser(Tokenizer tokenizer, TokenParser tokenParser, boolean canHandleParenthesis)
    {
        this.tokenizer = tokenizer;
        
        if (canHandleParenthesis && !tokenParser.canHandleParenthesis())
            throw new IllegalArgumentException("Token parser must be able to handle parenthesis");
        if (canHandleParenthesis && !tokenizer.canHandleParenthesis())
            throw new IllegalArgumentException("Tokenizer must be able to handle parenthesis");
        
        
        this.tokenParser = tokenParser;
    }
    
    public Queryable parseQuery(String query) throws ParsingException
    {
        ArrayList<Token> tokens = tokenizer.tokenize(query);
        return tokenParser.parseTokens(tokens);
    }
}
