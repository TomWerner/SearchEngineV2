package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.MockMultipleQuery;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenParser;
import org.uiowa.cs2820.engine.queryparser.SimpleTokenizer;
import org.uiowa.cs2820.engine.queryparser.TokenParser;

public class SimpleTokenParserTest
{
    @Test(expected = ParsingException.class)
    public void testNoFieldStart() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String query = "\"Field\", \"Value\"}";
        parser.parseTokens(new SimpleTokenizer().tokenize(query));
    }

    @Test(expected = ParsingException.class)
    public void testNoFirstTerm() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String query = "( [ \"Field\", \"Value\"}";
        parser.parseTokens(new SimpleTokenizer().tokenize(query));
    }

    @Test(expected = ParsingException.class)
    public void testNoSecondTerm() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String query = "{\"Field\"}";
        parser.parseTokens(new SimpleTokenizer().tokenize(query));
    }

    @Test(expected = ParsingException.class)
    public void testNoFieldEnd() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String query = "{\"Field\", \"Value\"]";
        parser.parseTokens(new SimpleTokenizer().tokenize(query));
    }

    @Test
    public void testDefaultOperatorSingleQuery() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String expression = "{\"Field\", \"Value\"}";
        Query result = (Query) parser.parseTokens(new SimpleTokenizer().tokenize(expression));

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testSpecifiedOperatorSingleQuery() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String expression = "equal {\"Field\", \"Value\"}";
        Query result = (Query) parser.parseTokens(new SimpleTokenizer().tokenize(expression));

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testDefaultOperatorSingleQueryWithQueryMarkings() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String expression = "[{\"Field\", \"Value\"}]";
        Query result = (Query) parser.parseTokens(new SimpleTokenizer().tokenize(expression));

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testSpecifiedOperatorSingleQueryWithQueryMarkings() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String expression = "[equal {\"Field\", \"Value\"}]";
        Query result = (Query) parser.parseTokens(new SimpleTokenizer().tokenize(expression));

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testTwoQueries() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String expression = "[equal {\"Field\", \"Value\"}] and [{\"Field\", \"Other value\"}]";
        MockMultipleQuery result = (MockMultipleQuery) parser.parseTokens(new SimpleTokenizer().tokenize(expression));

        assertEquals(new Field("Field", "Value"), result.get(0).getField());
        assertTrue(result.get(0).getOperator() instanceof FieldEquals);

        assertEquals(new Field("Field", "Other value"), result.get(1).getField());
        assertTrue(result.get(1).getOperator() instanceof FieldEquals);
    }

    @Test
    public void testTwoQueriesDefaultOperator() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String expression = "[equal {\"Field\", \"Value\"}][{\"Field\", \"Other value\"}]";
        MockMultipleQuery result = (MockMultipleQuery) parser.parseTokens(new SimpleTokenizer().tokenize(expression));

        assertEquals(new Field("Field", "Value"), result.get(0).getField());
        assertTrue(result.get(0).getOperator() instanceof FieldEquals);

        assertEquals(new Field("Field", "Other value"), result.get(1).getField());
        assertTrue(result.get(1).getOperator() instanceof FieldEquals);
    }

    @Test
    public void testLotsOfQueries() throws ParsingException
    {
        TokenParser parser = new SimpleTokenParser();
        String expression = "";
        int number = 2;
        for (int i = 0; i < number; i++)
            expression += "[equals {\"Field\", \"" + i + "\"}]";
        
        MockMultipleQuery result = (MockMultipleQuery) parser.parseTokens(new SimpleTokenizer().tokenize(expression));

        for (int i = 0; i < number; i++)
        {
            assertEquals(new Field("Field", "" + i), result.get(i).getField());
            assertTrue(result.get(i).getOperator() instanceof FieldEquals);
        }
    }
}
