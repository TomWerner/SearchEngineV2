package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;
import org.uiowa.cs2820.engine.queryparser.Token;
import org.uiowa.cs2820.engine.queryparser.Tokenizer;

public class TokenizerTest
{
    @Test
    public void testSingleTermParse()
    {
        String string = "\"Term\"";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        assertEquals("Term", tokens.get(0).getString());
        assertEquals(Token.TERM, tokens.get(0).getType());
    }
    
    @Test
    public void testDoubleTermParse()
    {
        String string = "\"Term\", \"Term2\"";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        assertEquals("Term", tokens.get(0).getString());
        assertEquals(Token.TERM, tokens.get(0).getType());
        
        assertEquals("Term2", tokens.get(1).getString());
        assertEquals(Token.TERM, tokens.get(1).getType());
    }
    
    @Test
    public void testDoubleTermWithGarbageParse()
    {
        String string = "agf\"Term\", asdf\"Term2\"asdf";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        assertEquals("Term", tokens.get(0).getString());
        assertEquals(Token.TERM, tokens.get(0).getType());
        
        assertEquals("Term2", tokens.get(1).getString());
        assertEquals(Token.TERM, tokens.get(1).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldParse()
    {
        String string = "{\"Term\", \"Term2\"}";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("{", tokens.get(0).getString());
        assertEquals(Token.FIELD_START, tokens.get(0).getType());
        
        assertEquals("Term", tokens.get(1).getString());
        assertEquals(Token.TERM, tokens.get(1).getType());
        
        assertEquals("Term2", tokens.get(2).getString());
        assertEquals(Token.TERM, tokens.get(2).getType());
        
        assertEquals("}", tokens.get(3).getString());
        assertEquals(Token.FIELD_END, tokens.get(3).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithGarbageParse()
    {
        String string = "{sadfh\"Term\"gasd,gasdg\"Term2\"sadfasd}";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("{", tokens.get(0).getString());
        assertEquals(Token.FIELD_START, tokens.get(0).getType());
        
        assertEquals("Term", tokens.get(1).getString());
        assertEquals(Token.TERM, tokens.get(1).getType());
        
        assertEquals("Term2", tokens.get(2).getString());
        assertEquals(Token.TERM, tokens.get(2).getType());
        
        assertEquals("}", tokens.get(3).getString());
        assertEquals(Token.FIELD_END, tokens.get(3).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorParse()
    {
        String string = "operator{\"Term\", \"Term2\"}";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("operator", tokens.get(0).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(0).getType());
        
        assertEquals("{",tokens.get(1).getString());
        assertEquals(Token.FIELD_START, tokens.get(1).getType());
        
        assertEquals("Term", tokens.get(2).getString());
        assertEquals(Token.TERM, tokens.get(2).getType());
        
        assertEquals("Term2", tokens.get(3).getString());
        assertEquals(Token.TERM, tokens.get(3).getType());
        
        assertEquals("}", tokens.get(4).getString());
        assertEquals(Token.FIELD_END, tokens.get(4).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorAndWhitespaceParse()
    {
        String string = " operator {\"Term\", \"Term2\"}";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("operator", tokens.get(0).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(0).getType());
        
        assertEquals("{",tokens.get(1).getString());
        assertEquals(Token.FIELD_START, tokens.get(1).getType());
        
        assertEquals("Term", tokens.get(2).getString());
        assertEquals(Token.TERM, tokens.get(2).getType());
        
        assertEquals("Term2", tokens.get(3).getString());
        assertEquals(Token.TERM, tokens.get(3).getType());
        
        assertEquals("}", tokens.get(4).getString());
        assertEquals(Token.FIELD_END, tokens.get(4).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorAndWhitespaceAndQueryParse()
    {
        String string = "[ operator { \"Term\", \"Term2\" } ]";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("[", tokens.get(0).getString());
        assertEquals(Token.QUERY_START, tokens.get(0).getType());
        
        assertEquals("operator", tokens.get(1).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(1).getType());
        
        assertEquals("{",tokens.get(2).getString());
        assertEquals(Token.FIELD_START, tokens.get(2).getType());
        
        assertEquals("Term", tokens.get(3).getString());
        assertEquals(Token.TERM, tokens.get(3).getType());
        
        assertEquals("Term2", tokens.get(4).getString());
        assertEquals(Token.TERM, tokens.get(4).getType());

        assertEquals("}", tokens.get(5).getString());
        assertEquals(Token.FIELD_END, tokens.get(5).getType());
        
        assertEquals("]", tokens.get(6).getString());
        assertEquals(Token.QUERY_END, tokens.get(6).getType());
    }
    
    
    /*
     * This test shows that the token values aren't hardcoded and can be changed
     * by a user who wants to change the characters
     */
    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorAndWhitespaceAndQueryParseAndAlternateTokens()
    {
        String string = "{sdfsequaldsfsr( \'Term\', \'Term2\' ) }";
        HashSet<Character> whitespace = new HashSet<Character>();
        whitespace.add(' ');
        whitespace.add(',');
        whitespace.add('s');
        whitespace.add('d');
        whitespace.add('f');
        whitespace.add('r');
        Tokenizer tokenizer = new Tokenizer('\'', '(', ')', '{', '}', whitespace);
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("{", tokens.get(0).getString());
        assertEquals(Token.QUERY_START, tokens.get(0).getType());
        
        assertEquals("equal", tokens.get(1).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(1).getType());
        
        assertEquals("(",tokens.get(2).getString());
        assertEquals(Token.FIELD_START, tokens.get(2).getType());
        
        assertEquals("Term", tokens.get(3).getString());
        assertEquals(Token.TERM, tokens.get(3).getType());
        
        assertEquals("Term2", tokens.get(4).getString());
        assertEquals(Token.TERM, tokens.get(4).getType());

        assertEquals(")", tokens.get(5).getString());
        assertEquals(Token.FIELD_END, tokens.get(5).getType());
        
        assertEquals("}", tokens.get(6).getString());
        assertEquals(Token.QUERY_END, tokens.get(6).getType());
    }

    @Test
    public void testOperatorBetweenQueryParse()
    {
        String string = "[] and []";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("[", tokens.get(0).getString());
        assertEquals(Token.QUERY_START, tokens.get(0).getType());
        
        assertEquals("]", tokens.get(1).getString());
        assertEquals(Token.QUERY_END, tokens.get(1).getType());
        
        assertEquals("and", tokens.get(2).getString());
        assertEquals(Token.QUERY_OPERATOR, tokens.get(2).getType());

        assertEquals("[", tokens.get(3).getString());
        assertEquals(Token.QUERY_START, tokens.get(3).getType());
        
        assertEquals("]", tokens.get(4).getString());
        assertEquals(Token.QUERY_END, tokens.get(4).getType());
    }
    
    
}
