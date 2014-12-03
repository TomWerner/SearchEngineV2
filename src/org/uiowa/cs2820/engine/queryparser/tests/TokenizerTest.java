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
    public void testDoubleTermSurroundedByTrashParse()
    {
        String string = "asfdasd\"Term\"asd,sadf g\"Term2\"asdfh";
        Tokenizer tokenizer = new Tokenizer();
        ArrayList<Token> tokens = tokenizer.tokenize(string);
        
        assertEquals("Term", tokens.get(0).getString());
        assertEquals(Token.TERM, tokens.get(0).getType());
        
        assertEquals("Term2", tokens.get(1).getString());
        assertEquals(Token.TERM, tokens.get(1).getType());
    }
}
