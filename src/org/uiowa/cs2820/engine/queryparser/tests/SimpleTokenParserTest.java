package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.MockMultipleQuery;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenizer;
import org.uiowa.cs2820.engine.queryparser.TokenParser;

public class SimpleTokenParserTest
{
    @Test(expected = ParsingException.class)
    public void testNoFieldStart() throws ParsingException
    {
        String query = "\"Field\", \"Value\"}";
        parseString(query);
    }

    @Test(expected = ParsingException.class)
    public void testNoFirstTerm() throws ParsingException
    {
        String query = "( [ \"Field\", \"Value\"}";
        parseString(query);
    }

    @Test(expected = ParsingException.class)
    public void testNoSecondTerm() throws ParsingException
    {
        String query = "{\"Field\"}";
        parseString(query);
    }

    @Test(expected = ParsingException.class)
    public void testNoFieldEnd() throws ParsingException
    {
        String query = "{\"Field\", \"Value\"]";
        parseString(query);
    }

    @Test
    public void testDefaultOperatorSingleQuery() throws ParsingException
    {
        String expression = "{\"Field\", \"Value\"}";
        Query result = (Query) parseString(expression);

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testSpecifiedOperatorSingleQuery() throws ParsingException
    {
        String expression = "equal {\"Field\", \"Value\"}";
        Query result = (Query) parseString(expression);

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testDefaultOperatorSingleQueryWithQueryMarkings() throws ParsingException
    {
        String expression = "[{\"Field\", \"Value\"}]";
        Query result = (Query) parseString(expression);

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testSpecifiedOperatorSingleQueryWithQueryMarkings() throws ParsingException
    {
        String expression = "[equal {\"Field\", \"Value\"}]";
        Query result = (Query) parseString(expression);

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testTwoQueries() throws ParsingException
    {
        String expression = "[equal {\"Field\", \"Value\"}] and [{\"Field\", \"Other value\"}]";
        MockMultipleQuery result = (MockMultipleQuery) parseString(expression);

        assertEquals(new Field("Field", "Value"), result.get(0).getField());
        assertTrue(result.get(0).getOperator() instanceof FieldEquals);

        assertEquals(new Field("Field", "Other value"), result.get(1).getField());
        assertTrue(result.get(1).getOperator() instanceof FieldEquals);
    }

    @Test
    public void testTwoQueriesDefaultOperator() throws ParsingException
    {
        String expression = "[equal {\"Field\", \"Value\"}][{\"Field\", \"Other value\"}]";
        MockMultipleQuery result = (MockMultipleQuery) parseString(expression);

        assertEquals(new Field("Field", "Value"), result.get(0).getField());
        assertTrue(result.get(0).getOperator() instanceof FieldEquals);

        assertEquals(new Field("Field", "Other value"), result.get(1).getField());
        assertTrue(result.get(1).getOperator() instanceof FieldEquals);
    }

    @Test
    public void testLotsOfQueries() throws ParsingException
    {
        String expression = "";
        int number = 2;
        for (int i = 0; i < number; i++)
            expression += "[equals {\"Field\", \"" + i + "\"}]";
        
        MockMultipleQuery result = (MockMultipleQuery) parseString(expression); 

        for (int i = 0; i < number; i++)
        {
            assertEquals(new Field("Field", "" + i), result.get(i).getField());
            assertTrue(result.get(i).getOperator() instanceof FieldEquals);
        }
    }
    
    protected Queryable parseString(String query) throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        return parser.parseTokens(new SimpleTokenizer().tokenize(query));
    }
}
