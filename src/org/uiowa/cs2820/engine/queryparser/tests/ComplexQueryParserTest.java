package org.uiowa.cs2820.engine.queryparser.tests;

import java.util.ArrayList;

import org.junit.Test;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;
import org.uiowa.cs2820.engine.queryparser.Token;
import org.uiowa.cs2820.engine.queryparser.TokenParser;
import org.uiowa.cs2820.engine.queryparser.Tokenizer;

public class ComplexQueryParserTest extends ComplexTokenParserTest
{
    protected Queryable parseString(String query) throws ParsingException
    {
        TokenParser parser = new ComplexTokenParser();
        Tokenizer tokenizer = new ComplexTokenizer();
        return new QueryParser(tokenizer, parser, true).parseQuery(query);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTokenParserCantHandleParenthesis()
    {
        TokenParser simpleParser = new TokenParser()
        {
            @Override
            public Queryable parseTokens(ArrayList<Token> tokens) throws ParsingException
            {
                return null;
            }
            
            @Override
            public boolean canHandleParenthesis()
            {
                return false;
            }
        };
        Tokenizer tokenizer = new ComplexTokenizer();
        
        new QueryParser(tokenizer, simpleParser, true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTokenizerCantHandleParenthesis()
    {
        Tokenizer tokenizer = new Tokenizer()
        {
            @Override
            public ArrayList<Token> tokenize(String string)
            {
                return null;
            }
            
            @Override
            public boolean canHandleParenthesis()
            {
                return false;
            }
        };
        
        new QueryParser(tokenizer, new ComplexTokenParser(), true);
    }
}
