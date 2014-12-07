package org.uiowa.cs2820.engine.queryparser.tests;

import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;
import org.uiowa.cs2820.engine.queryparser.SimpleQueryParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenizer;

public class SimpleQueryParserTest extends SimpleTokenParserTest
{

    @Override
    protected Queryable parseString(String query) throws ParsingException
    {
        QueryParser parser = new SimpleQueryParser(new SimpleTokenizer(), new SimpleTokenParser());
        return parser.parseQuery(query);
    }
    
}
