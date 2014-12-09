package org.uiowa.cs2820.engine.queryparser.tests;

import org.junit.Test;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ComplexStackTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenizer;

public class SimpleQueryParserTest extends SimpleTokenParserTest
{

    @Override
    protected Queryable parseString(String query) throws ParsingException
    {
        QueryParser parser = new QueryParser(new SimpleTokenizer(), new SimpleTokenParser(), false);
        return parser.parseQuery(query);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testParenthesisTokenParserRequirement()
    {
        new QueryParser(new ComplexTokenizer(), new SimpleTokenParser(), true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testParenthesisTokenizerRequirement()
    {
        new QueryParser(new SimpleTokenizer(), new ComplexStackTokenParser(), true);
    }
    
}
