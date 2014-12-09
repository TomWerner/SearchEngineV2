package org.uiowa.cs2820.engine.queryparser.tests;

import org.junit.Test;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;
import org.uiowa.cs2820.engine.queryparser.SimpleBadTokenParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenizer;

public class SimpleQueryParserTest extends SimpleTokenParserTest
{

    @Override
    protected Queryable parseString(String query) throws ParsingException
    {
        QueryParser parser = new QueryParser(new SimpleTokenizer(), new SimpleBadTokenParser(), false);
        return parser.parseQuery(query);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testParenthesisTokenParserRequirement()
    {
        new QueryParser(new ComplexTokenizer(), new SimpleBadTokenParser(), true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testParenthesisTokenizerRequirement()
    {
        new QueryParser(new SimpleTokenizer(), new ComplexTokenParser(), true);
    }
    
}
