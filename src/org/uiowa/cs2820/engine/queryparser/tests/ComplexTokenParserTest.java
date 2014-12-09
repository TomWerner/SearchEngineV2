package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.DoubleQuery;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queries.QueryAnd;
import org.uiowa.cs2820.engine.queries.QueryOr;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.TokenParser;

public class ComplexTokenParserTest
{   
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
        String expression = "{\"Field\", \"Value\"}";
        Query result = (Query) parseString(expression);

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testSpecifiedOperatorSingleQueryWithQueryMarkings() throws ParsingException
    {
        String expression = "equal {\"Field\", \"Value\"}";
        Query result = (Query) parseString(expression);

        assertEquals(new Field("Field", "Value"), result.getField());
        assertTrue(result.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testTwoQueries() throws ParsingException
    {
        String expression = "equal {\"Field\", \"Value\"} and {\"Field\", \"Other value\"}";
        DoubleQuery result = (DoubleQuery) parseString(expression);

        Query query1 = (Query) result.getQuery1();
        Query query2 = (Query) result.getQuery2();

        assertEquals(new Field("Field", "Value"), query1.getField());
        assertTrue(query1.getOperator() instanceof FieldEquals);

        assertEquals(new Field("Field", "Other value"), query2.getField());
        assertTrue(query2.getOperator() instanceof FieldEquals);

        assertTrue(result.getOperator() instanceof QueryAnd);
    }

    @Test
    public void testTwoQueriesDefaultOperator() throws ParsingException
    {
        String expression = "equal {\"Field\", \"Value\"}{\"Field\", \"Other value\"}";
        DoubleQuery result = (DoubleQuery) parseString(expression);

        Query query1 = (Query) result.getQuery1();
        Query query2 = (Query) result.getQuery2();

        assertEquals(new Field("Field", "Value"), query1.getField());
        assertTrue(query1.getOperator() instanceof FieldEquals);

        assertEquals(new Field("Field", "Other value"), query2.getField());
        assertTrue(query2.getOperator() instanceof FieldEquals);

        assertTrue(result.getOperator() instanceof QueryOr);
    }

    @Test
    public void test4Queries() throws ParsingException
    {
        String expression = "";
        int number = 4;
        for (int i = 0; i < number; i++)
            expression += "equals {\"Field\", \"" + i + "\"}";

        DoubleQuery result = (DoubleQuery) parseString(expression);
        // System.out.println(result);
        // n = 4
        // (((a, b), c), d)
        // By doing it like this we have sets of double queries
        // This assumes left to right reading, and no parenthesis

        Query right = ((Query) result.getQuery2());
        assertEquals(new Field("Field", "" + 3), right.getField());
        assertTrue(right.getOperator() instanceof FieldEquals);

        // Move down a layer
        result = (DoubleQuery) result.getQuery1();
        right = ((Query) result.getQuery2());
        assertEquals(new Field("Field", "" + 2), right.getField());
        assertTrue(right.getOperator() instanceof FieldEquals);

        // Move down one more layer
        result = (DoubleQuery) result.getQuery1();
        right = ((Query) result.getQuery2());
        assertEquals(new Field("Field", "" + 1), right.getField());
        assertTrue(right.getOperator() instanceof FieldEquals);

        right = ((Query) result.getQuery1());
        assertEquals(new Field("Field", "" + 0), right.getField());
        assertTrue(right.getOperator() instanceof FieldEquals);
    }

    @Test
    public void testNQueries() throws ParsingException
    {
        String expression = "";
        int number = 10;
        for (int i = 0; i < number; i++)
            expression += "equals {\"Field\", \"" + i + "\"}";

        DoubleQuery result = (DoubleQuery) parseString(expression);
        // System.out.println(result);
        // n = 4
        // (((a, b), c), d)
        // By doing it like this we have sets of double queries
        // This assumes left to right reading, and no parenthesis

        Query right = ((Query) result.getQuery2());
        assertEquals(new Field("Field", "" + (number - 1)), right.getField());
        assertTrue(right.getOperator() instanceof FieldEquals);

        for (int i = number - 2; i >= 1; i--)
        {
            // Move down a layer
            result = (DoubleQuery) result.getQuery1();
            right = ((Query) result.getQuery2());

            right = ((Query) result.getQuery2());
            assertEquals(new Field("Field", "" + i), right.getField());
            assertTrue(right.getOperator() instanceof FieldEquals);
        }
        
        right = ((Query) result.getQuery2());
        assertEquals(new Field("Field", "" + 1), right.getField());
        assertTrue(right.getOperator() instanceof FieldEquals);
        
        Query left = ((Query) result.getQuery1());
        assertEquals(new Field("Field", "" + 0), left.getField());
        assertTrue(left.getOperator() instanceof FieldEquals);
    }
    
    protected Queryable parseString(String query) throws ParsingException
    {
        TokenParser parser = new ComplexTokenParser();
        return parser.parseTokens(new ComplexTokenizer().tokenize(query));
    }

    @Test
    public void testThreeQueriesNormal() throws ParsingException
    {
        String qA = "equal {\"Field\", \"a\"}";
        String qB = "{\"Field\", \"b\"}";
        String qC = "equal {\"Field\", \"c\"}";
        String wholeThing = "(" + qA + " or " + qB + ") and " + qC;
        String query1 = qA + " or " + qB;
        String query2 = qC;
        DoubleQuery left = (DoubleQuery) parseString(query1);
        Query right = (Query) parseString(query2);
        DoubleQuery result = (DoubleQuery) parseString(wholeThing);
                
        assertEquals(left, result.getQuery1());
        assertEquals(right, result.getQuery2());
        assertTrue(result.getOperator() instanceof QueryAnd);
    }
    
    @Test
    public void testThreeQueriesBackwards() throws ParsingException
    {
        String qA = "equal {\"Field\", \"a\"}";
        String qB = "{\"Field\", \"b\"}";
        String qC = "equal {\"Field\", \"c\"}";
        String wholeThing = qA + " or (" + qB + " and " + qC + ")";
        String query1 = qA;
        String query2 = qB + "and" + qC;
        Query left = (Query) parseString(query1);
        DoubleQuery right = (DoubleQuery) parseString(query2);
        DoubleQuery result = (DoubleQuery) parseString(wholeThing);
        
        assertEquals(left, result.getQuery1());
        assertEquals(right, result.getQuery2());
        assertTrue(result.getOperator() instanceof QueryOr);
    }
    
    @Test
    public void testFourQueriesNormal() throws ParsingException
    {
        String qA = "equal {\"Field\", \"a\"}";
        String wholeThing = String.format("(((%s or %s) or %s) or %s)", qA, qA, qA, qA);
        
        Query right = (Query) parseString(qA);
        DoubleQuery result = (DoubleQuery) parseString(wholeThing);
        
        assertEquals(right, result.getQuery2());
        assertTrue(result.getOperator() instanceof QueryOr);
        
        result = (DoubleQuery) result.getQuery1();
        assertEquals(right, result.getQuery2());
        assertTrue(result.getOperator() instanceof QueryOr);
        
        result = (DoubleQuery) result.getQuery1();
        assertEquals(right, result.getQuery2());
        assertEquals(right, result.getQuery1());
        assertTrue(result.getOperator() instanceof QueryOr);
    }
    
    @Test
    public void testFourQueriesBackwards() throws ParsingException
    {
        String qA = "equal {\"Field\", \"a\"}";
        String wholeThing = String.format("(%s or (%s or (%s or %s)))", qA, qA, qA, qA);
        
        Query left = (Query) parseString(qA);
        DoubleQuery result = (DoubleQuery) parseString(wholeThing);
        
        assertEquals(left, result.getQuery1());
        assertTrue(result.getOperator() instanceof QueryOr);
        
        result = (DoubleQuery) result.getQuery2();
        assertEquals(left, result.getQuery1());
        assertTrue(result.getOperator() instanceof QueryOr);
        
        result = (DoubleQuery) result.getQuery2();
        assertEquals(left, result.getQuery2());
        assertEquals(left, result.getQuery1());
        assertTrue(result.getOperator() instanceof QueryOr);
    }
    
    @Test
    public void testFourQueriesPaired() throws ParsingException
    {
        String qA = "equal {\"Field\", \"a\"}";
        String wholeThing = String.format("(%s or %s) or (%s and %s)", qA, qA, qA, qA);
        
        Query query = (Query) parseString(qA);
        DoubleQuery left = new DoubleQuery(query, query, new QueryOr());
        DoubleQuery right = new DoubleQuery(query, query, new QueryAnd());
        DoubleQuery result = (DoubleQuery) parseString(wholeThing);
        
        assertEquals(left, result.getQuery1());
        assertEquals(right, result.getQuery2());
        assertTrue(result.getOperator() instanceof QueryOr);
        
    }
}
