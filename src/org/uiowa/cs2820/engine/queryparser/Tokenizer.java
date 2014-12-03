package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;
import java.util.HashSet;

public class Tokenizer
{
    private static final char QUOTE = '\"';
    private static HashSet<Character> specialChars = new HashSet<Character>();
    {{
        specialChars.add('(');
        specialChars.add(')');
        specialChars.add('[');
        specialChars.add(']');
    }}
    
    public Tokenizer()
    {
        
    }

    
    public ArrayList<Token> tokenize(String string)
    {
        ArrayList<Token> tokens = new ArrayList<Token>();
        
        
        for (int i = 0; i < string.length(); i++)
        {
            if (string.charAt(i) == QUOTE) // Now inside term
            {
                StringBuffer term = new StringBuffer();
                int k = i+1; 
                while (k < string.length() && string.charAt(k) != QUOTE)
                {
                    term.append(string.charAt(k));
                    k++;
                }
                i = k;
                tokens.add(new Token(term.toString(), Token.TERM));
            }
        }
        
        return tokens;
    }
    
}
