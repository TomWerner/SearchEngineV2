package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.queries.DoubleQuery;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queries.QueryAnd;
import org.uiowa.cs2820.engine.queries.QueryOr;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.TokenParser;

public class ComplexTokenParserTest extends SimpleBadTokenParserTest
{    
    protected Queryable parseString(String query) throws ParsingException
    {
        TokenParser parser = new ComplexTokenParser();
        return parser.parseTokens(new ComplexTokenizer().tokenize(query));
    }

    @Test
    public void testThreeQueriesNormal() throws ParsingException
    {
        String qA = "[equal {\"Field\", \"a\"}]";
        String qB = "[{\"Field\", \"b\"}]";
        String qC = "[equal {\"Field\", \"c\"}]";
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
        String qA = "[equal {\"Field\", \"a\"}]";
        String qB = "[{\"Field\", \"b\"}]";
        String qC = "[equal {\"Field\", \"c\"}]";
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
        String qA = "[equal {\"Field\", \"a\"}]";
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
        String qA = "[equal {\"Field\", \"a\"}]";
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
        String qA = "[equal {\"Field\", \"a\"}]";
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
