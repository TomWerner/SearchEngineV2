package org.uiowa.cs2820.engine.queryparser.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
        String string = "(\"Term\", \"Term2\")";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("(", tokens.get(0).getString());
        assertEquals(Token.START_FIELD, tokens.get(0).getType());
        
        assertEquals("Term", tokens.get(1).getString());
        assertEquals(Token.TERM, tokens.get(1).getType());
        
        assertEquals("Term2", tokens.get(2).getString());
        assertEquals(Token.TERM, tokens.get(2).getType());
        
        assertEquals(")", tokens.get(3).getString());
        assertEquals(Token.END_FIELD, tokens.get(3).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithGarbageParse()
    {
        String string = "(sadfh\"Term\"gasd,gasdg\"Term2\"sadfasd)";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("(", tokens.get(0).getString());
        assertEquals(Token.START_FIELD, tokens.get(0).getType());
        
        assertEquals("Term", tokens.get(1).getString());
        assertEquals(Token.TERM, tokens.get(1).getType());
        
        assertEquals("Term2", tokens.get(2).getString());
        assertEquals(Token.TERM, tokens.get(2).getType());
        
        assertEquals(")", tokens.get(3).getString());
        assertEquals(Token.END_FIELD, tokens.get(3).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorParse()
    {
        String string = "operator(\"Term\", \"Term2\")";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("operator", tokens.get(0).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(0).getType());
        
        assertEquals("(",tokens.get(1).getString());
        assertEquals(Token.START_FIELD, tokens.get(1).getType());
        
        assertEquals("Term", tokens.get(2).getString());
        assertEquals(Token.TERM, tokens.get(2).getType());
        
        assertEquals("Term2", tokens.get(3).getString());
        assertEquals(Token.TERM, tokens.get(3).getType());
        
        assertEquals(")", tokens.get(4).getString());
        assertEquals(Token.END_FIELD, tokens.get(4).getType());
    }

    @Test
    public void testDoubleTermSurroundedByFieldWithOperatorAndWhitespaceParse()
    {
        String string = " operator (\"Term\", \"Term2\")";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);

        assertEquals("operator", tokens.get(0).getString());
        assertEquals(Token.FIELD_OPERATOR, tokens.get(0).getType());
        
        assertEquals("(",tokens.get(1).getString());
        assertEquals(Token.START_FIELD, tokens.get(1).getType());
        
        assertEquals("Term", tokens.get(2).getString());
        assertEquals(Token.TERM, tokens.get(2).getType());
        
        assertEquals("Term2", tokens.get(3).getString());
        assertEquals(Token.TERM, tokens.get(3).getType());
        
        assertEquals(")", tokens.get(4).getString());
        assertEquals(Token.END_FIELD, tokens.get(4).getType());
    }
}