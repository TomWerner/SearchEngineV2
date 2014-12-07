package org.uiowa.cs2820.engine.queryparser.tests;

import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenizer;

public class QueryParserTest extends SimpleTokenParserTest
{

    @Override
    protected Queryable parseString(String query) throws ParsingException
    {
        QueryParser parser = new QueryParser(new SimpleTokenizer(), new SimpleTokenParser());
        return parser.parseQuery(query);
    }
    
}
