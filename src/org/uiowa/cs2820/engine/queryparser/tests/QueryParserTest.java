package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.MockMultipleQuery;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;


public class QueryParserTest
{
    @Test(expected = ParsingException.class)
    public void testNoFieldStart() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String query = "\"Field\", \"Value\"}";
        parser.parseExpression(query);
    }
    
    @Test(expected = ParsingException.class)
    public void testNoFirstTerm() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String query = "( [ \"Field\", \"Value\"}";
        parser.parseExpression(query);
    }
    
    @Test(expected = ParsingException.class)
    public void testNoSecondTerm() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String query = "{\"Field\"}";
        parser.parseExpression(query);
    }
    
    @Test(expected = ParsingException.class)
    public void testNoFieldEnd() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String query = "{\"Field\", \"Value\"]";
        parser.parseExpression(query);
    }
    
    @Test
    public void testDefaultOperatorSingleQuery() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String expression = "{\"Field\", \"Value\"}";
        Query result = (Query) parser.parseExpression(expression);
        
        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }
    
    @Test
    public void testSpecifiedOperatorSingleQuery() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String expression = "equal {\"Field\", \"Value\"}";
        Query result = (Query) parser.parseExpression(expression);
        
        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }
    
    @Test
    public void testDefaultOperatorSingleQueryWithQueryMarkings() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String expression = "[{\"Field\", \"Value\"}]";
        Query result = (Query) parser.parseExpression(expression);
        
        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }
    
    @Test
    public void testSpecifiedOperatorSingleQueryWithQueryMarkings() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String expression = "[equal {\"Field\", \"Value\"}]";
        Query result = (Query) parser.parseExpression(expression);
        
        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }
    
    @Test
    public void testTwoQueries() throws ParsingException
    {
        QueryParser parser = new QueryParser();
        String expression = "[equal {\"Field\", \"Value\"}] and [{\"Field\", \"Other value\"}]";
        MockMultipleQuery result = (MockMultipleQuery) parser.parseExpression(expression);
        
        assertEquals(new Field("Field", "Value"), result.get(0).getField());
        assertTrue(result.get(0).getOperator() instanceof FieldEquals);

        assertEquals(new Field("Field", "Other value"), result.get(1).getField());
        assertTrue(result.get(1).getOperator() instanceof FieldEquals);
    }
    
    
}
