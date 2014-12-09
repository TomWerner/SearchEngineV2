package org.uiowa.cs2820.engine.queryparser.tests;

import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ComplexStackTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;
import org.uiowa.cs2820.engine.queryparser.TokenParser;
import org.uiowa.cs2820.engine.queryparser.Tokenizer;

public class ComplexStackQueryParserTest extends ComplexStackTokenParserTest
{
    protected Queryable parseString(String query) throws ParsingException
    {
        TokenParser parser = new ComplexStackTokenParser();
        Tokenizer tokenizer = new ComplexTokenizer();
        return new QueryParser(tokenizer, parser, true).parseQuery(query);
    }
}
