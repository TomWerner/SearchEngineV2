package org.uiowa.cs2820.engine.queryparser.tests;

import org.junit.Test;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.SimpleStackTokenParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenizer;
import org.uiowa.cs2820.engine.queryparser.TokenParser;

public class SimpleStackTokenParserTest extends SimpleTokenParserTest
{
    @Test
    public void testNoFieldStart() throws ParsingException
    {
    }

    @Test
    public void testNoFirstTerm() throws ParsingException
    {
    }

    @Test
    public void testNoSecondTerm() throws ParsingException
    {
    }

    @Test
    public void testNoFieldEnd() throws ParsingException
    {
    }

    @Override
    protected Queryable parseString(String query) throws ParsingException
    {
        TokenParser parser = new SimpleStackTokenParser();
        return parser.parseTokens(new SimpleTokenizer().tokenize(query));
    }
    
}
