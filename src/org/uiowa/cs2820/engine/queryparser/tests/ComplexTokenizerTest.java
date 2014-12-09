package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.Token;
import org.uiowa.cs2820.engine.queryparser.Tokenizer;

public class ComplexTokenizerTest
{
    
    @Test
    public void testSingleTermParse()
    {
        String string = "\"Term\"";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
        
        assertEquals("Term", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }
    
    @Test
    public void testDoubleTermParse()
    {
        String string = "\"Term\", \"Term2\"";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
        
        assertEquals("Term", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("Term2", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }
    
    @Test
    public void testDoubleTermWithGarbageParse()
    {
        String string = "agf\"Term\", asdf\"Term2\"asdf";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
        
        assertEquals("Term", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("Term2", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldParse()
    {
        String string = "{\"Term\", \"Term2\"}";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
        
        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());
        
        assertEquals("Term", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("Term2", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithGarbageParse()
    {
        String string = "{sadfh\"Term\"gasd,gasdg\"Term2\"sadfasd}";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
        
        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());
        
        assertEquals("Term", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("Term2", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorParse()
    {
        String string = "operator{\"Term\", \"Term2\"}";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
        
        assertEquals("OPERATOR", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());
        
        assertEquals("{",tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());
        
        assertEquals("Term", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("Term2", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorAndWhitespaceParse()
    {
        String string = " operator {\"Term\", \"Term2\"}";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
        
        assertEquals("OPERATOR", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());
        
        assertEquals("{",tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());
        
        assertEquals("Term", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("Term2", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorAndWhitespaceAndQueryParse()
    {
        String string = "operator { \"Term\", \"Term2\" }";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
                
        assertEquals("OPERATOR", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());
        
        assertEquals("{",tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());
        
        assertEquals("Term", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());
        
        assertEquals("Term2", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());
                
        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }

    @Test
    public void testOperatorBetweenQueryParse()
    {
        String string = "equals{\"a\",\"b\"} and equals{\"c\",\"d\"}";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());

        assertEquals("EQUALS", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());

        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());

        assertEquals("a", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("b", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());
        
        assertEquals("AND", tokens.get(++i).getString());
        assertEquals(Token.QUERY_OPERATOR, tokens.get(i).getType());

        assertEquals("EQUALS", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());

        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());

        assertEquals("c", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("d", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());
        
        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }
    
    @Test
    public void testParenBackwards()
    {
        String string = "equals{\"a\",\"b\"} and (equals{\"c\",\"d\"} or equals{\"e\",\"f\"})";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());

        assertEquals("EQUALS", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());

        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());

        assertEquals("a", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("b", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());
        
        assertEquals("AND", tokens.get(++i).getString());
        assertEquals(Token.QUERY_OPERATOR, tokens.get(i).getType());

        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());
        
        assertEquals("EQUALS", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());

        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());

        assertEquals("c", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("d", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());
        
        assertEquals("OR", tokens.get(++i).getString());
        assertEquals(Token.QUERY_OPERATOR, tokens.get(i).getType());
        
        assertEquals("EQUALS", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());

        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());

        assertEquals("e", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("f", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }
    
    @Test
    public void testParenNormal()
    {
        String string = "(equals{\"a\",\"b\"} and equals{\"c\",\"d\"}) or equals{\"e\",\"f\"}";
        Tokenizer tokenizer = getTokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        int i = -1;
        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());

        assertEquals("(", tokens.get(++i).getString());
        assertEquals(Token.PAREN_START, tokens.get(i).getType());

        assertEquals("EQUALS", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());

        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());

        assertEquals("a", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("b", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());
        
        assertEquals("AND", tokens.get(++i).getString());
        assertEquals(Token.QUERY_OPERATOR, tokens.get(i).getType());
        
        assertEquals("EQUALS", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());

        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());

        assertEquals("c", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("d", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
        
        assertEquals("OR", tokens.get(++i).getString());
        assertEquals(Token.QUERY_OPERATOR, tokens.get(i).getType());
        
        assertEquals("EQUALS", tokens.get(++i).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(i).getType());

        assertEquals("{", tokens.get(++i).getString());
        assertEquals(Token.FIELD_START, tokens.get(i).getType());

        assertEquals("e", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("f", tokens.get(++i).getString());
        assertEquals(Token.TERM, tokens.get(i).getType());

        assertEquals("}", tokens.get(++i).getString());
        assertEquals(Token.FIELD_END, tokens.get(i).getType());

        assertEquals(")", tokens.get(++i).getString());
        assertEquals(Token.PAREN_END, tokens.get(i).getType());
    }
    
    protected Tokenizer getTokenizer()
    {
        return new ComplexTokenizer();
    }
}
