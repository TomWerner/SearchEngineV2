package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.uiowa.cs2820.engine.queryparser.Token;

public class TokenTest
{
    @Test
    public void testGetTypeName()
    {
        assertEquals("Term", Token.getTypeName(Token.TERM));
        assertEquals("Field Start", Token.getTypeName(Token.FIELD_START));
        assertEquals("Field End", Token.getTypeName(Token.FIELD_END));
        assertEquals("Field Operator", Token.getTypeName(Token.FIELD_OPERATOR));
        assertEquals("Query Operator", Token.getTypeName(Token.QUERY_OPERATOR));
        assertEquals("Parenthesis Start", Token.getTypeName(Token.PAREN_START));
        assertEquals("Parenthesis End", Token.getTypeName(Token.PAREN_END));
        assertEquals("Unknown type", Token.getTypeName(-1));
    }
}
